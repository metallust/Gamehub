import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUp extends JFrame {
    final private Font mainFont = new Font("Segoe print", Font.BOLD, 18);
    JTextField tfUserName;
    JPasswordField pfPassword;

    public void initialize() {
        /****** form panel *********/
        JLabel ibSignUpForm = new JLabel("Sign Up Form", SwingConstants.CENTER);
        ibSignUpForm.setFont(mainFont);

        // Name
        JLabel ibName = new JLabel("Name");
        ibName.setFont(mainFont);

        JTextField tfName = new JTextField();
        tfName.setFont(mainFont);

        // Username
        JLabel ibUserName = new JLabel("Username");
        ibUserName.setFont(mainFont);

        tfUserName = new JTextField();
        tfUserName.setFont(mainFont);

        // Password
        JLabel ibPassword = new JLabel("Password");
        ibPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        // confirm password
        JLabel ibConfirmPassword = new JLabel("Password");
        ibConfirmPassword.setFont(mainFont);

        JPasswordField pfConfirmPassword = new JPasswordField();
        pfConfirmPassword.setFont(mainFont);

        // adding all the element in single panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.add(ibSignUpForm);
        formPanel.add(ibName);
        formPanel.add(tfName);
        formPanel.add(ibUserName);
        formPanel.add(tfUserName);
        formPanel.add(ibPassword);
        formPanel.add(pfPassword);
        formPanel.add(ibConfirmPassword);
        formPanel.add(pfConfirmPassword);

        /********************* button panel *************************** */
        JButton btnSignup = new JButton("Sign Up");
        btnSignup.setFont(mainFont);
        btnSignup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String name = tfName.getText();
                String username = tfUserName.getText();
                String password = String.valueOf(pfPassword.getPassword());

                // checking if username already exist
                Boolean userExist = checkUser(username);

                if (!userExist) {
                    // adding data in the database
                    String user = addNewUser(name, username, password);

                    // callingg main frame
                    new MainFrame(user);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(SignUp.this,
                            "Username already in use",
                            "Try Again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // cancel button
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
        buttonsPanel.add(btnSignup);
        buttonsPanel.add(btnCancel);

        /********************* initializing the frame**************** */
        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setMinimumSize(new Dimension(350, 450));

        // setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String addNewUser(String name, String username, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/userdatabase";
        final String USERNAME = "root";
        final String PASSWORD = "irfan";
        try {
            Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // inserting data in the database
            String sql = "INSERT INTO users (name, username, password) VALUES(?, ?, ?)";
            PreparedStatement preparedStatment = con.prepareStatement(sql);
            preparedStatment.setString(1, name);
            preparedStatment.setString(2, username);
            preparedStatment.setString(3, password);
            preparedStatment.executeUpdate();

            // getting the same data in the form of user to display in the dashboard
            String sql2 = "SELECT * FROM users WHERE username=?";
            PreparedStatement preparedStatment2 = con.prepareStatement(sql2);
            preparedStatment2.setString(1, username);
            ResultSet resultSet = preparedStatment2.executeQuery();
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
        return user.userName;
    }

    private boolean checkUser(String username) {
        int result = 0;

        final String DB_URL = "jdbc:mysql://localhost:3306/userdatabase";
        final String USERNAME = "root";
        final String PASSWORD = "irfan";
        try {
            Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT COUNT(*) FROM users WHERE username=?";
            PreparedStatement preparedStatment = con.prepareStatement(sql);
            preparedStatment.setString(1, username);

            ResultSet resultSet = preparedStatment.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("count(*)");
            }

            preparedStatment.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Database connection failed ......   ");
        }
        if (result > 0)
            return true;
        else
            return false;
    }

}
