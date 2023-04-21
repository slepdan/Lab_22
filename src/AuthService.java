import java.sql.SQLException;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass) throws SQLException, ClassNotFoundException;
    boolean createUser (String login, String pass);
    boolean deleteUserByNick (String login);

    boolean findUserByNick (String nick);

    void stop();
}
