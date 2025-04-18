package controller;


import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import enumeration.CardRank;
import enumeration.CardSuit;
import enumeration.CommandType;

import enumeration.GameParms;
import model.DTO.PlayerRankDTO;
import model.JavaBean.Card;
import model.JavaBean.Deck;
import model.JavaBean.Player;
import model.DTO.PlayerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ServerReceiveThread;
import thread.ServerSendThread;
import utils.*;

/**
 * Controls the game logic and handles communication between clients and server.
 * Manages player connections, card dealing, and game flow.
 */
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private ServerReceiveMQ serverReceiveMQ;
    private ServerSendMQ serverSendMQ;
    private MessageBroadcaster messageBroadcaster;

    private Socket socket;

    private static final int MAX_PLAYER_NUMBER = GameParms.MAX_PLAYER_NUMBER.getValue();

    private static final int MAX_CARDS_NUMBER = GameParms.MAX_CARD_NUMBER.getValue();

    private List<Player> playerList;

    private Deck deck;

    private List<PlayerRankDTO> playerRankResult;

    private int RANK_COUNTER = 0;

    /**
     * Creates a new GameController.
     * Initializes player list, message queues, and deck.
     */
    public GameController() {
        this.playerList = Collections.synchronizedList(new ArrayList<>());
        serverReceiveMQ = new ServerReceiveMQ();
        serverSendMQ = new ServerSendMQ();
        messageBroadcaster = new MessageBroadcaster(serverSendMQ);
        deck = new Deck();
        playerRankResult = new ArrayList<>();
    }

    /**
     * Sets up communication with a new client connection.
     * Starts the send and receive threads for handling messages.
     *
     * @param socket The socket for the new client connection
     */
    public void handleNewConnection(Socket socket) {
        this.socket = socket;

        startServerSendThread();

        startServerReceiveThread();

        startHandlingMsg();
    }

    /**
     * Creates and starts a new thread for sending messages to clients.
     */
    private void startServerSendThread() {
        // Start ServerSendThread
        ServerSendThread serverSendThread = new ServerSendThread(serverSendMQ);
        serverSendThread.start();
        log.info("ClientSendThread Started Successfully");
    }

    /**
     * Creates and starts a new thread for receiving messages from clients.
     */
    private void startServerReceiveThread() {
        ServerReceiveThread serverReceiveThread = new ServerReceiveThread(socket, serverReceiveMQ);
        serverReceiveThread.start();
        log.info("ClientReceiveThread Started Successfully");
    }


    /**
     * Creates and starts a new thread that listens for incoming messages.
     * Handles messages as they arrive in the queue.
     */
    private void startHandlingMsg() {
        new Thread(() -> {
            log.info("GameController Listening for Messages...");
            while (true) {
                ServerReceiveMQ.MessageEntry entry = serverReceiveMQ.takeMessage();
                if (entry != null) {
                    log.info("GC has taken message: {}, {}", entry.message, entry.socket);
                    // Handling messages
                    handleMessage(entry.message, entry.socket);
                }
            }
        }).start();
    }

    /**
     * Processes incoming messages based on command type.
     * Routes to specific handler methods for each command.
     *
     * @param message The message received from the client
     * @param socket The socket from which the message was received
     */
    private void handleMessage(String message, Socket socket) {
        System.out.println("========= Server HandleMessage Info ========");

        // Split JOIN command to commandType and commandParams
        String[] parts = message.split(" ");
        String commandType = parts[0];
        String commandArgs = message.substring(commandType.length()).trim();

        // Handle message according to command type defined in CommandType enum class
        switch (commandType) {
            // handle join cmd
            case "JOIN" -> handleJoinCmd(parts);

            // handle play cmd
            case "PLAY" -> handlePlayCmd(commandArgs);

        }
    }


    /**
     * Handles a player joining the game.
     * Creates a new player, adds to the player list, and broadcasts welcome message.
     * Starts the game if the maximum number of players is reached.
     *
     * @param parts The JOIN command split into parts
     */
    private void handleJoinCmd(String[] parts) {
        System.out.println("----------- JOIN COMMAND -----------");
        // Creating new player and adding to the player list
        Player newPlayer = new Player(parts[1], socket);
        this.playerList.add(newPlayer);

        // "WELCOME" command
        String welcomeCmd = CommandBuilder.buildCommand(
                CommandType.SERVER_WELCOME,           // parts[0]
                newPlayer.getName(),                  // parts[1]
                Integer.toString(newPlayer.getId()),  // parts[2]
                Integer.toString(playerList.size()),  // parts[3]
                newPlayer.getSocket().getRemoteSocketAddress().toString()  //parts[4]
        );

        // BroadCast welcomeCommand to client
        messageBroadcaster.broadcastMessage(playerList, welcomeCmd);

        // All players are ready, start the game
        if (playerList.size() >= MAX_PLAYER_NUMBER) {
            // Shuffle the deck
            deck.shuffle();

            for (Player player : playerList) {
                // Deal cards
                List<Card> cardsDeal = deck.deal(MAX_CARDS_NUMBER);
                HandEvaluator.sortHandByValue(cardsDeal);
                player.setHand(cardsDeal);
                PlayerDTO playerDTO = new PlayerDTO(player.getId(), player.getName(), player.getHand());
                // Converting PlayerDTO to json string
                String playerDtoJson = JsonUtil.toJson(playerDTO);
                // Build "START" command
                String startCmd = CommandBuilder.buildCommand(CommandType.SERVER_START, playerDtoJson);

                // Send msg
                serverSendMQ.addMessage(player.getSocket(), startCmd);
            }
            log.info("Finished sending players' info to client");
        }
    }


    /**
     * Handles a player's card play.
     * Updates player's selected cards and calculates rankings when all players have played.
     * Broadcasts results to all players.
     *
     * @param commandArgs The JSON string containing the played cards information
     */
    private void handlePlayCmd(String commandArgs) {

        System.out.println("----------- PLAY COMMAND -----------");
        try {
            // Parse the selected cards from JSON
            JSONArray playedCardsJsonArray = JsonUtil.toArray(commandArgs);

            // Parse Json string and set player object's SelectedCards attribute
            int playerId = -1;
            List<Card> playedCardList = new ArrayList<>();
            for(int i = 0; i < playedCardsJsonArray.size(); i++){
                JSONObject playedCardJson = (JSONObject) playedCardsJsonArray.get(i);
                playerId = playedCardJson.getInteger("playerId");
                String playedCardRank = playedCardJson.getString("rank");
                String playedCardSuit = playedCardJson.getString("suit");

                Card playedCard  = new Card(CardSuit.fromName(playedCardSuit),
                        CardRank.fromName(playedCardRank));

                playedCardList.add(playedCard);
            }
            if (playerId == -1) log.error("Can't find player");

            // Set played cards to player
            for (Player player : playerList) {
                if (player.getId() == playerId) {
                    player.setSelectedCards(playedCardList);
                    RANK_COUNTER++;
                    break;
                }
            }

            // Sort player list by rank of played card list
            // Encapsulating rank info and send to frontend
            if (RANK_COUNTER == MAX_PLAYER_NUMBER) {
                playerList.sort((p1, p2) -> new HandComparator()
                        .compare(p1.getSelectedCards(), p2.getSelectedCards()));

                int rank = 1;
                for (Player player : playerList) {
                    PlayerRankDTO dto = new PlayerRankDTO();
                    dto.setRank(rank++);
                    dto.setPlayerId(player.getId());
                    dto.setPlayerName(player.getName());
                    dto.setPlayedCards(player.getSelectedCards());
                    playerRankResult.add(dto);
                }
            }
            log.info("Ranking Complete: {}", playerRankResult);

            // Encapsulate cmd and send to client
            String resultJsonString = JsonUtil.toJson(playerRankResult);
            String resultCommand = CommandBuilder.buildCommand(CommandType.SERVER_RESULT, resultJsonString);
            messageBroadcaster.broadcastMessage(playerList, resultCommand);

        } catch (Exception e) {
            log.error("Error handling client play", e);
        }
    }
}


