package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import enumeration.CardRank;
import enumeration.CardSuit;
import enumeration.CommandType;

import enumeration.GameParms;
import model.DTO.PlayerDTO;
import model.DTO.PlayerRankDTO;
import model.DTO.selectedCardDTO;
import model.JavaBean.Card;
import model.JavaBean.Player;
import model.VO.CardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientReceiveThread;
import thread.ClientSendThread;
import utils.ClientReceiveMQ;
import utils.ClientSendMQ;
import utils.CommandBuilder;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.JsonUtil;


/**
 * ClientController is responsible for handling message
 * passing from backend, and informing updating UI
 */
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private static final String SEVER_IP = "127.0.0.1"; // IP address listened by server

    private static final int SERVER_PORT = 10087;   // port listened by server

    private Socket socket;

    private final String playerName;  // player name from login frame

    private final ClientSendMQ clientSendMQ;

    private final ClientReceiveMQ clientReceiveMQ;

    private final EventListener eventListener;

    private final Player localPlayer;


    /**
     * Constructor of ClientController
     *
     * @param playerName player name input in login frame
     *                   received from login frame
     */
    public ClientController(String playerName, EventListener eventListener) {
        this.clientSendMQ = new ClientSendMQ();
        this.clientReceiveMQ = new ClientReceiveMQ();
        this.playerName = playerName;
        this.eventListener = eventListener;
        this.localPlayer = new Player(playerName);

        createSocket();

        startClientSendThread();

        startClientReceiveThread();

        startHandlingMsg();

        sendPlayerName();
    }

    /**
     * Make connection with backend game server
     * by socket with target IP and target port number
     */
    private void createSocket() {
        try {
            this.socket = new Socket(SEVER_IP, SERVER_PORT);
        } catch (IOException ex) {
            log.error("Open socket failed");
            throw new RuntimeException(ex);
        }

        log.info("Try to connect server: IP: {}, Port: {}", SEVER_IP, SERVER_PORT);
    }

    /**
     * Create thread sending message to backend
     * and start the thread
     */
    private void startClientSendThread() {

        ClientSendThread clientSendThread = new ClientSendThread(socket, clientSendMQ);
        clientSendThread.start();
        log.info("ClientSendThread Started Successfully");
    }

    /**
     * Create thread receiving message to backend
     * and start the thread
     */
    private void startClientReceiveThread() {
        ClientReceiveThread clientReceiveThread = new ClientReceiveThread(socket, clientReceiveMQ);
        clientReceiveThread.start();
        log.info("ClientReceiveThread Started Successfully");
    }

    /**
     * Send player name taken from login frame to backend
     */
    private void sendPlayerName() {
        // Encapsulate player name to JOIN command
        // eg: JOIN yyd
        String joinCommand = CommandBuilder.buildCommand(CommandType.CLIENT_JOIN, playerName);
        // Add command to sendMQ
        this.clientSendMQ.addMessage(joinCommand);
        log.info("JoinCommand ==> clientSendMQ: {}", joinCommand);
    }

    /**
     * Start new thread taking msg from MQ,
     * and start handing message
     */
    private void startHandlingMsg() {
        // Starting Message thread and taking messages
        new Thread(() -> {
            log.info("ClientController Listening for Messages...");
            while (true) {
                String message = clientReceiveMQ.takeMessage();
                if (message != null && !message.isEmpty()) {
                    log.info("CC has taken message: {}", message);
                    // Handling messages
                    handleMessage(message);
                }
            }
        }).start();
    }


    /**
     * Handle message taken from messageBuffer
     *
     * @param message message taken from messageBuffer
     *                send from backend server
     */
    private void handleMessage(String message) {
        System.out.println("========== Client handleMessage info==========");

        String[] commandParts = message.split(" ");
        String commandType = commandParts[0];
        String commandArgs = message.substring(commandType.length()).trim();

        switch (commandType) {
            case "WELCOME" -> handleWelcomeCmd(commandParts);

            case "START" -> handleStartCmd(commandArgs);

            case "RESULT" -> handleResultCmd(commandArgs);

        }
    }

    private void handleResultCmd(String commandArgs) {
        System.out.println("----------- RESULT ----------");
        // RESULT [{
        //          "playedCards":[{"rank":"ACE","suit":"SPADES"},
        //                         {"rank":"TEN","suit":"DIAMONDS"},
        //                         {"rank":"TEN","suit":"HEARTS"}],
        //          "playerId":1,
        //          "playerName":"123",
        //          "rank":1 },
        //         {
        //              "playedCards":[{"rank":"JACK","suit":"SPADES"},
        //                              {"rank":"TEN","suit":"CLUBS"},
        //                              {"rank":"EIGHT","suit":"SPADES"}],
        //              "playerId":2,
        //              "playerName":"456",
        //              "rank":2
        //          }
        //        ]
        // Parsing json strings to array
        System.out.println(commandArgs);

        // Parsing json strings to array
        ArrayList<PlayerRankDTO> playerRankDTOS = new ArrayList<>();
        JSONArray playerJsonArray = JsonUtil.toArray(commandArgs);
        for (int i = 0; i < playerJsonArray.size(); i++) {
            JSONObject playerJson = (JSONObject) playerJsonArray.get(i);
            // Parse player id
            int parsedPlayerId = playerJson.getInteger("playerId");
            // Parse player name
            String parsedPlayerName = playerJson.getString("playerName");
            // Parse rank
            int parsedRank = playerJson.getInteger("rank");
            // Parse played card list
            List<Card> parsedPlayedCards = new ArrayList<>();
            JSONArray playedCardsJsonArray = playerJson.getJSONArray("playedCards");
            for (int j = 0; j < playedCardsJsonArray.size(); j++) {
                JSONObject handCard = (JSONObject) playedCardsJsonArray.get(j);
                String handCardRank = handCard.getString("rank");
                String handCardSuit = handCard.getString("suit");
                parsedPlayedCards.add(
                        new Card(CardSuit.valueOf(handCardSuit), CardRank.valueOf(handCardRank)));
            }

            // Set attributes
            playerRankDTOS.add(new PlayerRankDTO(parsedRank, parsedPlayerId,
                    parsedPlayerName, parsedPlayedCards));
        }

        // Notifying listener
        if(eventListener != null && playerRankDTOS.size() >= GameParms.MAX_PLAYER_NUMBER.getValue()){
            StringBuilder resultMessage = new StringBuilder("\n******** Game Result: *******\n");

            for (int i = 0; i < playerRankDTOS.size(); i++) {
                PlayerRankDTO dto = playerRankDTOS.get(i);
                String resString = dto.getPlayedCards().stream()
                        .map(card -> card.getSuit() + "" + card.getRank())
                        .collect(Collectors.joining(", "));

                resultMessage.append("Rank ")
                        .append(dto.getRank())
                        .append(": Player ")
                        .append(dto.getPlayerName())
                        .append(" played: ")
                        .append(resString)
                        .append("\n");
            }
            eventListener.onPlayerResultUpdated(resultMessage.toString());
            eventListener.onTextAreaUpdated(resultMessage.toString());
        }
    }

    /**
     * Function to handle WELCOME command
     *
     * @param commandParts command parts split
     */
    private void handleWelcomeCmd(String[] commandParts){
        // eg: WELCOME (name)daniel (id)1 (player number)1 (socket)127.0.0.1
        System.out.println("----------- WELCOME COMMAND -----------");
        if (eventListener != null) {
            String welcomeString = String.format(
                    "%s Welcome! Online player number: %s.", commandParts[1], commandParts[3]
            );

            // Getting socket send by server
            String receivedSocketAddress = commandParts[4];
            // Getting client local socket
            String localSocketAddress = socket.getLocalSocketAddress().toString();
            // Setting id for player
            if (receivedSocketAddress.equals(localSocketAddress)) {
                this.localPlayer.setId(Integer.parseInt(commandParts[2]));
                log.info("Set ID for current player: {}", this.localPlayer.getId());
            }

            eventListener.onTextAreaUpdated(welcomeString);
        }
    }

    /**
     * Function to START command
     *
     * @param commandArgs
     */
    private void handleStartCmd(String commandArgs){
        System.out.println("----------- START COMMAND -----------");
        if (eventListener != null) {
            // Update system message area with the game start information
            eventListener.onTextAreaUpdated("All players joined. Game is starting...");

            try {
                PlayerDTO playerDTO = JsonUtil.parseJson(commandArgs, PlayerDTO.class);

                List<Card> playerHand = playerDTO.getPlayerHand();
                // Encapsulate cardVO list
                List<CardVO> cardVOs = playerHand.stream()
                        .map(card -> new CardVO(card.getSuit(), card.getRank(), true))
                        .collect(Collectors.toList());
                log.info("Mapped to CardVO list: {}", cardVOs);

                eventListener.onCardAreaUpdated(cardVOs);
            } catch (Exception e) {
                log.error("Failed to parse player hands", e);
            }

            // Optional: Send a special message or mark the end of message update (if necessary)
            eventListener.onTextAreaUpdated("******** Game Start, Good Luck! ********");
            eventListener.onTextAreaUpdated("^_^ Please select cards to play ^_^");
        }
    }

    /**
     * Sends the selected cards info to the server
     */
    public void sendSelectedCardsToServer(List<CardVO> selectedCardVOList) {
        // Convert selected CardVO to Card list
        List<selectedCardDTO> playCardDTOs = selectedCardVOList.stream()
                .map(cardVO -> new selectedCardDTO(localPlayer.getId(),cardVO.getSuit(),cardVO.getRank()))
                .toList();

        // Create a JSON string from the selected cards
        String selectedCardsJson = JsonUtil.toJson(playCardDTOs);

        // Build CLIENT_PLAY command
        String playCommand = CommandBuilder.buildCommand(
                CommandType.CLIENT_PLAY,
                selectedCardsJson
        );

        // Send the command to the server via the client controller
        sendPlayCommand(playCommand);

        // Update UI with message
        eventListener.onTextAreaUpdated("You played your selected cards!");
    }

    /**
     * Sends the play command with the selected cards to the server
     *
     * @param playCommand the formatted play command with selected cards
     */
    public void sendPlayCommand(String playCommand) {
        this.clientSendMQ.addMessage(playCommand);
        log.info("PlayCommand ==> clientSendMQ: {}", playCommand);
    }

    public void playerSelectCards() {

    }
}
