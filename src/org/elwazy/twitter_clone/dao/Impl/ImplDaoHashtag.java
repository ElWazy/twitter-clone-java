package org.elwazy.twitter_clone.dao.Impl;

import org.elwazy.twitter_clone.dao.AbstractDbConnection;
import org.elwazy.twitter_clone.dao.DaoHashtag;
import org.elwazy.twitter_clone.models.Hashtag;
import org.elwazy.twitter_clone.persistence.DefaultDbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplDaoHashtag extends AbstractDbConnection implements DaoHashtag {

    public ImplDaoHashtag(DefaultDbConnection connection) {
        super(connection);
    }

    @Override
    public List<Hashtag> getAll() {
        String sql = "SELECT id, name FROM hashtag";
        List<Hashtag> hashtags = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hashtags = new ArrayList<>();
                    do {
                        hashtags.add(new Hashtag(
                                rs.getInt("id"),
                                rs.getString("name")
                        ));
                    } while (rs.next());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hashtags;
    }

    @Override
    public Hashtag getById(int id) {
        String sql = "SELECT id, name FROM hashtag WHERE id = ?";
        Hashtag hashtag = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hashtag = new Hashtag(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hashtag;
    }

    @Override
    public int insert(Hashtag hashtag) {
        String sql = "INSERT INTO hashtag (name) VALUES " +
                "(?)";
        int rowsAffected = 0;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setString(1, hashtag.getName());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(Hashtag hashtag) {
        return 0;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM hashtag WHERE id = ?";
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
