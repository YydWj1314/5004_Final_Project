package model.JavaBean;

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
  private static int IDCOUNTER = 0;
  private Socket socket;
  private SocketHandler socketHandler;
  private List<Card> selectedCards;
  private int rank = -1;

  /**
   * Constructor for frontend Need to be set
   *
   * @param playerName the username of the player
   */
  public Player(String playerName) {
    this.playerName = playerName;
    this.playerHand = new ArrayList<Card>(); //sets up empty lists for player's hand
  }


  /**
   * Player Constructor for Backend Each player is assigned a unique player ID
   *
   * @param playerName the username of the player
   * @param socket     socket to connect with frontend
   */
  public Player(String playerName, Socket socket) {
    // Ensure each player is created with a unique playerId incremented by 1
    this.playerId = IDCOUNTER++;
    this.playerName = playerName;
    this.playerHand = new ArrayList<>(); //sets up empty lists for player's hand
    this.socket = socket;
    this.socketHandler = new SocketHandler(socket);
    this.selectedCards = new ArrayList<>();
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
   * @return cards selected and played by player
   */
  public List<Card> getSelectedCards() {
    return selectedCards;
  }

  /**
   * Cards selected and played by player
   *
   * @param selectedCards
   */
  public void setSelectedCards(List<Card> selectedCards) {
    this.selectedCards = selectedCards;
  }

  /**
   * @return rank of the player
   */
  public int getRank() {
    return rank;
  }

  /**
   * @param rank rank of the player
   */
  public void setRank(int rank) {
    this.rank = rank;
  }

  @Override
  public String toString() {
    return "Player{" +
        "playerId=" + playerId +
        ", playerName='" + playerName + '\'' +
        ", playerHand=" + playerHand +
        ", selectedCards=" + selectedCards +
        '}';
  }
}