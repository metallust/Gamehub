import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class Game2048 extends JFrame implements KeyListener {

    public static void main(String[] args) {
        new Game2048("basit");
    }

    Jdbc conJdbc;
    int w = 100;
    int rows = 4, cols = 4;
    Cell[][] grid = new Cell[rows][cols];
    boolean flag = false;
    int score = 0;
    int highScore;

    int red[] = { 255, 255, 255, 255, 255, 255, 255, 255 };
    int green[] = { 214, 190, 164, 145, 120, 105, 95, 0 };
    int blue[] = { 0, 3, 0, 0, 0, 0, 0, 0 };

    JFrame frame = new JFrame();

    JPanel titlePanel = new JPanel();
    JLabel label = new JLabel();
    JPanel scoresPanel = new JPanel();
    JLabel scoreTitle = new JLabel("Score");
    JLabel scoreNumber = new JLabel("0");
    JLabel highScoreTitle = new JLabel("High Score");
    JLabel highScoreNumber = new JLabel("0");

    JPanel panel = new JPanel();
    JLabel[][] labels = new JLabel[4][4];

    JPanel buttonPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    JButton exitButton = new JButton("Exit");

    String fontName = "Verdana";
    String username;

    Game2048(String username) {
        this.username = username;
        conJdbc = new Jdbc(username);
        highScore = conJdbc.fetchHighscore(2);
        highScoreNumber.setText(Integer.toString(highScore));

        // main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        // frame.addKeyListener(this);
        frame.setTitle("User : " + username);

        // title panel
        titlePanel.setPreferredSize(new Dimension(550, 100));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(new Color(8, 36, 80));

        label = new JLabel("2048", JLabel.CENTER);
        label.setFont(new Font(fontName, Font.BOLD, 70));
        label.setForeground(new Color(255, 214, 0));

        titlePanel.add(label, BorderLayout.WEST);

        scoresPanel = new JPanel();
        scoresPanel.setLayout(new GridLayout(2, 2));

        scoreTitle.setFont(new Font(fontName, Font.BOLD, 27));
        scoreTitle.setForeground(new Color(255, 164, 0));
        scoreNumber.setFont(new Font(fontName, Font.BOLD, 23));
        scoreNumber.setForeground(new Color(255, 164, 0));
        highScoreTitle.setFont(new Font(fontName, Font.BOLD, 27));
        highScoreTitle.setForeground(new Color(255, 164, 0));
        highScoreNumber.setFont(new Font(fontName, Font.BOLD, 23));
        highScoreNumber.setForeground(new Color(255, 164, 0));

        scoresPanel.add(scoreTitle);
        scoresPanel.add(highScoreTitle);
        scoresPanel.add(scoreNumber);
        scoresPanel.add(highScoreNumber);
        scoresPanel.setBackground(new Color(8, 36, 80));

        titlePanel.add(scoresPanel, BorderLayout.EAST);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Game Panel
        panel.setLayout(new GridLayout(4, 4));
        panel.setBackground(new Color(5, 24, 53));
        panel.setBorder(new LineBorder(new Color(5, 24, 53), 7, true));
        panel.setBackground(new Color(5, 24, 53));

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                labels[i][j] = new JLabel();
                panel.add(labels[i][j]);
                labels[i][j].setFont(new Font(fontName, Font.BOLD, 50));
                labels[i][j].setFocusable(false);
                labels[i][j].setOpaque(true);

                // borders
                labels[i][j].setBorder(new LineBorder(new Color(5, 24, 53), 10, false));
            }
        }
        frame.add(panel, BorderLayout.CENTER);

        // button panel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        newGameButton.setPreferredSize(new Dimension(180, 40));
        exitButton.setPreferredSize(new Dimension(180, 40));
        newGameButton.setBackground(new Color(255, 214, 0));
        newGameButton.setForeground(Color.white);
        exitButton.setBackground(new Color(255, 214, 0));
        exitButton.setForeground(Color.white);
        newGameButton.setFont(new Font(fontName, Font.BOLD, 23));
        exitButton.setFont(new Font(fontName, Font.BOLD, 25));

        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (score > highScore) {
                    conJdbc.updatescore(score, 2);
                }
                new Game2048(username);
                conJdbc.connectionClose();
                frame.dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (score > highScore) {
                    conJdbc.updatescore(score, 2);
                }
                new MainFrame(username);
                conJdbc.connectionClose();
                frame.dispose();
            }
        });
        frame.requestFocusInWindow();
        frame.addKeyListener(this);
        buttonPanel.add(newGameButton);
        buttonPanel.add(exitButton);
        buttonPanel.setBackground(new Color(8, 36, 80));
        frame.add(buttonPanel, BorderLayout.SOUTH);

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
        scoreNumber.setText(Integer.toString(score));
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
                    labels[i][j].setBackground(new Color(64, 87, 128));
                }
            }
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
        if (flag) {
            addNumbers();
        } else {
            flag = false;
        }
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
        if (flag) {
            addNumbers();
        } else {
            flag = false;
        }
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
        if (flag) {
            addNumbers();
        } else {
            flag = false;
        }
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
        if (flag) {
            addNumbers();
        } else {
            flag = false;
        }
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
        if (!Arrays.equals(temp, temp2))
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
        if (temp.size() > 0) {
            Random random = new Random();
            temp.get(random.nextInt(temp.size())).number = (random.nextInt(2) + 1) * 2;
        }
        temp.clear();
    }

    public boolean gameOver() {
        // check left
        int[] temp = new int[4];
        int[] temp2;
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                temp[i] = grid[i][j].number;
            }
            temp2 = addArray(temp);
            if (!Arrays.equals(temp, temp2)) {
                return false;
            }
        }
        // check right
        for (int j = 0; j < rows; j++) {
            for (int i = 3; i >= 0; i--) {
                temp[3 - i] = grid[i][j].number;
            }
            temp2 = addArray(temp);
            if (!Arrays.equals(temp, temp2)) {
                return false;
            }
        }
        // check up
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                temp[i] = grid[j][i].number;
            }
            temp2 = addArray(temp);
            if (!Arrays.equals(temp, temp2)) {
                return false;
            }
        }
        // check down
        for (int j = 0; j < rows; j++) {
            for (int i = 3; i >= 0; i--) {
                temp[3 - i] = grid[j][i].number;
            }
            temp2 = addArray(temp);
            if (!Arrays.equals(temp, temp2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println(e.getKeyCode());

        if (!gameOver()) {

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
                    "Try Again",
                    "Game Over",
                    JOptionPane.ERROR_MESSAGE);
            if (score > highScore) {
                conJdbc.updatescore(score, 2);
            }
            conJdbc.connectionClose();
            new Game2048(username);
            frame.dispose();
        }

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