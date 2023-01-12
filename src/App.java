import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    public static void main(String[] args) {
        new Login_signUp();
        // new Game2048();

    }
}

class Login_signUp extends JFrame {
    JButton login, sign_up;
    JLabel greet, or;
    Container mainContainer = this.getContentPane();

    public Login_signUp() {
        login = new JButton("LOGIN");
        sign_up = new JButton("SIGNUP");

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginForm loginForm = new LoginForm();
                loginForm.initialize();
                dispose();
            }
        });
        sign_up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignUp signUp = new SignUp();
                signUp.initialize();
                dispose();
            }
        });

        // mainContainer.setLayout(new BorderLayout(8,6));
        mainContainer.setBackground(Color.gray);
        login.setBackground(Color.black);
        sign_up.setBackground(Color.black);
        login.setForeground(Color.orange);
        sign_up.setForeground(Color.orange);

        // Label
        greet = new JLabel("WELCOME", SwingConstants.CENTER);
        greet.setForeground(Color.BLACK);
        greet.setFont(new Font("Serif", Font.PLAIN, 30));
        or = new JLabel("----or----", SwingConstants.CENTER);
        or.setBackground(Color.gray);
        or.setForeground(Color.BLACK);
        // or.setFont(new Font("Arial",Font.PLAIN,14));

        JPanel panel = new JPanel();
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(4, 1, 10, 20));
        grid.setBackground(Color.gray);
        panel.setBounds(100, 160, 180, 180);
        panel.add(greet);
        panel.add(grid);
        grid.add(login);
        grid.add(or);
        grid.add(sign_up);
        panel.setBackground(Color.GRAY);
        mainContainer.add(panel, BorderLayout.CENTER);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));

        setLayout(null);
        setVisible(true);
        setSize(400, 600);
        setDefaultCloseOperation(3);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
