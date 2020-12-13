package org.elwazy.twitter_clone.gui.render;

import org.elwazy.twitter_clone.dao.DaoHashtag;
import org.elwazy.twitter_clone.dao.DaoHashtagPublication;
import org.elwazy.twitter_clone.dao.DaoPublication;
import org.elwazy.twitter_clone.dao.DaoUser;
import org.elwazy.twitter_clone.models.Hashtag;
import org.elwazy.twitter_clone.models.HashtagPublication;
import org.elwazy.twitter_clone.models.Publication;
import org.elwazy.twitter_clone.models.User;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationTableRenderer extends JLabel implements TableCellRenderer {

    private DaoPublication daoPublication;
    private DaoHashtag daoHashtag;
    private DaoUser daoUser;
    DaoHashtagPublication daoHashtagPublication;

    int publicationId = 0;

    public PublicationTableRenderer(DaoPublication daoPublication, DaoHashtag daoHashtag,
                                    DaoUser daoUser, DaoHashtagPublication daoHashtagPublication) {
        this.daoPublication = daoPublication;
        this.daoHashtag = daoHashtag;
        this.daoUser = daoUser;
        this.daoHashtagPublication = daoHashtagPublication;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocused,
                                                   int row, int col) {
        Publication publication;
        switch (col) {
            case 0:
                publicationId = (int) value;
                publication = daoPublication.getById(publicationId);
                User user = daoUser.getById(publication.getUser_id_fk());

                setText(user.getUsername());
                break;
            case 1:
                publicationId = (int) value;
                publication = daoPublication.getById(publicationId);

                setText(publication.getText());
                break;
            case 2:
                publicationId = (int) value;
                publication = daoPublication.getById(publicationId);

                setText(publication.getDate().toString());
                break;
            case 3:

                // Aqui sucede la magia
                String hashtagsNames = "";
                List<HashtagPublication> hashtagsByPublication = daoHashtagPublication.getHastagsByPublication(publicationId);
                List<Hashtag> hashtags = new ArrayList<>();
                if ( hashtagsByPublication != null ) {
                    if ( hashtagsByPublication.get(0).getHashtag_id_fk() != 0 ) {
                        for (HashtagPublication hashtagPublication : hashtagsByPublication) {
                            Hashtag hashtag = daoHashtag.getById(hashtagPublication.getHashtag_id_fk());
                            hashtags.add(hashtag);
                        }

                        for (Hashtag hashtag : hashtags) {
                            hashtagsNames += hashtag.getName() + ", ";
                        }
                    }
                }

                setText(hashtagsNames);
                break;
            default:
                System.out.println(col);
        }
        return this;
    }
}
