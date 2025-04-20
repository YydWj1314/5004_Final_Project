package model.DTO;

import model.JavaBean.Card;

import java.util.List;

public class PlayerRankDTO {

  private int rank;
  private int playerId;
  private String playerName;
  private List<Card> playedCards;

  /**
   * Constructor of PlayerRankDTO
   *
   * @param rank        rank of the player
   * @param playerId    id of the player
   * @param playerName  name of the player
   * @param playedCards cards played by player
   */
  public PlayerRankDTO(int rank, int playerId, String playerName, List<Card> playedCards) {
    this.rank = rank;
    this.playerId = playerId;
    this.playerName = playerName;
    this.playedCards = playedCards;
  }

  /**
   * Constructor without params
   */
  public PlayerRankDTO() {
  }

  // Getters and Setters

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public List<Card> getPlayedCards() {
    return playedCards;
  }

  public void setPlayedCards(List<Card> playedCards) {
    this.playedCards = playedCards;
  }

  @Override
  public String toString() {
    return "PlayerRankDTO{" +
        "rank=" + rank +
        ", playerId=" + playerId +
        ", playerName='" + playerName + '\'' +
        ", playedCards=" + playedCards +
        '}';
  }
}
