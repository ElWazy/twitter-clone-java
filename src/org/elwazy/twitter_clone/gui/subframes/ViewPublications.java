package org.elwazy.twitter_clone.gui.subframes;

import org.elwazy.twitter_clone.dao.DaoHashtagPublication;
import org.elwazy.twitter_clone.gui.MainFrame;
import org.elwazy.twitter_clone.gui.models.PublicationsTableModel;
import org.elwazy.twitter_clone.gui.render.PublicationTableRenderer;

import javax.swing.*;

public class ViewPublications {

    private JPanel pMain;
    private JScrollPane spPublications;
    private JTable tPublications;
    private JButton btnDelete;

    private DaoHashtagPublication daoHashtagPublication;

    private PublicationsTableModel tableModel;

    public ViewPublications(MainFrame mainFrame) {
        pMain.setVisible(false);

        daoHashtagPublication = mainFrame.getDaoHashtagPublication();

        tableModel = new PublicationsTableModel(daoHashtagPublication.getAll());

        tPublications.setModel(tableModel);
        tPublications.setDefaultRenderer(Integer.class, new PublicationTableRenderer(
                mainFrame.getDaoPublication(),
                mainFrame.getDaoHashtag(),
                mainFrame.getDaoUser(),
                mainFrame.getDaoHashtagPublication()
        ));
    }

    public void refreshTable() {
        tableModel.refreshTable(daoHashtagPublication.getAll());
    }

    public JPanel getpMain() {
        return pMain;
    }
}
