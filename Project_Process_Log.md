# Summary of PokerGame Class Implementation @ 2025/03/24 by ZMT


This document summarizes the implementation of the core classes used in our card game application:

- **CardRank**
- **CardSuit**
- **Card**
- **Deck**
- **Player**

---

## CardRank

`CardRank` is an enumeration (`enum`) representing the ranks of playing cards.

- **Ranks**:
  - **Numbers**: Two (`2`) through Ten (`10`)
  - **Face Cards**: Jack (`J`), Queen (`Q`), King (`K`)
  - **Ace**: (`A`)
- **Attributes**:
  - `symbol`: A string symbol for the rank (e.g., `"A"` for Ace).
  - `rankValue`: An integer value associated with the rank for comparison purposes.

---

## CardSuit

`CardSuit` is an enumeration (`enum`) representing the suits of playing cards.

- **Suits**:
  - **Clubs** (`♣`)
  - **Diamonds** (`♦`)
  - **Hearts** (`♥`)
  - **Spades** (`♠`)
- **Attributes**:
  - `name`: The name of the suit (e.g., `"HEARTS"`).
  - `symbol`: A string symbol representing the suit (e.g., `"♥"` for Hearts).

---

## Card

The `Card` class represents an individual playing card, defined by a `CardRank` and a `CardSuit`.

- **Attributes**:
  - `cardSuit`: The suit of the card (`CardSuit`).
  - `cardRank`: The rank of the card (`CardRank`).
- **Key Methods**:
  - **Constructor**: Initializes a card with a specific suit and rank.
  - `getCardSuit()`: Returns the suit of the card.
  - `getCardRank()`: Returns the rank of the card.
  - `compareTo()`: Compares this card to another card based on rank and suit.
  - `equals()`: Checks if two cards are equal based on suit and rank.
  - `hashCode()`: Generates a hash code for the card.
  - `toString()`: Returns a string representation of the card (e.g., `"♥A"` for Ace of Hearts).

---

## Deck

The `Deck` class represents a standard deck of 52 playing cards.

- **Attributes**:
  - `cards`: A list containing all the `Card` objects in the deck.
- **Key Methods**:
  - **Constructor**: Initializes the deck with 52 unique cards in order.
  - `initDeck()`: Clears the deck and adds all 52 unique cards.
  - `shuffle()`: Randomizes the order of the cards in the deck.
  - `deal(int n)`: Deals (removes and returns) the top `n` cards from the deck.
  - `isEmpty()`: Checks if the deck is empty.
  - `size()`: Returns the number of cards remaining in the deck.
  - `toString()`: Returns a string representation of the deck.

---

## Player

The `Player` class represents a player participating in the card game.

- **Attributes**:
  - `userId`: A unique identifier for the player.
  - `userName`: The name of the player.
  - `playerHand`: A list of `Card` objects representing the player's hand.
  - `handRank`: The evaluated rank or strength of the player's hand.
  - `IDCOUNTER`: A static counter to assign unique IDs to each player.
- **Key Methods**:
  - **Constructor**: Creates a player with a unique ID and username.
  - `getUserId()`: Returns the player's unique ID.
  - `getUserName()`: Returns the player's username.
  - `getPlayerHand()`: Returns the player's hand.
  - `setPlayerHand(List<Card>)`: Sets the player's hand.
  - `getHandRank()`: Returns the rank of the player's hand.
  - `setHandRank(String)`: Sets the rank of the player's hand.
  - `displayHand()`: Displays the player's hand.
  - `toString()`: Returns a string representation of the player's information.

---

## Overview

- **Purpose**: These classes collectively form the foundation of a card game application.
- **Functionality**:
  - **Card and Enumerations**:
    - Cards are defined with specific ranks and suits using `CardRank` and `CardSuit` enums.
    - Cards can be compared, displayed, and managed within collections.
  - **Deck Management**:
    - A `Deck` can be initialized, shuffled, and used to deal cards to players.
    - It handles the core functionality of card distribution.
  - **Player Representation**:
    - `Player` objects hold personal information and their current hand of cards.
    - Players can interact with the deck and participate in the game logic.

---

## How It Works Together

1. **Initialization**:
   - A `Deck` is created and initialized with 52 cards.
   - The deck can be shuffled to randomize the order of the cards.

2. **Creating Players**:
   - `Player` instances are created with unique IDs and usernames.
   - Each player has an empty hand initially.

3. **Dealing Cards**:
   - The deck deals cards to players using the `deal()` method.
   - Players receive cards and add them to their hands.

