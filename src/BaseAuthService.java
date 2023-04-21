import java.sql.*;
public class BaseAuthService implements AuthService {
    final static String connectionUrl = "jdbc:sqlite:C:\\Lab_21\\Lab_21.db";
    private static Connection connection;
    private static Statement stmt;

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }
    @Override
    public void stop(){
        System.out.println("Сервис аутентификации остановлен");
    }
    public void connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(connectionUrl);
        stmt = connection.createStatement();
    }
    public void closeConnection(){
        try{
            if (stmt !=null){
                stmt.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try{
            if(connection !=null){
                connection.close();
            }
        }
        catch (SQLException esql){
            esql.printStackTrace();
        }
    }
    @Override
    public String getNickByLoginPass(String login, String pass) throws SQLException, ClassNotFoundException{
        connect();
        String query = "SELECT * FROM users";
        String nickReturn = null;
        ResultSet rs= stmt.executeQuery(query);
        while (rs.next()) {
            if(rs.getString("login").equals(login) && rs.getString("pass").equals(pass)){
                nickReturn = rs.getString("nick");
                System.out.println(rs.getString("nick"));
            }
        }
        closeConnection();
        return nickReturn;
    }
    @Override
    public synchronized boolean createUser (String login, String pass) {
        try {
            String query = "INSERT into user (login, password, name) VALUES ('"+login+"', '"+pass+"', '"+login+"')";
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public synchronized boolean deleteUserByNick (String login) {
        try {
            String query = "DELETE from user where name='"+login+"'";
            stmt.execute(query);
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
            String query = "select * from user where name='"+nick+"'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                nickname = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return !nickname.equals("");
    }
}
