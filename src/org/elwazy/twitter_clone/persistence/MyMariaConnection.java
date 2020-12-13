package org.elwazy.twitter_clone.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyMariaConnection implements DefaultDbConnection {

    private Connection connection;

    public MyMariaConnection(String host, String db, String username, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");

        String url = String.format(
                "jdbc:mariadb://%s:3306/%s?user=%s&password=%s",
                host,
                db,
                username,
                password
        );
        connection = DriverManager.getConnection(url);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
