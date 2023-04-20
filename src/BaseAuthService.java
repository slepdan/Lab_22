import java.sql.*;

public class BaseAuthService implements AuthService {
    final static String connectionUrl = "jdbc:sqlite:identifier.sqlite";
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
        connection = DriverManager.getConnection("jdbc:sqlite:D:\\slepdan\\Lab_21\\Lab_21.db");
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
    public String getNickByLoginPassDB(String login, String pass) throws SQLException, ClassNotFoundException{
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
    public String setNewUsers(int login, int pass, int nick) throws SQLException, ClassNotFoundException {
        connect();
        String query = "INSERT INTO peoples (login, pass, nick) " + "VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,login);
        preparedStatement.setInt(2,pass);
        preparedStatement.setInt(3,nick);
        preparedStatement.executeUpdate();
        return query;
    }
    @Override
     public int getIdByNick(String nick) {
        String idNick = String.format("SELECT id FROM main where nickname = '%s'", nick);
        try {
            ResultSet rs = stmt.executeQuery(idNick);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public boolean addBlackListByNickAndNickName(int _nickId, int _nicknameId) {
        String addBlackListUser = String.format("INSERT INTO blacklist (id_user,id_blacklist_user) VALUES ('%s', '%s');", _nickId, _nicknameId);

        try {
            boolean rs = stmt.execute(addBlackListUser);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public int getBlackListUserById(int _nickId) {
        String idBlackListUser = String.format("SELECT id_blacklist_user FROM blacklist where id_user = '%s'", _nickId);

        try {
            ResultSet rs = stmt.executeQuery(idBlackListUser);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
