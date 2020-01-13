import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i], y[i], this);
            }
        }
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
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    private void checkCollisions() {
        for (int i = dots; i > 0 ; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
            if (x[0] > size)
                inGame = false;

            if(x[0] < 0)
                inGame = false;

            if (y[0] > size)
                inGame = false;

            if (y[0] < 0)
                inGame = false;
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}
