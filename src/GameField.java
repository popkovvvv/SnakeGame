import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private final int size = 320;
    private final int dotSize = 16;
    private final int allDots = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[allDots];
    private int[] y = new int[allDots];
    private int dots;
    private Timer timer;
    private boolean left;
    private boolean right = true;
    private boolean up;
    private boolean down;
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++){
            x[i] = 48 - i * dotSize;
            y[i] = 48;

            timer = new Timer(250, this);
            timer.start();

            createApple();

        }
    }

    private void createApple() {
        appleX = new Random().nextInt(20) * dotSize;
        appleY = new Random().nextInt(20) * dotSize;

    }

    public void loadImages(){
        ImageIcon appleImage = new ImageIcon("apple.png");
        apple = appleImage.getImage();

        ImageIcon dotImage = new ImageIcon("dot.png");
        dot = dotImage.getImage();
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left){
            x[0] -= dotSize;
        }
        if (right){
            x[0] += dotSize;
        }
        if (up){
            y[0] -= dotSize;
        }
        if (down){
            y[0] += dotSize;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            move();
        }
        repaint();
    }
}
