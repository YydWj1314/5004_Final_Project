package view;

import java.net.URL;
import javax.swing.*;
import java.awt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The {@code CardPanel} class extends {@link JPanel} and is responsible for
 * displaying the background image in the main game window.
 */
public class CardPanel extends JPanel {
  private static final Logger log = LoggerFactory.getLogger(CardPanel.class);
  private Image backgroundImage;

  /**
   * Constructs a {@code CardPanel} and loads the background image.
   * If the image is not found, a runtime exception is thrown.
   */
  public CardPanel() {
    // Try loading the image
    URL imageUrl = getClass().getResource("/bg1.jpg");
    if (imageUrl == null) {
      throw new RuntimeException("Image not found! Check the path.");
    }
    backgroundImage = new ImageIcon(imageUrl).getImage();
  }

  /**
   * Overrides the {@code paintComponent} method to draw the background image.
   *
   * @param g the {@link Graphics} object used for painting
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // check null
    if (backgroundImage != null) {
      g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    } else {
      log.error("Image Unloaded, check the URL");
    }
  }
}