import javax.swing.*;
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

    JPanel leaderbroadPanel = new JPanel();

    SubFrame(String username, int game) {

        this.username = username;
        gameName = games[game - 1];
        conJdbc = new Jdbc(username);
        top10Users = conJdbc.getTop10Users(game);
        highScore = conJdbc.fetchHighscore(game);
        conJdbc.connectionClose();

        // main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("User : " + username);

        // Title panel
        titlePanel.setPreferredSize(new Dimension(550, 100));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(new Color(8, 36, 80));

        gameTitle = new JLabel(gameName, JLabel.CENTER);
        gameTitle.setFont(new Font(fontName, Font.BOLD, 50));
        gameTitle.setForeground(new Color(255, 214, 0));
        titlePanel.add(gameTitle, BorderLayout.NORTH);

        highscoreLabel = new JLabel(Integer.toString(highScore), JLabel.CENTER);
        highscoreLabel.setFont(new Font(fontName, Font.BOLD, 50));
        highscoreLabel.setForeground(new Color(255, 214, 0));
        titlePanel.add(highscoreLabel, BorderLayout.SOUTH);

        // Button panel
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(600, 100));
        buttonPanel.setBackground(new Color(8, 36, 80));
        playButton = new JButton();
        playButton.setText("PLAY");
        playButton.setFont(new Font(fontName, Font.BOLD, 50));
        playButton.setForeground(new Color(255, 214, 0));
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
        backButton.setFont(new Font(fontName, Font.BOLD, 50));
        backButton.setForeground(new Color(255, 214, 0));
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame(username);
                frame.dispose();
            }
        });
        buttonPanel.add(playButton, BorderLayout.EAST);
        buttonPanel.add(backButton, BorderLayout.WEST);

        // Leaderbroad panel
        leaderbroadPanel.setPreferredSize(new Dimension(550, 600));
        leaderbroadPanel.setLayout(new GridLayout(10, 3));
        leaderbroadPanel.setBackground(new Color(8, 36, 80));

        int i = 0;
        for (User user : top10Users) {
            i++;
            // System.out.println("Username: " + user.username + " Score: " + user.score);

            JLabel ranklabel = new JLabel(Integer.toString(i), JLabel.CENTER);
            ranklabel.setFont(new Font(fontName, Font.BOLD, 25));
            ranklabel.setForeground(new Color(255, 214, 0));
            JLabel usernameLabel = new JLabel(user.username, JLabel.CENTER);
            usernameLabel.setFont(new Font(fontName, Font.BOLD, 25));
            usernameLabel.setForeground(new Color(255, 214, 0));
            JLabel scoreLable = new JLabel(Integer.toString(user.score), JLabel.CENTER);
            scoreLable.setFont(new Font(fontName, Font.BOLD, 25));
            scoreLable.setForeground(new Color(255, 214, 0));

            JPanel userPanel = new JPanel();
            userPanel.setLayout(new GridLayout(1, 3));
            userPanel.add(ranklabel);
            userPanel.add(usernameLabel);
            userPanel.add(scoreLable);

            leaderbroadPanel.add(userPanel);
        }

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(leaderbroadPanel, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        new SubFrame("irfan1", 3);
    }

}
