import java.sql.SQLException;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass);

    boolean createUser (String login, String pass, String nick);
    boolean deleteUserByNick (String login);

    boolean findUserByNick (String nick);

    void stop();
}
