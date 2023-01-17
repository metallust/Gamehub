import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    JButton g1, g2, g3, logout;

    MainFrame(String username) {
        g1 = new JButton("Guess the number");
        g2 = new JButton("2048");
        g3 = new JButton("Guess the color");
        logout = new JButton("LOGOUT");
        // add(g1);
        // add(g2);
        // add(g3);
        // add(logout);
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        // c.setBackground(Color.BLACK);

        setLayout(new GridLayout(3, 1, 0, 20));
        setTitle("user: " + username);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(350, 650);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        JLabel l = new JLabel("Hello");
        JPanel top = new JPanel();
        top.add(l);

        JPanel allGame = new JPanel();
        allGame.setLayout(null);
        JPanel grid = new JPanel();
        // grid.setBounds(25,25,550,450);
        grid.setLayout(new GridLayout(2,10,20,25));
        g1.setBorder(null);
        g2.setBorder(null);
        g3.setBorder(null);
        g1.setBackground(Color.BLACK);
        g1.setForeground(Color.orange);
        g2.setBackground(Color.BLACK);
        g2.setForeground(Color.orange);
        g3.setBackground(Color.BLACK);
        g3.setForeground(Color.orange);
        grid.add(g1);
        grid.add(g2);
        grid.add(g3);
        // allGame.add(grid);

        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.add(logout);


        // grid.setBackground(Color.BLACK);
        // bottom.setBackground(Color.BLACK);
        // c.add(top,BorderLayout.NORTH);
       c.add(grid,BorderLayout.CENTER);
       c.add(bottom,BorderLayout.SOUTH);





        g1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SubFrame(username, 1);
                dispose();
            }

        });
        g2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SubFrame(username, 2);
                dispose();
            }
        });
        g3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SubFrame(username, 3);
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
