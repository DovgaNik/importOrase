package dev.dovhan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionProvider {

    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        Credentials credentials = new Credentials();
        String url = Credentials.dbURL;
        String username = Credentials.dbUsername;
        String password = Credentials.dbPassword;

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
