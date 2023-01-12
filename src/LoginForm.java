// import java.awt.Dimension;
// import java.awt.Font;

// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JTextField;
// import javax.swing.SwingConstants;
// import javax.swing.WindowConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JFrame {
    final private Font mainFont = new Font("Segoe print", Font.BOLD, 18);
    JTextField tfUserName;
    JPasswordField pfPassword;

    public void initialize() {
        /****** form panel *********/
        JLabel ibLoginForm = new JLabel("Login Form", SwingConstants.CENTER);
        ibLoginForm.setFont(mainFont);

        JLabel IbUserName = new JLabel("Username");
        IbUserName.setFont(mainFont);

        tfUserName = new JTextField();
        tfUserName.setFont(mainFont);

        JLabel IbPassword = new JLabel("Password");
        IbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.add(ibLoginForm);
        formPanel.add(IbUserName);
        formPanel.add(tfUserName);
        formPanel.add(IbPassword);
        formPanel.add(pfPassword);

        /********************* button panel *************************** */
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(mainFont);
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String username = tfUserName.getText();
                String password = String.valueOf(pfPassword.getPassword());
                User user = getAuthenticatedUser(username, password);

                if (user != null) {
                    new MainFrame(username);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Invalid Username or Password",
                            "Try Again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(mainFont);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login_signUp();
                dispose();
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonsPanel.add(btnLogin);
        buttonsPanel.add(btnCancel);

        /********************* initializing the frame**************** */
        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setMinimumSize(new Dimension(350, 450));

        // serResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private User getAuthenticatedUser(String userName, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/userdatabase";
        final String USERNAME = "root";
        final String PASSWORD = "irfan";
        try {
            Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement preparedStatment = con.prepareStatement(sql);
            preparedStatment.setString(1, userName);
            preparedStatment.setString(2, password);

            ResultSet resultSet = preparedStatment.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.userName = resultSet.getString("username");
                // user.password = resultSet.getString("password");
                user.score1 = resultSet.getString("score1");
                user.score2 = resultSet.getString("score2");
                user.score3 = resultSet.getString("score3");
            }

            preparedStatment.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Database connection failed ......   ");
        }
        return user;
    }
}
