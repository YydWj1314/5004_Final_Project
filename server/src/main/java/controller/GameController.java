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
     *
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
     * @param socket
     */
    public void handleNewConnection(Socket socket) {
        this.socket = socket;

        startServerSendThread();

        startServerReceiveThread();

        startHandlingMsg();
    }

    /**
     *
     */
    private void startServerSendThread() {
        // Start ServerSendThread
        ServerSendThread serverSendThread = new ServerSendThread(serverSendMQ);
        serverSendThread.start();
        log.info("ClientSendThread Started Successfully");
    }

    /**
     * Create thread receiving message to backend
     * and start the thread
     */
    private void startServerReceiveThread() {
        ServerReceiveThread serverReceiveThread = new ServerReceiveThread(socket, serverReceiveMQ);
        serverReceiveThread.start();
        log.info("ClientReceiveThread Started Successfully");
    }


    /**
     * Create new thread handing message receiving from backend
     * and start the thread
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
     * @param message
     * @param socket
     */
    private void handleMessage(String message, Socket socket) {
        System.out.println("========= Server HandleMessage Info ========");

        // Split JOIN command to commandType and commandParams
        // eg: JOIN yyd --> commandType: JOIN,
        //                  commandParams: yyd
        String[] parts = message.split(" ");
        String commandType = parts[0];
        String commandArgs = message.substring(commandType.length()).trim();

        // Handle message according to command type defined in CommandType enum class
        // command type eg: JOIN, PLAY ...
        switch (commandType) {
            // handle join cmd
            case "JOIN" -> handleJoinCmd(parts);

            // handle play cmd
            case "PLAY" -> handlePlayCmd(commandArgs);

        }
    }


    /**
     * @param parts
     */
    private void handleJoinCmd(String[] parts) {
        System.out.println("----------- JOIN COMMAND -----------");
        // Creating new player and adding to the player list
        Player newPlayer = new Player(parts[1], socket);
        this.playerList.add(newPlayer);

        // Build "WELCOME" command
        // eg:   WELCOME (name)daniel (id)1 (socket)127.0.0.1 (player number)1
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
                // Sort hand in descending order
                HandEvaluator.sortHandByValue(cardsDeal);
                // Set hand of player
                player.setHand(cardsDeal);
                // Encapsulate DTO
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
     * Handles a client play command, eg:
     * PLAY_RESULT { "playerHand":[{"rank":"EIGHT","suit":"SPADES"},
     *                             {"rank":"THREE","suit":"SPADES"},
     *                             {"rank":"KING","suit":"DIAMONDS"},
     *                             {"rank":"NINE","suit":"SPADES"},
     *                             {"rank":"TEN","suit":"CLUBS"}],
     *               "playerId":1,
     *               "playerName":"yyd"
     *              }
     * @param commandArgs The command arguments containing the card data
     */
    private void handlePlayCmd(String commandArgs) {

        System.out.println("----------- PLAY COMMAND -----------");
        try {
            // Parse the selected cards from JSON, eg:
            //     [{"rank":"QUEEN","suit":"SPADES","playerId":1},
            //      {"rank":"QUEEN","suit":"DIAMONDS","playerId":1},
            //      {"rank":"ACE","suit":"DIAMONDS","playerId":1}]
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


