package controller;


import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enumeration.CommandType;
import java.util.Objects;
import model.Card;
import model.Deck;
import model.Player;
import model.PlayerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ServerReceiveThread;
import thread.ServerSendThread;
import utils.CommandBuilder;
import utils.JsonUtil;
import utils.MessageBroadcaster;
import utils.ServerReceiveMQ;
import utils.ServerSendMQ;


public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private ServerReceiveMQ serverReceiveMQ;
    private ServerSendMQ serverSendMQ;
    private MessageBroadcaster messageBroadcaster;

    private Socket socket;

    private static final int MAX_PLAYER_NUMBER = 2;

    private static final int MAX_CARDS_NUMBER = 5;

    private List<Player>  playerList;

    private Deck deck;

    public GameController() {
        this.playerList = Collections.synchronizedList(new ArrayList<>());
        serverReceiveMQ = new ServerReceiveMQ();
        serverSendMQ = new ServerSendMQ();
        messageBroadcaster = new MessageBroadcaster(serverSendMQ);
        deck = new Deck();

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
        ServerSendThread serverSendThread = new ServerSendThread (socket, serverSendMQ);
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
        System.out.println("========= Server handleMessage info ========");

        // Split JOIN command to commandType and commandParams
        // eg: UNITCAST JOIN yyd --> commandType: JOIN,
        //                  commandParams: yyd
        String[] parts = message.split(" ");
        String commandType =  parts[0];
        String commandArgs = message.substring(commandType.length()).trim();

        // Handle message according to commandType
        // eg: JOIN, BROADCAST, CLIENT_PLAY ....
        switch (commandType) {
            case "JOIN" -> {
                log.info("------ JOIN COMMAND -----");
                // Creating new player and adding to the player list
                Player newPlayer = new Player(parts[1], socket);
                synchronized (playerList){}
                this.playerList.add(newPlayer);
                log.info("PlayerList Added: [id:{}, name:{}, socket:{}]",
                        newPlayer.getId(), newPlayer.getName(), newPlayer.getSocket());

                // Build WELCOME command.
                // eg:   WELCOME (name)daniel (id)1 (socket)127.0.0.1 (player number)1
                String welcomeCommand = CommandBuilder.buildCommand(
                        CommandType.WELCOME,                   // parts[0]
                        newPlayer.getName(),                  // parts[1]
                        Integer.toString(newPlayer.getId()),  // parts[2]
                        Integer.toString(playerList.size()),  // parts[3]
                        newPlayer.getSocket().getRemoteSocketAddress().toString()  //parts[4]
                );
                messageBroadcaster.broadcastMessage(playerList, welcomeCommand);
                if (playerList.size() >= MAX_PLAYER_NUMBER) {
                    // Step 1: Build the "START" command and broadcast it to all players
                    // This informs all connected clients that the game is officially starting
                    String startCommand = CommandBuilder.buildCommand(CommandType.START);
                    messageBroadcaster.broadcastMessage(playerList, startCommand);

                    // Step 2: Shuffle the deck and deal cards to each player individually
                    // Each player only receives their own private hand
                    deck.shuffle();

                    for (Player player : playerList) {
                        log.info("--------Deal start--------");
                        // Deal MAX_CARDS_NUMBER cards to this player
                        List<Card> hold = deck.deal(MAX_CARDS_NUMBER);
                        log.info("Deal 5 cards:");
                        log.info("Set player hand start");
                        log.info(
                            Objects.toString(hold, "null"));
                        player.setHand(hold);  // Store the hand in the Player object
                        log.info("Set player hand success");


                        // Create a PlayerDTO with the player's ID, name, and private hand
                        PlayerDTO dto = new PlayerDTO(player.getId(), player.getName(), hold);
                        log.info("Player info:");
                        log.info(dto.getPlayerName());
                        log.info(dto.getPlayerHand().toString());

                        // Convert the DTO to JSON format for network transmission
                        String playerJson = "START " + JsonUtil.toJson(dto);
                        log.info("Player json");
                        log.info(playerJson);

                        // Send the JSON message to the individual player via socket
                        // Note: This message is private — only this player receives their hand
                        player.sendMessage(playerJson);
                        log.info("Player info sent success");
                    }
                }




            }

        }
    }
}


