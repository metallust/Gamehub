import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    JButton g1, g2, g3, logout;

    MainFrame(String username) {
        g1 = new JButton("g1");
        g2 = new JButton("g2");
        g3 = new JButton("g3");
        logout = new JButton("LOGOUT");
        add(g1);
        add(g2);
        add(g3);
        add(logout);

        setLayout(new GridLayout(3, 1, 0, 20));
        setTitle("user: " + username);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1100, 650);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        g2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Game2048(username);
                dispose();
            }

        });
        g1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new guessingNumber(username);
                dispose();
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login_signUp();
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new MainFrame("basit");
    }

}
