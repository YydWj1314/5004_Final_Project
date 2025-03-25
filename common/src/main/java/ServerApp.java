import enumeration.CardRank;
import enumeration.CardSuit;

public class ServerApp {
    public static void main(String[] args) {
        Card card1 = new Card(CardSuit.CLUBS, CardRank.ACE);
        System.out.println(card1);

        Deck deck1 = new Deck();
        System.out.println(deck1);
        deck1.shuffle();
        System.out.println(deck1);
        deck1.deal(50);
        System.out.println(deck1);

        Player player1 = new Player("Trump");
        Player player2 = new Player("Donald");
        System.out.println(player1);
        System.out.println(player2);

    }
}
