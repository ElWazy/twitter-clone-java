package org.elwazy.twitter_clone.dao;

import org.elwazy.twitter_clone.persistence.DefaultDbConnection;

public abstract class AbstractDbConnection {

    protected DefaultDbConnection connection;

    public AbstractDbConnection(DefaultDbConnection connection) {
        this.connection = connection;
    }

}
