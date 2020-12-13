package org.elwazy.twitter_clone.gui;

import org.elwazy.twitter_clone.dao.DaoUser;
import org.elwazy.twitter_clone.dao.Impl.ImplDaoUser;
import org.elwazy.twitter_clone.models.User;
import org.elwazy.twitter_clone.persistence.DefaultDbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

    // region Components
    private JPanel pMain;
    private JLabel lblTitle;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JLabel lblPassword;
    private JPasswordField pswPassword;
    private JButton btnEnter;

    // endregion

    private DefaultDbConnection connection;
    private DaoUser daoUser;

    public Login(DefaultDbConnection connection) {
        super("Bomba");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(260, 320);
        add(pMain);
        setVisible(true);

        this.connection = connection;
        daoUser = new ImplDaoUser(this.connection);

        lblTitle.setFont( new Font("Dialog", Font.PLAIN, 24) );

        btnEnter.setMnemonic(KeyEvent.VK_E);
        btnEnter.addActionListener(this::signIn);
    }

    private void refreshForm() {
        txtUsername.setText("");
        pswPassword.setText("");
        txtUsername.requestFocus();
    }

    private void signIn(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = new String(pswPassword.getPassword());

        if ( !username.isBlank() && !password.isBlank() ) {
            User user = daoUser.login(username, password);

            if ( user != null ) {
                SwingUtilities.invokeLater(() -> new MainFrame(this, user));
            } else {
                JOptionPane.showMessageDialog(this, "Not found in db", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Empty fields", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        refreshForm();
    }


    public DefaultDbConnection getConnection() {
        return connection;
    }
}
