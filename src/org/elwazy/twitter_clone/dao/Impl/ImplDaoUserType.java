package org.elwazy.twitter_clone.dao.Impl;

import org.elwazy.twitter_clone.dao.AbstractDbConnection;
import org.elwazy.twitter_clone.dao.DaoUserType;
import org.elwazy.twitter_clone.models.UserType;
import org.elwazy.twitter_clone.persistence.DefaultDbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplDaoUserType extends AbstractDbConnection implements DaoUserType {

    public ImplDaoUserType(DefaultDbConnection connection) {
        super(connection);
    }

    @Override
    public List<UserType> getAll() {
        String sql = "SELECT id, name FROM user_type";
        List<UserType> userTypes = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userTypes = new ArrayList<>();
                    do {
                        userTypes.add(new UserType(
                                rs.getInt("id"),
                                rs.getString("name")
                        ));
                    } while (rs.next());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userTypes;
    }

    @Override
    public UserType getById(int id) {
        String sql = "SELECT id, name FROM user_type WHERE id = ?";
        UserType userType = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userType = new UserType(
                                rs.getInt("id"),
                                rs.getString("name")
                        );
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userType;
    }

    @Override
    public int insert(UserType userType) {
        return 0;
    }

    @Override
    public int update(UserType userType) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
