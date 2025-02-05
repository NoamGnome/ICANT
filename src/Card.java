import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private String backImageFileName;
    private boolean show;
    private BufferedImage image;
    private Rectangle cardBox;
    private boolean highlight;
    public static ArrayList<Card> deck = new ArrayList<Card>();
    public static ArrayList<Card> hand = new ArrayList<Card>();

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.imageFileName = "images/card_"+suit+"_"+value+".png";
        this.show = true;
        this.backImageFileName = "images/card_back.png";
        this.image = readImage();
        this.cardBox = new Rectangle(-100, -100, image.getWidth(), image.getHeight());
        this.highlight = false;
    }

    public Rectangle getCardBox() {
        return cardBox;
    }

    public String getSuit() {
        return suit;
    }

    public void setRectangleLocation(int x, int y) {
        cardBox.setLocation(x, y);
    }

    public String getValue() {
        return value;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String toString() {
        return suit + " " + value;
    }


    public void flipHighlight() {
        highlight = !highlight;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage readImage() {
        try {
            BufferedImage image;
            if (show) {
                image = ImageIO.read(new File(imageFileName));
            }
            else {
                image = ImageIO.read(new File(backImageFileName));
            }
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
    public static void swap(int i) {
        if (deck.size() > 1) {
            hand.set(i, deck.getLast());
            deck.removeLast();
        } else {
            if (deck.size() == 1) {
                hand.set(i, deck.getFirst());
                deck.removeFirst();
            } else {
                hand.remove(i);
            }
        }
    }

    public static ArrayList<Card> buildDeck() {
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "A", "J", "K", "Q"};
        for (String s : suits) {
            for (String v : values) {
                Card c = new Card(s, v);
                deck.add(c);
            }
        }
        return deck;
    }
    public static boolean anotherPlayIsPossible() {
        for (int a = 0; a < hand.size(); a++) {
            for (Card card : hand) {
                String c1 = hand.get(a).getValue();
                String c2 = card.getValue();
                int val1 = 0;
                int val2 = 0;
                if (!c1.equals("K") && !c1.equals("Q") && !c1.equals("J")) {
                    if (!c2.equals("K") && !c2.equals("Q") && !c2.equals("J")) {
                        if (c1.equals("A")) {
                            val1 = 1;
                        }
                        if (c2.equals("A")) {
                            val2 = 1;
                        }
                        if (!c1.equals("A")) {
                            val1 = Integer.parseInt(c1);
                        }
                        if (!c2.equals("A")) {
                            val2 = Integer.parseInt(c2);
                        }
                    }
                }
                if (val1 + val2 == 11) {
                    return true;
                }
            }
        }
        for (int c = 0; c < hand.size(); c++) {
            for (int d = 0; d < hand.size(); d++) {
                for (Card card : hand) {
                    String c1 = hand.get(c).getValue();
                    String c2 = hand.get(d).getValue();
                    String c3 = card.getValue();
                    if (c1.equals("K") || c1.equals("Q") || c1.equals("J")) {
                        if (c2.equals("K") || c2.equals("Q") || c2.equals("J")) {
                            if (c3.equals("K") || c3.equals("Q") || c3.equals("J")) {
                                if (!c1.equals(c2) && !c1.equals(c3) && !c2.equals(c3)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static ArrayList<Card> buildHand() {
        deck = Card.buildDeck();
        for (int i = 0; i < 9; i++) {
            int r = (int)(Math.random()*deck.size());
            Card c = deck.remove(r);
            hand.add(c);
        }
        return hand;
    }
}
