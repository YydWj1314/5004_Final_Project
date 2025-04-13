package model;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import utils.SocketHandler;

/**
 * Represents a player in the game with a unique ID, username, hand of cards, and hand rank.
 */
public class Player {
  private int playerId; //unique identifier for the player
  private String playerName;
  private List<Card> playerHand; //cards currently held by the player
  private String handRank; // evaluated rank of the player's hand
  private static int IDCOUNTER = 0;
  private Socket socket;
  private SocketHandler socketHandler;

  /**
   * Constructs a new model.Player with the specified username.
   * Each player is assigned a unique player ID.
   *
   * @param playerName the username of the player
   */
  public Player(String playerName) {
    // Ensure each player is created with a unique playerId incremented by 1
    this.playerId = IDCOUNTER++;
    this.playerName = playerName;
    this.playerHand = new ArrayList<Card>(); //sets up empty lists for player's hand
    this.handRank = null;
  }

  public Player(String playerName, Socket socket) {
    // Ensure each player is created with a unique playerId incremented by 1
    this.playerId = IDCOUNTER++;
    this.playerName = playerName;
    this.playerHand = new ArrayList<Card>(); //sets up empty lists for player's hand
    this.handRank = null;
    this.socket = socket;
    this.socketHandler = new SocketHandler(socket);
  }

  /**
   * @return the player ID of the player
   */
  public int getId() {
    return playerId;
  }

  /**
   * @return the username of the player
   */
  public String getName() {
    return playerName;
  }

  /**
   * Returns the list of cards currently held by the player.
   *
   * @return the player's hand of cards
   */
  public List<Card> getHand() {
    return playerHand;
  }

  /**
   * Returns the socket of the player.
   *
   * @return socket of the player
   */
  public Socket getSocket() {
    return socket;
  }


  /**
   * Sets player's id.
   *
   * @param playerId player id
   */
  public void setId(int playerId) {
    this.playerId = playerId;
  }

  /**
   * Sets the player's hand to the specified list of cards.
   *
   * @param playerHand the list of cards to set as the player's hand
   */
  public void setHand(List<Card> playerHand) {
    this.playerHand = playerHand;
  }

  /**
   * @return the hand rank of the player
   */
  public String getHandRank() {
    return handRank;
  }

  /**
   * Sets the hand rank of the player to the specified value.
   *
   * @param handRank the hand rank to set
   */
  public void setHandRank(String handRank) {
    this.handRank = handRank;
  }

  /**
   * Displays the player's hand to the console.
   */
  public void displayHand() {
    System.out.println(playerId + " hand: " + playerHand);
  }

  /**
   * Returns a string representation of the player, including player ID, username, hand, and hand rank.
   *
   * @return a string representation of the player
   */
  @Override
  public String toString() {
    return "model.Player{" +
        "playerId=" + playerId +
        ", playerName='" + playerName + '\'' +
        ", playerHand=" + playerHand +
        ", handRank='" + handRank + '\'' +
        '}';
  }

  /**
   * @param message
   */
  public void sendMessage(String message) {
    socketHandler.sendMessage(message);
  }


}