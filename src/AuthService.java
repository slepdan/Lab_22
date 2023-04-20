import java.sql.SQLException;

public interface AuthService {
    void start();
    String getNickByLoginPassDB(String login, String pass) throws SQLException, ClassNotFoundException;

    String setNewUsers(int login, int pass, int nick) throws SQLException, ClassNotFoundException;

     int getBlackListUserById(int _nickId) throws SQLException;

     int getIdByNick(String _nick) throws SQLException;

    boolean addBlackListByNickAndNickName(int _nickId, int _nicknameId) throws SQLException;

    void stop();
}