4. **Gameplay**:
   - Players can display their hands using `displayHand()`.
   - Game logic can be applied to evaluate hands using `handRank`.

5. **Testing**:
   - Implement some tests for Card, Deck and Player classes to ensure their correctness

---

## Conclusion

This set of classes forms the foundation for building card game applications. 
They provide basic functionalities such as creating a deck, shuffling, dealing cards, 
and representing players and their hands.

- **Next Steps** : Implement game-specific logic, such as hand evaluations (e.g., poker hands), 
- scoring, and game flow control.


# Summary of PokerGame Java Swing-based UI Implementation @ 2025/03/26 By YQ
This Java Swing-based client UI consists of three main components:

1. CardPanel: A JPanel subclass that loads and displays a background image for the main game interface.

2. LoginFrame: A JFrame subclass that serves as the login window, allowing users to enter a username and transition to the main game frame.

3. MainFrame: The primary game window that integrates CardPanel, a system message area, and a play button.

The UI is structured in a way that first shows the login screen (LoginFrame), and upon successful login, 
transitions to the main game screen (MainFrame), which contains a background panel (CardPanel) and other interactive elements.


# Summary of SocketHandler and GameServer @ 2025/03/28 By ZYL

1. SocketHandler implementation
Implemented a SocketHandler utility class to simplify socket communication in our project. 
This class manages Socket connections by wrapping DataInputStream and DataOutputStream for UTF-8 
encoded message transfers.
It includes encapsulated methods like sendMessage() (auto-flush) and receiveMessage() (returns null on failure),

2. GameServer implementation
The GameServer class forms the core network infrastructure for our multiplayer game, 
running continuously on localhost (port 10087) to handle three essential functions: 
(1) accepting incoming player connections through a ServerSocket infinite loop, 
(2) tracking all connected players by logging their IP addresses (e.g., "New client connected: /127.0.0.1")
(3) providing the foundation for future game logic integration including message exchange via SocketHandler, 
  thread pool management for multiple players, and game state synchronization.
3. next step
Implement the ClientSendThread

# update @ 2025/04/2 by YYD
- Create ClientSendThread.class;
- Create ClientReceiveThread.class;
- Create ServerReceiveThread.class;
- Start thread and receive messages;
- 
# update @ 2025/04/4 by YYD
- update ClientController
- update MainFrame and LoginFrame
- let LoginFrame only pass username to MainFrame
- Move connection with server by socket from LoginFrame to ClientController
- Create and start message thread in ClientController to handle message
- 
# update @ 2025/04/07 by ZMT
- update GameController
- create player instances based on incoming message
- broadcast messages back to connected players
- initialize a deck for the game, will add operations later
- move model package under server directory
- update ClientController and MainFrame

# update @ 2025/04/08 by YYD
- bug fixed in mainframe JTextField, now can show broadcasting message
- rename MessageBuffer to ReceiveMQ / SendMQ
- update structure: add ReceiveMQ / SendMQ in both backend and front end
  -  now using this structure to send msg:
  ```
  while (true) {
  String message = messageQueue.take(); // This line blocks (waits)
  socketHandler.sendMessage(message);   // This sends the message
  ```
  - add ClientSendThread to take msg from sendMQ and call socketHandler to send msg
- update Command system: now message need to be encapsulated to standard cmd
  - format：CommandType + string, eg: JOIN yyd
- update MessageBroadCaster util: broadcasting msg to every player's client by sendMQ
- Update handleMessage() in GC & CC:  JOIN & WELCOME 

# update @ 2025/04/09 by YQ
- update GameController, ClientController, MainFrame
- add javadoc note
- Added an enum class: Command.Start.
- Wrote the corresponding handling code in ClientController for this enum.
- Implemented displaying "Game Started" on the whiteboard at the bottom right of the game 
once the required number of players is reached.

# update @ 2025/04/12 by YQ
- Added the logic for dealing cards after reaching the maximum number of players in GameController, 
and encapsulated the player information into a newly created PlayerDTO class to send to the frontend for UI updates.
- Enhanced the original Card class by adding coordinate properties and an image property for each card.
- Implemented the logic in MainFrame for each player to display their own hand, and adjusted the display order of the cards.
- Ensured that each player can only see their own cards.

# update @ 2025/04/15 by JL
- Implemented card selection in MainFrame by adding a CardClickListener class. (maybe too crowded in the MainFrame class)
- Added animation to selected cards(3 cards).
- Displaying selected cards in the center of the window after pressed play button.
- Added CLIENT_PLAY and PLAY_RESULT command type.
- Updated ClientController and GameController to handle new command types and card play functionality.

}