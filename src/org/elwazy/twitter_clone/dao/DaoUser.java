package org.elwazy.twitter_clone.dao;

import org.elwazy.twitter_clone.models.User;

import java.util.List;

public interface DaoUser extends GenericCrud<User> {
    List<User> getAllDismissed();
    User login(String username, String passwd);
    int rejoin(int id);
}
