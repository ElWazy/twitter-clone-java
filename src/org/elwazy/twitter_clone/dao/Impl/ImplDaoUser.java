package org.elwazy.twitter_clone.dao.Impl;

import org.elwazy.twitter_clone.dao.AbstractDbConnection;
import org.elwazy.twitter_clone.dao.DaoUser;
import org.elwazy.twitter_clone.models.User;
import org.elwazy.twitter_clone.persistence.DefaultDbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplDaoUser extends AbstractDbConnection implements DaoUser {

    public ImplDaoUser(DefaultDbConnection connection) {
        super(connection);
    }

    @Override
    public User login(String username, String passwd) {
        String sql = "SELECT id, username, passwd, user_type_id_fk, active FROM user " +
                "WHERE username = ? AND passwd = SHA2(?, 0) AND active = 1";
        User user = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("passwd"),
                            rs.getInt("user_type_id_fk"),
                            rs.getBoolean("active")
                    );
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public int rejoin(int id) {
        String sql = "UPDATE user SET active = 1 WHERE id = ? AND active = 0";
        int rowsAffected = 0;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT id, username, passwd, user_type_id_fk, active FROM user WHERE active = 1";
        List<User> users = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    users = new ArrayList<>();
                    do {
                        users.add(new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("passwd"),
                                rs.getInt("user_type_id_fk"),
                                rs.getBoolean("active")
                        ));
                    } while (rs.next());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getAllDismissed() {
        String sql = "SELECT id, username, passwd, user_type_id_fk, active FROM user WHERE active = 0";
        List<User> users = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    users = new ArrayList<>();
                    do {
                        users.add(new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("passwd"),
                                rs.getInt("user_type_id_fk"),
                                rs.getBoolean("active")
                        ));
                    } while (rs.next());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT id, username, passwd, user_type_id_fk, active FROM user " +
                "WHERE id = ?";
        User user = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("passwd"),
                                rs.getInt("user_type_id_fk"),
                                rs.getBoolean("active")
                        );
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public int insert(User user) {
        String sql = "INSERT INTO user (username, passwd, user_type_id_fk) VALUES " +
            "(?, SHA2(?, 0), ?)";
        int rowsAffected = 0;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswd());
            ps.setInt(3, user.getUser_type_id_fk());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(int id) {
        String sql = "UPDATE user SET active = 0 WHERE id = ? AND active = 1";
        int rowsAffected = 0;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }
}
