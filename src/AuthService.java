import java.sql.SQLException;

public interface AuthService {
    void start();
    String getNickByLoginPassDB(String login, String pass) throws SQLException, ClassNotFoundException;

    String setNewUsers(String login, String pass, String nick) throws SQLException, ClassNotFoundException;

    static int getBlackListUserById(int _nickId) throws SQLException {
        return 0;
    }

    static int getIdByNick(String _nick) throws SQLException {
        return 0;
    }

    static boolean addBlackListByNickAndNickName(int _nickId, int _nicknameId) throws SQLException {
        return false;
    }

    void stop();
}
