# Summary of PokerGame Class Implementation

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

This set of classes forms the foundation for building card game applications. They provide basic functionalities such as creating a deck, shuffling, dealing cards, and representing players and their hands.

- **Next Steps** : Implement game-specific logic, such as hand evaluations (e.g., poker hands), scoring, and game flow control.

