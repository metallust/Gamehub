import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        Jdbc conJdbc = new Jdbc(username);
        conJdbc.addNewUser(username, password);
        conJdbc.connectionClose();
        return username;
    }

    private boolean checkUser(String username) {
        Jdbc conJdbc = new Jdbc(username);
        boolean r = conJdbc.checkUser();
        conJdbc.connectionClose();
        return r;
    }

}
