package view;

import java.net.URL;
import model.Card;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code CardPanel} class extends {@link JPanel} and is responsible for
 * displaying the background image and card images in the main game window.
 */
public class CardPanel extends JPanel {
  private static final Logger log = LoggerFactory.getLogger(CardPanel.class);
  private Image backgroundImage;

  // List of cards to be displayed
  private List<Card> cardList;

  /**
   * Constructs a {@code CardPanel} and loads the background image.
   * Initializes the card list to hold cards for the game.
   */
  public CardPanel() {
    // Try loading the image
    URL imageUrl = getClass().getResource("/bg1.jpg");
    if (imageUrl == null) {
      throw new RuntimeException("Image not found! Check the path.");
    }
    backgroundImage = new ImageIcon(imageUrl).getImage();

    // Initialize the list to hold cards
    this.cardList = new ArrayList<>();
  }

  /**
   * Overrides the {@code paintComponent} method to draw the background image and cards.
   *
   * @param g the {@link Graphics} object used for painting
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw background image
    if (backgroundImage != null) {
      g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    } else {
      log.error("Image Unloaded, check the URL");
    }

    // Draw each card in the card list
    if (!cardList.isEmpty()) {
      int xOffset = 50; // Starting X position for cards
      int yOffset = 200; // Starting Y position for cards
      for (Card card : cardList) {
        // Load the image for each card
        Image cardImage = loadCardImage(card);
        if (cardImage != null) {
          g.drawImage(cardImage, xOffset, yOffset, 100, 150, this);  // Draw card image
          xOffset += 120; // Move to next card's X position
        }
      }
    }
  }

  /**
   * Loads the image for a given card based on its rank and suit.
   *
   * @param card the card whose image needs to be loaded
   * @return the image of the card, or null if not found
   */
  Image loadCardImage(Card card) {
    String imagePath = "/pokers/" + card.getCardRank().toString() + "_of_" + card.getCardSuit().getName().toLowerCase() + ".png";
    ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
    return icon.getImage();
  }

  /**
   * Adds a card to the list of cards to be displayed.
   *
   * @param card the card to be added
   */
  public void addCard(Card card) {
    this.cardList.add(card);
    repaint();  // Repaint to update the display
  }

  /**
   * Removes all cards from the list and repaints the panel.
   */
  public void removeAllCards() {
    this.cardList.clear();
    repaint();  // Repaint to update the display after removing cards
  }
}
