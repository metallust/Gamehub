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

    int fetchHighscore(int game) {

        String sql = "SELECT score" + game + " FROM users WHERE username=?";
        try (PreparedStatement preparedStatment = con.prepareStatement(sql)) {
            preparedStatment.setString(1, username);
            ResultSet resultSet = preparedStatment.executeQuery();
            if (resultSet.next()) {
                highscore = resultSet.getInt("score" + game);
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

    public void updatescore(int score, int game) {
        try {
            // inserting data in the database
            String sql = "UPDATE users SET score" + game + " = ? WHERE username = ?";
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

    User[] getTop10Users(int game) {
        Statement stmt = null;
        ResultSet rs = null;
        User[] top10Users = new User[10];
        for (int i = 0; i < 10; i++)
            top10Users[i] = new User();
        int i = 0;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(
                    "SELECT username,score" + game + " FROM users ORDER BY score" + game + " DESC LIMIT 10");
            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score" + game);
                top10Users[i].username = username;
                top10Users[i].score = score;
                i++;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top10Users;
    }
}
