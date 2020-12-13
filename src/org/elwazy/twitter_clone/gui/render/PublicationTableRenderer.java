package org.elwazy.twitter_clone.gui.render;

import org.elwazy.twitter_clone.dao.DaoHashtag;
import org.elwazy.twitter_clone.dao.DaoHashtagPublication;
import org.elwazy.twitter_clone.dao.DaoPublication;
import org.elwazy.twitter_clone.dao.DaoUser;
import org.elwazy.twitter_clone.models.Hashtag;
import org.elwazy.twitter_clone.models.HashtagPublication;
import org.elwazy.twitter_clone.models.Publication;
import org.elwazy.twitter_clone.models.User;
import org.elwazy.twitter_clone.util.ImageUtil;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationTableRenderer extends JLabel implements TableCellRenderer {

    private final Color[] COLORS = new Color[]{
            Color.decode("#4B6EAF"),
            Color.decode("#bbbbbb"),
            Color.decode("#45494A"),
            Color.decode("#bbbbbb")
    };

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

    public void selectedColors(boolean isSelected) {
        setOpaque(true);
        if ( isSelected ) {
            setBackground(COLORS[0]);
            setForeground(COLORS[1]);
        } else {
            setBackground(COLORS[2]);
            setForeground(COLORS[3]);
        }

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocused,
                                                   int row, int col) {
        Publication publication;
        switch (col) {
            case 0:
                selectedColors(isSelected);
                publicationId = (int) value;
                publication = daoPublication.getById(publicationId);
                User user = daoUser.getById(publication.getUser_id_fk());
                switch (user.getUser_type_id_fk()) {
                    case 1:
                        setIcon(ImageUtil.getInstance().getIcon("shield_icon.png"));
                        break;
                    case 2:
                        setIcon(ImageUtil.getInstance().getIcon("user_icon.png"));
                        break;
                    default:
                        setIcon(null);
                }
                setText(user.getUsername());
                break;
            case 1:
                selectedColors(isSelected);
                setIcon(null);
                publicationId = (int) value;
                publication = daoPublication.getById(publicationId);
                if ( publication.getText().length() >= 10 ) {
                    setForeground(Color.decode("#FF5A52"));
                } else {
                    setForeground(Color.decode("#42FF78"));
                }

                setText(publication.getText());
                break;
            case 2:
                selectedColors(isSelected);

                setIcon(null);
                publicationId = (int) value;
                publication = daoPublication.getById(publicationId);

                setText(publication.getDate().toString());
                break;
            case 3:
                selectedColors(isSelected);

                setIcon(null);
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
