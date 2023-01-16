import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class Game3 extends JFrame {

    public static void main(String[] args) {
        new Game3("irfan1");
    }

    int life = 4;

    Jdbc conJdbc;
    int rows = 2, cols = 2;
    Rgb[][] grid = new Rgb[rows][cols];
    Rgb randrRgb = new Rgb();
    int score = 0;
    int highScore;

    JFrame frame = new JFrame();

    JPanel titlePanel = new JPanel();
    JLabel label = new JLabel();
    JLabel question = new JLabel();

    JPanel scoresPanel = new JPanel();
    JLabel scoreTitle = new JLabel("Score");
    JLabel scoreNumber = new JLabel("0");
    JLabel highScoreTitle = new JLabel("High Score");
    JLabel highScoreNumber = new JLabel("0");
    JLabel lifeLabel = new JLabel("Life");
    JLabel lifeNumber = new JLabel(Integer.toString(life));

    JPanel optionPanel = new JPanel();
    JButton[][] buttons = new JButton[4][4];

    JPanel buttonPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    JButton exitButton = new JButton("Exit");

    String fontName = "Verdana";
    String username;

    Game3(String username) {
        this.username = username;
        conJdbc = new Jdbc(username);
        highScore = conJdbc.fetchHighscore("score3");
        highScoreNumber.setText(Integer.toString(highScore));

        // main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("User : " + username);

        // title panel
        titlePanel.setPreferredSize(new Dimension(550, 150));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(new Color(8, 36, 80));

        label = new JLabel("Guess the Color", JLabel.CENTER);
        label.setPreferredSize(new Dimension(550, 50));
        label.setFont(new Font(fontName, Font.BOLD, 40));
        label.setForeground(new Color(255, 214, 0));
        titlePanel.add(label, BorderLayout.NORTH);

        scoresPanel = new JPanel();
        scoresPanel.setLayout(new GridLayout(2, 3));
        scoresPanel.setPreferredSize(new Dimension(550, 50));

        scoreTitle.setFont(new Font(fontName, Font.BOLD, 27));
        scoreTitle.setForeground(new Color(255, 164, 0));
        scoreNumber.setFont(new Font(fontName, Font.BOLD, 23));
        scoreNumber.setForeground(new Color(255, 164, 0));
        highScoreTitle.setFont(new Font(fontName, Font.BOLD, 27));
        highScoreTitle.setForeground(new Color(255, 164, 0));
        highScoreNumber.setFont(new Font(fontName, Font.BOLD, 23));
        highScoreNumber.setForeground(new Color(255, 164, 0));
        lifeLabel.setFont(new Font(fontName, Font.BOLD, 27));
        lifeLabel.setForeground(new Color(255, 164, 0));
        lifeNumber.setFont(new Font(fontName, Font.BOLD, 23));
        lifeNumber.setForeground(new Color(255, 164, 0));

        scoresPanel.add(scoreTitle);
        scoresPanel.add(highScoreTitle);
        scoresPanel.add(lifeLabel);
        scoresPanel.add(scoreNumber);
        scoresPanel.add(highScoreNumber);
        scoresPanel.add(lifeNumber);
        scoresPanel.setBackground(new Color(8, 36, 80));

        titlePanel.add(scoresPanel, BorderLayout.SOUTH);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Game Panel
        optionPanel.setLayout(new GridLayout(rows, cols));
        optionPanel.setBackground(new Color(5, 24, 53));
        optionPanel.setBorder(new LineBorder(new Color(5, 24, 53), 7, true));
        optionPanel.setBackground(new Color(5, 24, 53));

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                buttons[i][j] = new JButton();
                optionPanel.add(buttons[i][j]);
                buttons[i][j].setFont(new Font(fontName, Font.BOLD, 50));
                buttons[i][j].setFocusable(false);
                buttons[i][j].setOpaque(true);

                final int finalI = i;
                final int finalJ = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        checkAns(finalI, finalJ);
                    }
                });

                // borders
                buttons[i][j].setBorder(new LineBorder(new Color(5, 24, 53), 10, false));
            }
        }
        frame.add(optionPanel, BorderLayout.CENTER);

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
                    conJdbc.updatescore(score, "score3");
                }
                new Game3(username);
                conJdbc.connectionClose();
                frame.dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (score > highScore) {
                    conJdbc.updatescore(score, "score3");
                }
                new MainFrame(username);
                conJdbc.connectionClose();
                frame.dispose();
            }
        });
        buttonPanel.add(newGameButton);
        buttonPanel.add(exitButton);
        buttonPanel.setBackground(new Color(8, 36, 80));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                grid[i][j] = new Rgb();
            }
        }
        upadateButtons();
    }

    void nextColor() {

        Random random = new Random();
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                int r = random.nextInt(255);
                int g = random.nextInt(255);
                int b = random.nextInt(255);
                grid[i][j].r = r;
                grid[i][j].g = g;
                grid[i][j].b = b;
                // System.out.println("rgb : " + r + " " + g + " " + b);
            }
        }
        // question setup
        int i = random.nextInt(2);
        int j = random.nextInt(2);
        randrRgb.r = grid[i][j].r;
        randrRgb.g = grid[i][j].g;
        randrRgb.b = grid[i][j].b;
    }

    void checkAns(int i, int j) {

        if (randrRgb.r == grid[i][j].r && randrRgb.g == grid[i][j].g && randrRgb.b == grid[i][j].b) {
            score += 10;
            System.out.println("working");
            upadateButtons();
        } else {
            life--;
            lifeNumber.setText(Integer.toString(life));
            if (life == 0) {
                JOptionPane.showMessageDialog(Game3.this,
                        "Game Over",
                        "Try Again",
                        JOptionPane.ERROR_MESSAGE);
                new Game3(username);
                if (score > highScore) {
                    conJdbc.updatescore(score, "score3");
                }
                conJdbc.connectionClose();
                frame.dispose();
            }
        }

    }

    void upadateButtons() {
        scoreNumber.setText(Integer.toString(score));

        nextColor();
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {

                int r = grid[i][j].r;
                int g = grid[i][j].g;
                int b = grid[i][j].b;

                buttons[i][j].setBackground(new Color(r, g, b));
                buttons[i][j].setForeground(new Color(50, 120, 153));
            }
        }
        // Question
        label.setText("RGB(" + randrRgb.r + "," + randrRgb.g + "," + randrRgb.b + ")");

    }

}

class Rgb {
    int r;
    int g;
    int b;

}