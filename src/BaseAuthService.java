import java.sql.*;

public class BaseAuthService implements AuthService {
    final static String connectionUrl = "jdbc:sqlite:Lab_21\\Lab_21.db";
    public static Connection connection;
    private static Statement stmt;

    @Override
    public void start() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e ) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(connectionUrl);
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop(){
        System.out.println("Сервис аутентификации остановлен");
    }

    @Override
    public synchronized boolean createUser (String login, String pass, String nick) {
        try {
            String query = "INSERT into users (login, pass, nick)" + " VALUES (?,?,?)";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1,login);
            pr.setString(2,pass);
            pr.setString(3,nick);
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized boolean deleteUserByNick (String login) {
        try {
            String query = "DELETE from users where nick=?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1,login);
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized boolean findUserByNick (String nick) {
        String nickname = "";
        try {
            String query = "select * from users where nick=?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1,nick);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                nickname = rs.getString("nick");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return !nickname.equals("");
    }

    @Override
    public synchronized String getNickByLoginPass (String login, String pass) {
        String nickReturn = null;
        try {
            String query = "select * from users where login='" + login + "'and pass='" + pass + "'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                nickReturn = rs.getString("nick");
                System.out.println(rs.getString("nick"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (nickReturn.equals(" "))
            return null;
        return nickReturn;
    }
}
