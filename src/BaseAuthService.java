import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    final static String connectionUrl = "jdbc:sqlite:identifier.sqlite";
    private static Connection connection;
    private static Statement stmt;
    private class Entry {
        private String login;
        private String pass;
        private String nick;
        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }
    private List<Entry> entries;
    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }
    @Override
    public void stop(){
        System.out.println("Сервис аутентификации остановлен");
    }
    public BaseAuthService() {
        entries = new ArrayList<>();
        entries.add(new Entry("login1", "pass1","nick1"));
        entries.add(new Entry("login2", "pass2","nick2"));
        entries.add(new Entry("login3", "pass3","nick3"));
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
    public String setNewUsers(String login, String pass, String nick) throws SQLException, ClassNotFoundException {
        connect();
        int hash = pass.hashCode();
        String sql = String.format("INSERT INTO main (login, password, nickname) VALUES ('%s', '%d', '%s')", login, hash, nick);

        try {
            boolean rs = stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sql;
    }

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
