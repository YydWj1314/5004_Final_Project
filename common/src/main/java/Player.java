import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
  private int userId; //unique identifier for the player
  private String userName;
  private List<Card> playerHand; //cards currently held by the player
  private String handRank; // evaluated rank of the player's hand
  private static int IDCOUNTER = 0;

  public Player(String userName) {
    // ensure player created each time with a userID increased by 1
    this.userId = IDCOUNTER++;
    this.userName = userName;
    this.playerHand = new ArrayList<Card>(); //sets up empty lists for player's hand
    this.handRank = null;
  }

  public int getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public List<Card> getPlayerHand() {
    return playerHand;
  }

  public void setPlayerHand(List<Card> playerHand) {
    this.playerHand = playerHand;
  }

  public String getHandRank() {
    return handRank;
  }

  public void setHandRank(String handRank) {
    this.handRank = handRank;
  }

  // Display the player's hand
  public void displayHand() {
    System.out.println(userId + " hand: " + playerHand);
  }

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