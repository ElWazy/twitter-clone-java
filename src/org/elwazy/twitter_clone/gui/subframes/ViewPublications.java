package org.elwazy.twitter_clone.gui.subframes;

import org.elwazy.twitter_clone.dao.DaoHashtagPublication;
import org.elwazy.twitter_clone.dao.DaoPublication;
import org.elwazy.twitter_clone.gui.MainFrame;
import org.elwazy.twitter_clone.gui.models.PublicationsTableModel;
import org.elwazy.twitter_clone.gui.render.PublicationTableRenderer;
import org.elwazy.twitter_clone.models.Publication;
import org.elwazy.twitter_clone.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ViewPublications {

    private JPanel pMain;
    private JScrollPane spPublications;
    private JTable tPublications;
    private JButton btnDelete;

    private User user;

    private DaoHashtagPublication daoHashtagPublication;
    private DaoPublication daoPublication;

    private PublicationsTableModel tableModel;

    public ViewPublications(MainFrame mainFrame) {
        pMain.setVisible(false);

        user = mainFrame.getUser();

        daoHashtagPublication = mainFrame.getDaoHashtagPublication();
        daoPublication = mainFrame.getDaoPublication();

        tableModel = new PublicationsTableModel(daoHashtagPublication.getAll());

        tPublications.setModel(tableModel);
        tPublications.setDefaultRenderer(Integer.class, new PublicationTableRenderer(
                daoPublication,
                mainFrame.getDaoHashtag(),
                mainFrame.getDaoUser(),
                daoHashtagPublication
        ));

        btnDelete.setMnemonic(KeyEvent.VK_D);
        btnDelete.addActionListener(this::deletePublication);
    }

    public void refreshTable() {
        tableModel.refreshTable(daoHashtagPublication.getAll());
    }

    private void deleteAndReport(int publicationId) {
        int rowsAffected = daoHashtagPublication.delete(publicationId);
        System.out.println("[Delete HashtagPublication] " + rowsAffected);
        rowsAffected = daoPublication.delete(publicationId);
        System.out.println("[Delete Publication] " + rowsAffected);
    }

    private void deletePublication(ActionEvent actionEvent) {
        int selectedRow = tPublications.getSelectedRow();
        if ( selectedRow != -1 ) {
            int publicationId = (int) tPublications.getValueAt(selectedRow, 1);
            Publication publication = daoPublication.getById(publicationId);

            if ( user.getUser_type_id_fk() == 1 ) {
                deleteAndReport(publicationId);
            } else {
                if (user.getId() == publication.getUser_id_fk()) {
                    deleteAndReport(publicationId);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "You can only delete your posts!!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }

        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Not Selected Row!!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        refreshTable();
    }

    public JPanel getpMain() {
        return pMain;
    }
}
