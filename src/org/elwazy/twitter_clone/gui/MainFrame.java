package org.elwazy.twitter_clone.gui;

import org.elwazy.twitter_clone.dao.*;
import org.elwazy.twitter_clone.dao.Impl.*;
import org.elwazy.twitter_clone.gui.subframes.AddPublication;
import org.elwazy.twitter_clone.gui.subframes.ViewPublications;
import org.elwazy.twitter_clone.models.User;
import org.elwazy.twitter_clone.persistence.DefaultDbConnection;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame{

    // region Components
    private JPanel pMain;
    private JPanel pContainer;
    private JButton btnAdd;
    private JButton btnView;
    private JLabel lblAux;

    // endregion

    private DefaultDbConnection connection;
    private User user;

    private DaoUser daoUser;
    private DaoUserType daoUserType;
    private DaoHashtagPublication daoHashtagPublication;
    private DaoPublication daoPublication;
    private DaoHashtag daoHashtag;

    private AddPublication addPublication;
    private ViewPublications viewPublications;
    private List<JPanel> panels;

    public MainFrame(Login login, User user) {
        super("Bomba");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 640);
        add(pMain);
        setVisible(true);
        login.setVisible(false);

        connection = login.getConnection();
        this.user = user;

        buildConnections(connection);
        buildSubPanels();

        setJMenuBar(configMenuBar());

        btnAdd.setMnemonic(KeyEvent.VK_A);
        btnView.setMnemonic(KeyEvent.VK_V);

        btnAdd.addActionListener((e) -> viewSubPanel(addPublication.getpMain()));
        btnView.addActionListener((e) -> {
            viewSubPanel(viewPublications.getpMain());
            viewPublications.refreshTable();
        });
    }

    private void buildConnections(DefaultDbConnection connection) {
        daoUser = new ImplDaoUser(connection);
        daoUserType = new ImplDaoUserType(connection);
        daoHashtagPublication = new ImplDaoHashtagPublication(connection);
        daoPublication = new ImplDaoPublication(connection);
        daoHashtag = new ImplDaoHashtag(connection);
    }

    private void buildSubPanels() {
        pContainer.setLayout(new OverlayLayout(pContainer));

        addPublication = new AddPublication(this);
        viewPublications = new ViewPublications(this);

        addNewSubPanel(addPublication.getpMain());
        addNewSubPanel(viewPublications.getpMain());
        viewSubPanel(viewPublications.getpMain());
    }

    private void addNewSubPanel(JPanel panel) {
        if ( panels == null ) {
            panels = new ArrayList<>();
        }
        panels.add(panel);
        pContainer.add(panel);
    }

    private void viewSubPanel(JPanel panel) {
        if ( panels != null ) {
            for (JPanel p : panels) {
                if ( panel.equals(p) ) {
                    p.setVisible(true);
                } else {
                    p.setVisible(false);
                }
            }
        }
    }

    private JMenuBar configMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu session = new JMenu("Session");
        JMenuItem closeSession = new JMenuItem("Close Session");
        session.setMnemonic(KeyEvent.VK_1);
        closeSession.setMnemonic(KeyEvent.VK_Q);
        closeSession.addActionListener(e -> {
            new Login(connection);
            dispose();
        });
        session.add(closeSession);
        bar.add(session);


        if ( user.getUser_type_id_fk() == 1 ) {
            JMenu users = new JMenu("Users");
            JMenuItem administrate = new JMenuItem("Administrate");
            users.setMnemonic(KeyEvent.VK_2);
            administrate.setMnemonic(KeyEvent.VK_W);
            administrate.addActionListener(e -> new AdminUser(this));
            users.add(administrate);
            bar.add(users);
        }

        return bar;
    }

    public User getUser() {
        return user;
    }

    public DaoUser getDaoUser() {
        return daoUser;
    }

    public DaoUserType getDaoUserType() {
        return daoUserType;
    }

    public DaoHashtagPublication getDaoHashtagPublication() {
        return daoHashtagPublication;
    }

    public DaoPublication getDaoPublication() {
        return daoPublication;
    }

    public DaoHashtag getDaoHashtag() {
        return daoHashtag;
    }

    public ViewPublications getViewPublications() {
        return viewPublications;
    }
}
