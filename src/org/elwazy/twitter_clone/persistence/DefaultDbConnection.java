package org.elwazy.twitter_clone.persistence;

import java.sql.Connection;

public interface DefaultDbConnection {
    Connection getConnection();
}
