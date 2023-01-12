import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class Game2048 extends JFrame implements KeyListener {
    int w = 100;
    int rows = 4, cols = 4;
    Cell[][] grid = new Cell[rows][cols];
    boolean flag;
    boolean gameover = false;
    int score = 0;

    int red[] = { 238, 237, 242, 245, 246, 246, 237 };
    int green[] = { 228, 224, 177, 149, 124, 94, 207 };
    int blue[] = { 218, 200, 121, 99, 95, 59, 114 };

    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel panel = new JPanel();
    JLabel textfield = new JLabel();
    JLabel[][] labels = new JLabel[4][4];

    Game2048() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 700);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(this);
        frame.setTitle("2048");

        textfield.setBackground(new Color(200, 200, 200));
        textfield.setForeground(new Color(50, 102, 153));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("0");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 550, 70);

        panel.setLayout(new GridLayout(4, 4));
        // button_panel.setBackground(new Color(150, 150, 150));

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                labels[i][j] = new JLabel();
                panel.add(labels[i][j]);
                labels[i][j].setFont(new Font("MV Boli", Font.BOLD, 50));
                labels[i][j].setFocusable(false);
                labels[i][j].setOpaque(true);

                // borders
                labels[i][j].setBorder(new LineBorder(new Color(187, 173, 160), 5, true));
            }
        }

        title_panel.add(textfield);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(panel);

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        Random random = new Random();
        grid[random.nextInt(4)][random.nextInt(4)].number = (random.nextInt(2) + 1) * 2;
        grid[random.nextInt(4)][random.nextInt(4)].number = (random.nextInt(2) + 1) * 2;
        updateLabels();
    }

    void updateLabels() {
        textfield.setText(Integer.toString(score));
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {

                if (grid[i][j].number != 0) {

                    int r, g, b;

                    if (grid[i][j].number <= 128) {
                        r = red[(int) (Math.log(grid[i][j].number) / Math.log(2)) - 1];
                        g = green[(int) (Math.log(grid[i][j].number) / Math.log(2)) - 1];
                        b = blue[(int) (Math.log(grid[i][j].number) / Math.log(2)) - 1];

                    } else {
                        r = 230;
                        g = 200;
                        b = 100;
                    }
                    labels[i][j].setBackground(new Color(r, g, b));
                    labels[i][j].setForeground(new Color(50, 120, 153));

                    int value = grid[i][j].number;
                    labels[i][j].setHorizontalAlignment(JLabel.CENTER);
                    labels[i][j].setText(Integer.toString(value));

                } else {
                    labels[i][j].setText("");
                    labels[i][j].setBackground(new Color(205, 193, 180));
                    // labels[i][j].setBackground(Color.YELLOW);
                }
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameover) {
            if (e.getKeyCode() == 37) {

                addRowsLeft();
            } else if (e.getKeyCode() == 40) {

                addColumnsDown();
            } else if (e.getKeyCode() == 39) {

                addRowsRight();
            } else if (e.getKeyCode() == 38) {

                addColumnsUp();
            }

            updateLabels();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Game Over",
                    "Game Over",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addRowsLeft() {
        for (int j = 0; j < rows; j++) {
            int[] temp = new int[4];
            for (int i = 0; i < cols; i++) {
                temp[i] = grid[i][j].number;
            }
            temp = addArray(temp);
            for (int i = 0; i < cols; i++) {
                grid[i][j].number = temp[i];
            }
        }
        addNumbers();
    }

    public void addRowsRight() {
        for (int j = 0; j < rows; j++) {
            int[] temp = new int[4];
            for (int i = 3; i >= 0; i--) {
                temp[3 - i] = grid[i][j].number;
            }
            temp = addArray(temp);
            for (int i = 3; i >= 0; i--) {
                grid[i][j].number = temp[3 - i];
            }
        }
        addNumbers();
    }

    public void addColumnsDown() {
        for (int j = 0; j < rows; j++) {
            int[] temp = new int[4];
            for (int i = 3; i >= 0; i--) {
                temp[3 - i] = grid[j][i].number;
            }
            temp = addArray(temp);
            for (int i = 3; i >= 0; i--) {
                grid[j][i].number = temp[3 - i];
            }
        }
        addNumbers();
    }

    public void addColumnsUp() {
        for (int j = 0; j < rows; j++) {
            int[] temp = new int[4];
            for (int i = 0; i < cols; i++) {
                temp[i] = grid[j][i].number;
            }
            temp = addArray(temp);
            for (int i = 0; i < cols; i++) {
                grid[j][i].number = temp[i];
            }
        }
        addNumbers();
    }

    public int[] addArray(int[] temp) {
        int[] temp2 = new int[4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            if (temp[i] != 0) {
                temp2[index] = temp[i];
                index++;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (temp2[i] == temp2[i + 1] && temp2[i] != 0) {
                temp2[i] = temp2[i] * 2;
                score += temp[i];
                for (int j = i + 1; j < 3; j++) {
                    temp2[j] = temp2[j + 1];
                }
                temp2[3] = 0;
            }
        }

        if (Arrays.equals(temp, temp2))
            flag = true;
        return temp2;
    }

    public void addNumbers() {
        ArrayList<Cell> temp = new ArrayList<>();
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                if (grid[i][j].number == 0) {
                    temp.add(grid[i][j]);
                }
            }
        }
        if (flag) {
            Random random = new Random();
            temp.get(random.nextInt(temp.size())).number = (random.nextInt(2) + 1) * 2;
        }
        if (temp.size() == 1) {
            System.out.println("Game over I think!!!!!!!!!!!!!!!!");
            gameOver();
            flag = false;
        }

        temp.clear();
        flag = false;
    }

    public void gameOver() {
        // check left
        gameover = true;
        int[] temp = new int[4];
        int[] temp2;
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                temp[i] = grid[i][j].number;
            }
            temp2 = addArray(temp);
            if (!Arrays.equals(temp, temp2)) {
                gameover = false;
                return;
            }
        }
        // check right
        for (int j = 0; j < rows; j++) {
            for (int i = 3; i >= 0; i--) {
                temp[3 - i] = grid[i][j].number;
            }
            temp2 = addArray(temp);
            if (!Arrays.equals(temp, temp2)) {
                gameover = false;
                return;
            }
        }
        // check up
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                temp[i] = grid[j][i].number;
            }
            temp2 = addArray(temp);
            if (!Arrays.equals(temp, temp2)) {
                gameover = false;
                return;
            }
        }
        // check down
        for (int j = 0; j < rows; j++) {
            for (int i = 3; i >= 0; i--) {
                temp[3 - i] = grid[j][i].number;
            }
            temp2 = addArray(temp);
            if (!Arrays.equals(temp, temp2)) {
                gameover = false;
                return;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}

class Cell {
    int x;
    int y;
    int i;
    int j;
    int number = 0;
    int w = 100;

    Cell(int i, int j) {
        this.i = i;
        this.j = j;
        this.number = 0;
        this.x = i * w;
        this.y = j * w;
    }
}