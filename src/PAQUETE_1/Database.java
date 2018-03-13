package PAQUETE_1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  private static Connection conn;
  private static final String DB_CONNECTION = "jdbc:h2:./db/maindb";
  private static String user;
  private static String password;

  public static void init(String _user, String _password) {
    user = _user;
    password = _password;
    conn = null;
  }
  public static Connection getConnection() throws SQLException {
    if (conn == null || conn.isClosed()) {
      conn = DriverManager.getConnection(DB_CONNECTION, user, password);
    }
    return conn;
  }
}