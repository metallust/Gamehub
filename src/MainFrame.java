import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    JButton g1, g2, g3, logout;
    JFrame frame = new JFrame();
    String fontName = "Verdana";

    MainFrame(String username) {
        g1 = new JButton();
        Image img = new ImageIcon(getClass().getResource("/Images/game1.png")).getImage();
        g1.setIcon(new ImageIcon(img));

        g2 = new JButton();
        Image img2 = new ImageIcon(getClass().getResource("/Images/game2.jpg")).getImage();
        g2.setIcon(new ImageIcon(img2));

        g3 = new JButton();
        Image img3 = new ImageIcon(getClass().getResource("/Images/game3.png")).getImage();
        g3.setIcon(new ImageIcon(img3));
        // g3.setForeground(Color.WHITE);
        // g3.setFont(new Font(fontName, Font.BOLD, 20));

        // logout button
        logout = new JButton("LOGOUT");
        logout.setBackground(new Color(255, 214, 0));
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font(fontName, Font.BOLD, 20));
        logout.setBorder(new LineBorder(new Color(5, 24, 53), 4, false));
        logout.setPreferredSize(new Dimension(120, 40));

        // tile panel
        JPanel title = new JPanel();
        title.setPreferredSize(new Dimension(700, 40));
        title.setLayout(new BorderLayout());

        // Username
        JLabel User = new JLabel(username);
        User.setFont(new Font(fontName, Font.BOLD, 30));
        User.setForeground(Color.WHITE);
        title.add(logout, BorderLayout.EAST);
        title.add(User, BorderLayout.WEST);
        title.setBackground(new Color(5, 24, 53));

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.add(g1);
        buttonPanel.add(g2);
        buttonPanel.add(g3);

        // initializing the frame
        frame.setTitle("User : " + username);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setMinimumSize(new Dimension(850, 800));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());

        // adding all the panels
        frame.add(title, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Action listener

        g1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SubFrame(username, 1);
                frame.dispose();
            }

        });
        g2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SubFrame(username, 2);
                frame.dispose();
            }
        });
        g3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SubFrame(username, 3);
                frame.dispose();
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login_signUp();
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new MainFrame("basit");
    }

}
