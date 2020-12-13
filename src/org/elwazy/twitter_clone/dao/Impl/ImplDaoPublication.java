package org.elwazy.twitter_clone.dao.Impl;

import org.elwazy.twitter_clone.dao.AbstractDbConnection;
import org.elwazy.twitter_clone.dao.DaoPublication;
import org.elwazy.twitter_clone.models.Publication;
import org.elwazy.twitter_clone.persistence.DefaultDbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplDaoPublication extends AbstractDbConnection implements DaoPublication {

    public ImplDaoPublication(DefaultDbConnection connection) {
        super(connection);
    }

    @Override
    public int getIdLastPublication() {
        String sql = "SELECT MAX(id) AS 'last_id' FROM publication";
        int publicationId = 0;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    publicationId = rs.getInt("last_id");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return publicationId;
    }

    @Override
    public List<Publication> getAll() {
        String sql = "SELECT id, user_id_fk, text, date FROM publication";
        List<Publication> publications = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    publications = new ArrayList<>();
                    do {
                        publications.add(new Publication(
                                rs.getInt("id"),
                                rs.getInt("user_id_fk"),
                                rs.getString("text"),
                                rs.getDate("date")
                        ));
                    } while (rs.next());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return publications;
    }

    @Override
    public Publication getById(int id) {
        String sql = "SELECT id, user_id_fk, text, date FROM publication " +
                "WHERE id = ?";
        Publication publication = null;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    publication = new Publication(
                            rs.getInt("id"),
                            rs.getInt("user_id_fk"),
                            rs.getString("text"),
                            rs.getDate("date")
                    );
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return publication;
    }

    @Override
    public int insert(Publication publication) {
        String sql = "INSERT INTO publication (user_id_fk, text, date) VALUES " +
                "(?, ?, NOW())";
        int rowsAffected = 0;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, publication.getUser_id_fk());
            ps.setString(2, publication.getText());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(Publication publication) {
        return 0;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM publication WHERE id = ?";
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
