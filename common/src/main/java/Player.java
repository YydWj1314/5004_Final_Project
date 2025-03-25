import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a player in the game with a unique ID, username, hand of cards, and hand rank.
 */
public class Player {
  private int userId; //unique identifier for the player
  private String userName;
  private List<Card> playerHand; //cards currently held by the player
  private String handRank; // evaluated rank of the player's hand
  private static int IDCOUNTER = 0;

  /**
   * Constructs a new Player with the specified username.
   * Each player is assigned a unique user ID.
   *
   * @param userName the username of the player
   */
  public Player(String userName) {
    // Ensure each player is created with a unique userId incremented by 1
    this.userId = IDCOUNTER++;
    this.userName = userName;
    this.playerHand = new ArrayList<Card>(); //sets up empty lists for player's hand
    this.handRank = null;
  }

  /**
   * @return the user ID of the player
   */
  public int getUserId() {
    return userId;
  }

  /**
   * @return the username of the player
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Returns the list of cards currently held by the player.
   *
   * @return the player's hand of cards
   */
  public List<Card> getPlayerHand() {
    return playerHand;
  }

  /**
   * Sets the player's hand to the specified list of cards.
   *
   * @param playerHand the list of cards to set as the player's hand
   */
  public void setPlayerHand(List<Card> playerHand) {
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
    System.out.println(userId + " hand: " + playerHand);
  }

  /**
   * Returns a string representation of the player, including user ID, username, hand, and hand rank.
   *
   * @return a string representation of the player
   */
  @Override
  public String toString() {
    return "Player{" +
        "userId=" + userId +
        ", userName='" + userName + '\'' +
        ", playerHand=" + playerHand +
        ", handRank='" + handRank + '\'' +
        '}';
  }
}