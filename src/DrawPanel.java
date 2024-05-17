import org.w3c.dom.css.Rect;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    private final Rectangle button;
    private final Rectangle button1;
    private boolean invalid = false;
    private boolean hasWon = false;
    private boolean hasLost = false;


    public DrawPanel() {
        button = new Rectangle(159, 330, 160, 26);
        button1 = new Rectangle(369, 23, 87, 20);

        this.addMouseListener(this);
        hand = Card.buildHand();
    }

    protected void paintComponent(Graphics g) {
        if (Card.deck.isEmpty() && Card.hand.isEmpty()) {
            hasWon = true;
        }
        if (!Card.anotherPlayIsPossible() && !(Card.deck.isEmpty()) && !(Card.hand.isEmpty())) {
            hasLost = true;
        }
        super.paintComponent(g);
        int x = 135;
        int y = 30;
        int count = 0;
        for (Card c : hand) {
            if (count == 3) {
                y += 90;
                x = 135;
                count = 0;
            }
            if (c.getHighlight()) {
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            c.setRectangleLocation(x, y);
            g.drawImage(c.getImage(), x, y, null);
            x = x + c.getImage().getWidth() + 10;
            count++;
        }
        if (!hasWon && !hasLost) {
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("REPLACE CARDS", 162, 350);
            g.drawRect((int) button.getX(), (int) button.getY(), (int) button.getWidth(), (int) button.getHeight());
            g.drawString("Cards Left: " + Card.deck.size(), 0, 15);
            g.drawString("RESTART", 194, 418);
            g.drawRect(193, 400, 87, 20);
            if (invalid) {
                g.drawString("INVALID", 200, 400);
            }
        }
        if (hasWon) {
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("You won congrats", 162, 350);
            g.drawString("RESTART", 194, 418);
            g.drawRect(193, 400, 87, 20);
        }
        if (hasLost) {
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("You lost, unlucky", 162, 350);
            g.drawString("RESTART", 194, 418);
            g.drawRect(193, 400, 87, 20);
        }
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();
        boolean atLeast = false;
        ArrayList<Integer> ind = new ArrayList<>();
        if (e.getButton() == 3) {
            for (Card card : hand) {
                Rectangle box = card.getCardBox();
                if (box.contains(clicked)) {
                    card.flipHighlight();
                }
            }
        }
        if (e.getButton() == 1 && button1.contains(clicked)) {
            hand.clear();
            Card.hand.clear();
            Card.deck.clear();
            hand = Card.buildHand();
            hasWon = false;
            hasLost = false;
        }
        if (e.getButton() == 1 && button.contains(clicked)) {
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getHighlight()) {
                    ind.add(i);
                    atLeast = true;

                }
            }
            if (atLeast && ind.size() > 1) {
                invalid = ind.size() > 3;
                if (ind.size() < 4) {
                    boolean cont = true;
                    String val1 = hand.get(ind.get(0)).getValue();
                    String val2 = hand.get(ind.get(1)).getValue();
                    if (ind.size() == 2) {
                        if (val1.equals("K") || val1.equals("Q") || val1.equals("J")) {
                            cont = false;
                        }
                        if (val2.equals("K") || val2.equals("Q") || val2.equals("J")) {
                            cont = false;
                        }
                    }
                    if (ind.size() == 2 && cont) {
                        int one = 0;
                        int two = 0;
                        if (val1.equals("A")) {
                            one = 1;
                        } else {
                            one = Integer.parseInt(hand.get(ind.getFirst()).getValue());
                        }
                        if (val2.equals("A")) {
                            two = 1;
                        } else {
                            two = Integer.parseInt(hand.get(ind.get(1)).getValue());
                        }
                        if (one + two == 11) {
                            if (!Card.deck.isEmpty()) {
                                Card.swap(ind.get(0));
                                Card.swap(ind.get(1));
                            } else {
                                Card.swap(ind.get(0));
                                Card.swap(ind.get(1) - 1);
                            }
                        } else {
                            invalid = true;
                        }
                    }
                    if (ind.size() == 3) {
                        String val3 = hand.get(ind.get(2)).getValue();
                        if (!val1.equals("K") && !val1.equals("Q") && !val1.equals("J")) {
                            invalid = true;
                        }
                        if (!val2.equals("K") && !val2.equals("Q") && !val2.equals("J")) {
                            invalid = true;
                        }
                        if (!val3.equals("K") && !val3.equals("Q") && !val3.equals("J")) {
                            invalid = true;
                        } else if (!val1.equals(val2) && !val1.equals(val3) && !val3.equals(val2) && !invalid) {
                            if (!Card.deck.isEmpty()) {
                                Card.swap(ind.get(0));
                                Card.swap(ind.get(1));
                                Card.swap(ind.get(2));
                            } else {
                                Card.swap(ind.get(0));
                                Card.swap(ind.get(1) - 1);
                                Card.swap(ind.get(2) - 2);
                            }
                        } else {
                            invalid = true;
                        }
                    }
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}