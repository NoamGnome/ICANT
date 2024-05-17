import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    private Rectangle button;
    private boolean invalid = false;


    public DrawPanel() {
        button = new Rectangle(159, 330, 160, 26);

        this.addMouseListener(this);
        hand = Card.buildHand();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 135;
        int y = 30;
        int count = 0;
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
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
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("REPLACE CARDS", 162, 350);
        g.drawRect((int) button.getX(), (int) button.getY(), (int) button.getWidth(), (int) button.getHeight());
        g.drawString("Cards Left: " + Card.deck.size(), 0, 450);
        if (invalid) {
            g.drawString("INVALID", 200, 400);
        }
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();
        int count = 0;
        int c1 = -1;
        int c2 = -1;
        int c3 = -1;
        ArrayList<Integer> ind = new ArrayList<>();
        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipHighlight();
                }
            }
        }
        if (e.getButton() == 1) {
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getHighlight()) {
                    ind.add(i);
                    System.out.println(ind);
                }
            }
            if (ind.size() > 3) {
                invalid = true;
            } else { invalid = false;}
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
                    } else {one = Integer.parseInt(hand.get(ind.get(0)).getValue());}
                    if (val2.equals("A")) {
                        two = 1;
                    } else {two = Integer.parseInt(hand.get(ind.get(1)).getValue());}
                    if (one + two == 11) {
                        Card.swap(ind.get(0));
                        Card.swap(ind.get(1));
                    } else {invalid = true;}
                }
                if (ind.size() == 3) {
                    String val3 = hand.get(ind.get(2)).getValue();
                    int one = 0;
                    int two = 0;
                    int three = 0;
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}