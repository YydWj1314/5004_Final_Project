package view;

import controller.ClientController;

import controller.ClientControllerListener;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

import model.Card;
import model.PlayerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientReceiveThread;
import thread.ClientSendThread;

import java.net.Socket;

/**
 * The {@code MainFrame} class represents the main game window.
 * It contains a background panel, a system message area, and a play button.
 */
public class MainFrame extends JFrame implements ClientControllerListener {
    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    private CardPanel cardPanel;

    private JTextArea systemMessageArea;

    private JButton playButton;

    private ClientSendThread clientSendThread;

    private ClientReceiveThread clientReceiveThread;

    private String message;

    private ClientController clientController;
//  private ClientSendThread clientSendThread;
//  private ClientReceiveThread clientReceiveThread;
//  private model.Player currentPlayer = new model.Player();
//  private List<CardVO> cardVOList = new ArrayList<>();
//  private List<CardVO> selectedCardVOList = new ArrayList<>();

    /**
     * Constructs the {@code MainFrame} and initializes UI components.
     */
    public MainFrame(String message) throws IOException {

        this.message = message;

        // Initialize ClientController
        clientController = new ClientController(message, this);

        // Setting window attributes
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        // add CardPanel
        cardPanel = new CardPanel();
        cardPanel.setBounds(0, 0, 1200, 700);
        cardPanel.setLayout(null);
        this.add(cardPanel);


        // Adding System message area
        systemMessageArea = new JTextArea();
        systemMessageArea.setEditable(false); // read only
        systemMessageArea.setLineWrap(true);  //wrap text
        JScrollPane scrollPane = new JScrollPane(
                systemMessageArea);
        scrollPane.setBounds(600, 475, 500, 150);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cardPanel.add(scrollPane);


        // Initializing `playButton`
        playButton = new JButton("Play");
        playButton.setBounds(200, 400, 100, 50);
        cardPanel.add(playButton);

        this.setVisible(true);
        log.info("MainFrame displayed successfully");
    }

    @Override
    public void onTextAreaUpdated(String message, Object... args) {
        SwingUtilities.invokeLater(() -> systemMessageArea.append(message + "\n"));
        log.info("TextFiled Update: {}", message );
    }

    @Override
    public void onGameStart() {
        // Use invokeLater to ensure the UI updates happen on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Append the game start message to the system message area
            systemMessageArea.append("Game Started!\n");
            // Enable the play button
            playButton.setEnabled(true);
        });
        log.info("Game Start triggered from Server.");
    }

    @Override
    public void onPlayerHandUpdated(Object playerDTO) {
        // Ensure that UI updates are done on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Cast the playerDTO to PlayerDTO
            PlayerDTO player = (PlayerDTO) playerDTO;

            // Clear the existing cards from the panel before adding new ones
            cardPanel.removeAllCards();

            int xOffset = 250;
            int cardWidth = 80;
            int cardHeight = 120;

            // Display with order
            List<Card> playerHands = player.getPlayerHand();
            Collections.sort(playerHands);
            Collections.reverse(playerHands);

            // Update the card panel with the new player's hand
            // Loop through the player's hand and add each card to the card panel
            for (Card card : playerHands) {
                // Load and scale image
                Image originalImage = cardPanel.loadCardImage(card);
                Image scaledImage = originalImage.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                // Create a JLabel with the card image or text
                JLabel cardLabel = new JLabel(scaledIcon);
                // Optionally, you can set the bounds or position of each card
                cardLabel.setBounds(xOffset, 500, cardWidth, cardHeight);

                xOffset -= 30;
                // Add the card label to the card panel
                cardPanel.add(cardLabel);
            }

            // Revalidate and repaint the cardPanel to ensure the UI updates correctly
            cardPanel.revalidate();
            cardPanel.repaint();
        });

        log.info("Player hand updated: {}", playerDTO);
    }



}
