package controller;


import static utils.SocketHandler.log;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import model.Deck;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MessageBroadcaster;
import utils.ServerMessageBuffer;


public class GameController {

  private static final int MAX_PLAYERS = 2;
  private static final Logger log = LoggerFactory.getLogger(GameController.class);
  private List<Player> playerList = new ArrayList<>();
  private Deck deck = new Deck();

  public GameController() {
    startMessageThread();
  }


  /**
   * Create new thread handing message receiving from backend
   * and start the thread
   */
  private void startMessageThread() {
    // Starting Message thread and taking messages
    new Thread(() -> {
      log.info("GameController Listening for Messages...");
      while (true) {
        ServerMessageBuffer.MessageEntry entry = ServerMessageBuffer.takeMessage();
        if (entry != null) {
          log.info("GC has taken message: {}, {}", entry.message, entry.socket);
          // Handling messages
          handleMessage(entry.message, entry.socket);
        }
      }
    }).start();
  }

  private void handleMessage(String message, Socket socket) {
    System.out.println("!!!!!!Message: " + message); //for test

    Player player1 = new Player(message, socket); //create a player

    playerList.add(player1);
    MessageBroadcaster.broadcastMessage(playerList, "Hello! Group 6");

  }

  }


