import java.sql.SQLException;

public interface AuthService {
    void start();

    boolean createUser(String var1, String var2, String var3);

    boolean deleteUserByNick(String var1);

    String getNickByLoginPass(String var1, String var2) throws SQLException, ClassNotFoundException;

    boolean createUser(String var1, String var2);

    boolean findUserByNick(String var1);

    void stop();
}
