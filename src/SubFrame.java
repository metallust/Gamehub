import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubFrame extends JFrame {

    String gameName;
    String username;
    int highScore;
    String fontName = "Verdana";
    String[] games = { "Guess the number", "2048", "Guess the Color" };
    User[] top10Users;
    Jdbc conJdbc;

    JFrame frame = new JFrame();

    JPanel titlePanel = new JPanel();
    JLabel gameTitle;
    JLabel highscoreLabel;

    JPanel buttonPanel = new JPanel();
    JButton playButton;
    JButton backButton;

    JPanel panel = new JPanel();
    JPanel leaderbroadPanel = new JPanel();
    JLabel leaderbroadLabel;

    SubFrame(String username, int game) {

        this.username = username;
        gameName = games[game - 1];
        conJdbc = new Jdbc(username);
        top10Users = conJdbc.getTop10Users(game);
        highScore = conJdbc.fetchHighscore(game);
        conJdbc.connectionClose();

        // main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 829);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("User : " + username);

        // Title panel
        titlePanel.setPreferredSize(new Dimension(550, 120));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(new Color(8, 36, 80));

        gameTitle = new JLabel(gameName, JLabel.CENTER);
        gameTitle.setFont(new Font(fontName, Font.BOLD, 50));
        gameTitle.setForeground(new Color(255, 214, 0));
        titlePanel.add(gameTitle, BorderLayout.NORTH);

        highscoreLabel = new JLabel("Highscore : " + highScore, JLabel.CENTER);
        highscoreLabel.setFont(new Font(fontName, Font.BOLD, 34));
        highscoreLabel.setForeground(new Color(255, 214, 0));
        titlePanel.add(highscoreLabel, BorderLayout.SOUTH);

        // Button panel
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(600, 100));
        buttonPanel.setBackground(new Color(8, 36, 80));
        playButton = new JButton();
        playButton.setText("PLAY");
        playButton.setFont(new Font(fontName, Font.BOLD, 35));
        playButton.setForeground(new Color(255, 214, 0));
        playButton.setPreferredSize(new Dimension(170, 50));
        playButton.setBorder(new LineBorder(Color.white, 10, false));

        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (game) {
                    case 1:
                        new guessingNumber(username);
                        break;
                    case 2:
                        new Game2048(username);
                        break;
                    case 3:
                        new Game3(username);
                        break;
                }
                frame.dispose();
            }
        });
        backButton = new JButton();
        backButton.setText("BACK");
        backButton.setFont(new Font(fontName, Font.BOLD, 35));
        backButton.setForeground(new Color(255, 214, 0));
        backButton.setPreferredSize(new Dimension(170, 50));
        backButton.setBorder(new LineBorder(Color.white, 10, false));
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame(username);
                frame.dispose();
            }
        });
        buttonPanel.add(playButton);
        buttonPanel.add(backButton);

        // Leaderbroad panel
        panel.setLayout(new BorderLayout());
        panel.setBorder(new LineBorder(Color.red, 10, false));

        leaderbroadPanel.setPreferredSize(new Dimension(550, 550));
        leaderbroadPanel.setLayout(new GridLayout(10, 3));
        leaderbroadPanel.setBackground(new Color(8, 36, 80));

        leaderbroadLabel = new JLabel("Leaderbroad", JLabel.CENTER);
        leaderbroadLabel.setFont(new Font(fontName, Font.BOLD, 30));
        panel.add(leaderbroadLabel, BorderLayout.NORTH);

        // rank username score
        // JLabel rankTitle = new JLabel(Integer.toString(i), JLabel.CENTER);
        // rankTitle.setFont(new Font(fontName, Font.BOLD, 25));
        // rankTitle.setForeground(new Color(255, 214, 0));
        // rankTitle.setPreferredSize(new Dimension(550, 30));

        // JLabel usernameTitle = new JLabel(user.username, JLabel.CENTER);
        // usernameTitle.setFont(new Font(fontName, Font.BOLD, 25));
        // usernameTitle.setForeground(new Color(255, 214, 0));
        // usernameTitle.setPreferredSize(new Dimension(550, 30));

        // JLabel scoreTitle = new JLabel(Integer.toString(user.score), JLabel.CENTER);
        // scoreTitle.setFont(new Font(fontName, Font.BOLD, 25));
        // scoreTitle.setForeground(new Color(255, 214, 0));
        // scoreTitle.setPreferredSize(new Dimension(550, 30));

        // JPanel userPanel = new JPanel();
        // userPanel.setLayout(new GridLayout(1, 3));
        // userPanel.add(ranklabel);
        // userPanel.add(usernameLabel);
        // userPanel.add(scoreLable);

        // userPanel.setBorder(new LineBorder(Color.red, 10, false));
        // leaderbroadPanel.add(userPanel);

        // list
        int i = 0;
        for (User user : top10Users) {
            i++;
            JLabel ranklabel = new JLabel(Integer.toString(i), JLabel.CENTER);
            ranklabel.setFont(new Font(fontName, Font.BOLD, 25));
            ranklabel.setForeground(new Color(255, 214, 0));
            ranklabel.setPreferredSize(new Dimension(550, 30));

            JLabel usernameLabel = new JLabel(user.username, JLabel.CENTER);
            usernameLabel.setFont(new Font(fontName, Font.BOLD, 25));
            usernameLabel.setForeground(new Color(255, 214, 0));
            usernameLabel.setPreferredSize(new Dimension(550, 30));

            JLabel scoreLable = new JLabel(Integer.toString(user.score), JLabel.CENTER);
            scoreLable.setFont(new Font(fontName, Font.BOLD, 25));
            scoreLable.setForeground(new Color(255, 214, 0));
            scoreLable.setPreferredSize(new Dimension(550, 30));

            JPanel userPanel = new JPanel();
            userPanel.setLayout(new GridLayout(1, 3));
            userPanel.add(ranklabel);
            userPanel.add(usernameLabel);
            userPanel.add(scoreLable);

            userPanel.setBorder(new LineBorder(Color.red, 10, false));
            leaderbroadPanel.add(userPanel);
        }

        panel.add(leaderbroadPanel, BorderLayout.SOUTH);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        new SubFrame("irfan", 3);
    }

}
