package view;

import controller.ClientController;

import controller.ControllerEventListener;
import controller.EventListener;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

import model.Card;
import model.CardVO;
import model.PlayerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientReceiveThread;
import thread.ClientSendThread;

import enumeration.CommandType;
import utils.CommandBuilder;
import utils.JsonUtil;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The {@code MainFrame} class represents the main game window.
 * It contains a background panel, a system message area, and a play button.
 */
public class MainFrame extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    private CardPanel cardPanel;

    private JTextArea systemMessageArea;

    private JButton playButton;

    private String message;

    private ClientController clientController;

    private List<CardVO> cardVOList = new ArrayList<>();

  private List<CardVO> selectedCardVOList = new ArrayList<>();
//  private model.Player currentPlayer = new model.Player();


    /**
     * Constructs the {@code MainFrame} and initializes UI components.
     */
    public MainFrame(String message) throws IOException {

        this.message = message;

        // Initialize ClientController
        ControllerEventListener eventListener = new ControllerEventListener(this);
        clientController = new ClientController(message, eventListener);

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
        Font font = new Font("Arial", Font.PLAIN, 15);
        systemMessageArea.setFont(font);


        // Initializing `playButton`
        playButton = new JButton("Play");
        playButton.setBounds(200, 400, 100, 50);
        cardPanel.add(playButton);

        playButton.addActionListener(e -> handlePlayButtonClick());

        this.setVisible(true);
        log.info("MainFrame displayed successfully");
    }

    // TODO update
    public void updateTextField(String message, Object... args){
        log.info("Updating Text Area..." );
        SwingUtilities.invokeLater(() -> systemMessageArea.append(message + "\n"));

    }

    // TODO update
    public void updateCardArea(List<CardVO> cardVOList, Object... args){
        log.info("Updating Card Area...");
        SwingUtilities.invokeLater(() -> {
            this.cardPanel.revalidate();
            this.cardPanel.repaint();

            // Creating Timer，interval 300ms
            Timer timer = new Timer(100, null);
            final int[] i = {0};             // counter
            timer.addActionListener(e -> {
                if (i[0] < cardVOList.size()) {
                    CardVO cardVO = cardVOList.get(i[0]);
                    cardVO.setUp(true);
                    this.cardVOList.add(cardVO);

                    int xPos = 300 + 30 * i[0]++;
                    int yPos = 450;
                    cardVO.setBounds(xPos, yPos, 150, 200);

                    // Adding mouse press event
                    cardVO.addMouseListener(new CardClickListener(cardVO, xPos, yPos));


                    this.cardPanel.add(cardVO);

                    this.cardPanel.setComponentZOrder(cardVO, 0);

                    this.cardPanel.revalidate();
                    this.cardPanel.repaint();
                } else {
                    ((Timer) e.getSource()).stop(); // stop Timer
                }
            });

            timer.start();
        });
    }

    /**
     * Handles the play button click event
     */
    private void handlePlayButtonClick() {
        log.info("Play button clicked");

        // Check if exactly 3 cards are selected
        if (selectedCardVOList.size() != 3) {
            updateTextField("Please select exactly 3 cards to play!");
            return;
        }

        // Display the selected cards in the middle
        displaySelectedCardsInMiddle();

        // Send the selected cards to the server
        sendSelectedCardsToServer();
    }

    /**
     * Displays the selected cards in the middle of the screen
     */
    private void displaySelectedCardsInMiddle() {
        // Hide all cards from hand first
        for (CardVO cardVO : cardVOList) {
            cardVO.setVisible(false);
        }

        // Display the selected cards in the middle
        int middleX = this.getWidth() / 2 - 150; // Center point minus half the width of 3 cards
        int middleY = this.getHeight() / 2 - 100; // Middle of the screen

        for (int i = 0; i < selectedCardVOList.size(); i++) {
            CardVO selectedCard = selectedCardVOList.get(i);
            // Make a copy of the card for display in the middle
            CardVO displayCard = new CardVO(selectedCard.getSuit(), selectedCard.getRank(), true);
            displayCard.setBounds(middleX + i * 100, middleY, 150, 200);
            this.cardPanel.add(displayCard);
            this.cardPanel.setComponentZOrder(displayCard, 0);
        }

        this.cardPanel.revalidate();
        this.cardPanel.repaint();
    }

    /**
     * Sends the selected cards to the server
     */
    private void sendSelectedCardsToServer() {
        // Convert selected CardVO to Card list
        List<Card> selectedCards = selectedCardVOList.stream()
            .map(cardVO -> new Card(cardVO.getSuit(), cardVO.getRank()))
            .collect(Collectors.toList());

        // Create a JSON string from the selected cards
        String selectedCardsJson = JsonUtil.toJson(selectedCards);

        // Build CLIENT_PLAY command
        String playCommand = CommandBuilder.buildCommand(
            CommandType.CLIENT_PLAY,
            selectedCardsJson
        );

        // Send the command to the server via the client controller
        clientController.sendPlayCommand(playCommand);

        // Update UI with message
        updateTextField("You played your selected cards!");
    }

    public class CardClickListener extends MouseAdapter {
        private final CardVO cardVO;
        private final int originalX;
        private final int originalY;
        private static final int MOVE_UP_PIXELS = 20;

        public CardClickListener(CardVO cardVO, int originalX, int originalY) {
            this.cardVO = cardVO;
            this.originalX = originalX;
            this.originalY = originalY;
            cardVO.saveOriginalY(originalY);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Check if we can select this card
            if (!cardVO.isSelected() && selectedCardVOList.size() >= 3) {
                // Already have 3 cards selected, do nothing
                return;
            }

            // Toggle selection status
            cardVO.setSelected(!cardVO.isSelected());

            if (cardVO.isSelected()) {
                cardVO.setLocation(originalX, originalY - MOVE_UP_PIXELS);
                selectedCardVOList.add(cardVO);
            } else {
                cardVO.setLocation(originalX, originalY);
                selectedCardVOList.remove(cardVO);
            }

            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }

}
