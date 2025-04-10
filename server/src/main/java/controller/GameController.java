package controller;


import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import enumeration.CommandType;
import model.Deck;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ServerReceiveThread;
import thread.ServerSendThread;
import utils.CommandBuilder;
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

    private List<Player>  playerList;

    public GameController() {
        this.playerList = new ArrayList<>();
        serverReceiveMQ = new ServerReceiveMQ();
        serverSendMQ = new ServerSendMQ();
        messageBroadcaster = new MessageBroadcaster(serverSendMQ);

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
                String startCommand = "";
                if (playerList.size() >= MAX_PLAYER_NUMBER) {
                    // Build the "START" command to indicate the game start
                    startCommand = CommandBuilder.buildCommand(
                            CommandType.START                   // parts[0] - Command type is "START"
                    );
                }
                // Broadcast the "START" command to all players to initiate the game
                messageBroadcaster.broadcastMessage(playerList, startCommand);
//                // Game starts when players are ready
//                if(playerList.size() >= MAX_PLAYER_NUMBER) {
//                    // Broadcasting game start message
//                    String startMessage = CommandBuilder.buildCommand(CommandType.BROADCAST, "Game Start!");
//                    MessageBroadcaster.broadcastMessage(playerList,startMessage);
//
//                    // Shuffling and Dealing cards to players
//                    deck.shuffle();
//                    log.info("Initialized deck and shuffled");
//                    for (Player player : playerList) {
//                        // Dealing cards
//                        player.getCardsFromDeck(deck, 5);
//
//                        // Sorting cards in descending order according to card rank
//                        List<Card> hand = player.getHand();
//                        Collections.sort(hand, Comparator
//                                .comparingInt((Card c) -> c.getRank().getValue())
//                                .reversed()
//                        );
//                    }
//                    log.info("Finished dealing cards to all players: {}", playerList);
            }
        }
    }
}


