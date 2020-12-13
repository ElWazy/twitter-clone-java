package org.elwazy.twitter_clone.gui.subframes;

import org.elwazy.twitter_clone.dao.DaoHashtag;
import org.elwazy.twitter_clone.dao.DaoHashtagPublication;
import org.elwazy.twitter_clone.dao.DaoPublication;
import org.elwazy.twitter_clone.gui.MainFrame;
import org.elwazy.twitter_clone.models.Hashtag;
import org.elwazy.twitter_clone.models.HashtagPublication;
import org.elwazy.twitter_clone.models.Publication;
import org.elwazy.twitter_clone.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class AddPublication {
    private JPanel pMain;
    private JLabel lblText;
    private JScrollPane spText;
    private JTextArea txtaText;
    private JLabel lblHashtag;
    private JScrollPane spHashtags;
    private JList lHashtags;
    private JButton btnAdd;

    private User user;
    private DaoPublication daoPublication;
    private DaoHashtagPublication daoHashtagPublication;

    public AddPublication(MainFrame mainFrame) {
        pMain.setVisible(false);

        user = mainFrame.getUser();

        daoPublication = mainFrame.getDaoPublication();
        daoHashtagPublication = mainFrame.getDaoHashtagPublication();

        DaoHashtag daoHashtag = mainFrame.getDaoHashtag();
        List<Hashtag> hashtags = daoHashtag.getAll();
        if ( hashtags != null ) {
            DefaultListModel listModel = new DefaultListModel();
            for (Hashtag hashtag: hashtags) {
                listModel.addElement(hashtag);
            }
            lHashtags.setModel(listModel);
        }
        
        btnAdd.setMnemonic(KeyEvent.VK_P);
        btnAdd.addActionListener(this::publish);
    }

    private void refreshForm() {
        txtaText.setText("");
        lHashtags.setSelectedIndices(new int[]{});
        txtaText.requestFocus();
    }

    private void publish(ActionEvent actionEvent) {
        String text = txtaText.getText();
        if ( !text.isBlank() ) {
            Publication publication = new Publication();
            publication.setUser_id_fk(user.getId());
            publication.setText(text);
            int rowAffected = daoPublication.insert(publication);
            System.out.println("[Insert Publication] " + rowAffected);

            List<Hashtag> selectedValuesList = lHashtags.getSelectedValuesList();
            int publicationId = daoPublication.getIdLastPublication();
            if ( !selectedValuesList.isEmpty() ) {
                for (Hashtag hashtag: selectedValuesList) {
                    HashtagPublication hashtagPublication = new HashtagPublication();
                    hashtagPublication.setPublication_id_fk(publicationId);
                    hashtagPublication.setHashtag_id_fk(hashtag.getId());
                    int rowsAffected = daoHashtagPublication.insert(hashtagPublication);
                    System.out.println("[Insert HashtagPublication] " + rowsAffected);
                }
            } else {
                HashtagPublication hashtagPublication = new HashtagPublication();
                hashtagPublication.setPublication_id_fk(publicationId);
                int rowsAffected = daoHashtagPublication.insertNullHashtag(hashtagPublication);
                System.out.println("[Insert HashtagPublication](null hashtag) " + rowsAffected);
            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Empty Field!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        refreshForm();
    }

    public JPanel getpMain() {
        return pMain;
    }
}
