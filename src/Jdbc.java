import java.sql.*;

public class Jdbc {
    final String DB_URL = "jdbc:mysql://localhost:3306/userdatabase";
    final String USERNAME = "root";
    final String PASSWORD = "irfan";
    int highscore;
    Connection con;
    String username;

    public Jdbc(String username) {
        this.username = username;
        try {
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("Database connection failed ......   ");
        }
    }

    int fetchHighscore(String score) {

        String sql = "SELECT " + score + " FROM users WHERE username=?";
        try (PreparedStatement preparedStatment = con.prepareStatement(sql)) {
            preparedStatment.setString(1, username);
            ResultSet resultSet = preparedStatment.executeQuery();
            if (resultSet.next()) {
                highscore = resultSet.getInt(score);
            }
            preparedStatment.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highscore;

    }

    void connectionClose() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatescore(int score, String gameno) {
        try {
            // inserting data in the database
            String sql = "UPDATE users SET " + gameno + " = ? WHERE username = ?";
            PreparedStatement preparedStatment = con.prepareStatement(sql);
            preparedStatment.setLong(1, score);
            preparedStatment.setString(2, username);
            preparedStatment.executeUpdate();
            preparedStatment.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addNewUser(String name, String password) {

        try {
            // inserting data in the database
            String sql = "INSERT INTO users (name, username, password) VALUES(?, ?, ?)";
            PreparedStatement preparedStatment = con.prepareStatement(sql);
            preparedStatment.setString(1, name);
            preparedStatment.setString(2, this.username);
            preparedStatment.setString(3, password);
            preparedStatment.executeUpdate();
            preparedStatment.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean checkUser() {
        int result = 0;

        try {
            String sql = "SELECT COUNT(*) FROM users WHERE username=?";
            PreparedStatement preparedStatment = con.prepareStatement(sql);
            preparedStatment.setString(1, username);
            ResultSet resultSet = preparedStatment.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("count(*)");
            }
            preparedStatment.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    boolean getAuthenticatedUser(String password) {
        int result = 0;
        try {
            String sql = "SELECT COUNT(*) FROM users WHERE username=? AND password=?";
            PreparedStatement preparedStatment = con.prepareStatement(sql);
            preparedStatment.setString(1, this.username);
            preparedStatment.setString(2, password);
            ResultSet resultSet = preparedStatment.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("count(*)");
            }
            preparedStatment.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }

}
