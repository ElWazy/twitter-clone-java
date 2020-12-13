package org.elwazy.twitter_clone.dao.Impl;

import org.elwazy.twitter_clone.dao.AbstractDbConnection;
import org.elwazy.twitter_clone.dao.DaoHashtagPublication;
import org.elwazy.twitter_clone.models.HashtagPublication;
import org.elwazy.twitter_clone.persistence.DefaultDbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplDaoHashtagPublication extends AbstractDbConnection implements DaoHashtagPublication {

    public ImplDaoHashtagPublication(DefaultDbConnection connection) {
        super(connection);
    }

    @Override
    public List<HashtagPublication> getHastagsByPublication(int publicationId) {
        String sql = "SELECT id, publication_id_fk, hashtag_id_fk FROM hashtag_publication WHERE publication_id_fk = ?";
        List<HashtagPublication> hashtagPublications = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, publicationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hashtagPublications = new ArrayList<>();
                    do {
                        hashtagPublications.add(new HashtagPublication(
                                rs.getInt("id"),
                                rs.getInt("publication_id_fk"),
                                rs.getInt("hashtag_id_fk")
                        ));
                    } while (rs.next());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hashtagPublications;
    }

    @Override
    public List<HashtagPublication> getAll() {
        String sql = "SELECT id, publication_id_fk, hashtag_id_fk FROM hashtag_publication GROUP BY publication_id_fk";
        List<HashtagPublication> hashtagPublications = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hashtagPublications = new ArrayList<>();
                    do {
                        hashtagPublications.add(new HashtagPublication(
                                rs.getInt("id"),
                                rs.getInt("publication_id_fk"),
                                rs.getInt("hashtag_id_fk")
                        ));
                    } while (rs.next());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hashtagPublications;
    }

    @Override
    public HashtagPublication getById(int id) {
        String sql = "SELECT id, publication_id_fk, hashtag_id_fk FROM hashtag_publication " +
                "WHERE id = ?";
        HashtagPublication hashtagPublication = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hashtagPublication = new HashtagPublication(
                            rs.getInt("id"),
                            rs.getInt("publication_id_fk"),
                            rs.getInt("hashtag_id_fk")
                    );
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hashtagPublication;
    }

    @Override
    public int insert(HashtagPublication hashtagPublication) {
        String sql = "INSERT INTO hashtag_publication (publication_id_fk, hashtag_id_fk) VALUES " +
                "(?, ?)";
        int rowsAffected = 0;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, hashtagPublication.getPublication_id_fk());
            ps.setInt(2, hashtagPublication.getHashtag_id_fk());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int insertNullHashtag(HashtagPublication hashtagPublication) {
        String sql = "INSERT INTO hashtag_publication (publication_id_fk) VALUES " +
                "(?)";
        int rowsAffected = 0;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, hashtagPublication.getPublication_id_fk());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(HashtagPublication hashtagPublication) {
        return 0;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM hashtag_publication WHERE id = ?";
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
