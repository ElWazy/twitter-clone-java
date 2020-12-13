package org.elwazy.twitter_clone.gui;

import org.elwazy.twitter_clone.dao.DaoUser;
import org.elwazy.twitter_clone.dao.DaoUserType;
import org.elwazy.twitter_clone.gui.models.UsersTableModel;
import org.elwazy.twitter_clone.gui.render.UsersTableRenderer;
import org.elwazy.twitter_clone.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class AdminUser extends JFrame {

    // region Components
    private JPanel pMain;
    private JTabbedPane tpMain;
    private JPanel pView;
    private JScrollPane spUsers;
    private JTable tUsers;
    private JScrollPane spDismissed;
    private JTable tDismissed;
    private JButton btnDismiss;
    private JButton btnRejoin;
    private JPanel pAdd;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JLabel lblPassword;
    private JPasswordField pswPassword;
    private JLabel lblUserType;
    private JRadioButton rGuest;
    private JRadioButton rAdmin;
    private JButton btnAdd;

    // endregion

    private ButtonGroup bgType;

    private UsersTableModel tableModelUsers;
    private UsersTableModel tableModelDismisses;

    private DaoUser daoUser;
    private DaoUserType daoUserType;

    public AdminUser(MainFrame mainFrame) {
        super("Bomba");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(720, 640);
        add(pMain);
        setVisible(true);

        buildConnections(mainFrame);

        buildTable(tUsers, true);
        buildTable(tDismissed, false);

        buildRadioButtons();

        buildMnemonics();

        btnDismiss.addActionListener(this::dismissUser);
        btnRejoin.addActionListener(this::rejoinUser);
        btnAdd.addActionListener(this::addUser);
    }

    private void buildConnections(MainFrame mainFrame) {
        daoUser = mainFrame.getDaoUser();
        daoUserType = mainFrame.getDaoUserType();
    }

    private void buildTable(JTable table, boolean isActives) {
        UsersTableModel tableModel;
        if ( isActives ) {
            tableModel = new UsersTableModel(daoUser.getAll());
            tableModelUsers = tableModel;
        } else {
            tableModel = new UsersTableModel(daoUser.getAllDismissed());
            tableModelDismisses = tableModel;
        }

        table.setModel(tableModel);
        table.setDefaultRenderer(Integer.class, new UsersTableRenderer(daoUserType));
        table.setDefaultRenderer(String.class, new UsersTableRenderer(daoUserType));
    }

    private void buildRadioButtons() {
        bgType = new ButtonGroup();

        rGuest.setSelected(true);
        rAdmin.setSelected(false);

        rGuest.setActionCommand("2");
        rAdmin.setActionCommand("1");

        bgType.add(rGuest);
        bgType.add(rAdmin);
    }

    private void buildMnemonics() {
        tpMain.setMnemonicAt(0, KeyEvent.VK_1);
        tpMain.setMnemonicAt(1, KeyEvent.VK_2);

        btnDismiss.setMnemonic(KeyEvent.VK_D);
        btnRejoin.setMnemonic(KeyEvent.VK_R);
        btnAdd.setMnemonic(KeyEvent.VK_A);
    }

    private void refreshTables() {
        List<User> users = daoUser.getAll();
        List<User> dismisses = daoUser.getAllDismissed();
        List<User> aux = new ArrayList<>();

        if ( users != null) {
            tableModelUsers.refreshTable(users);
        } else {
            tableModelUsers.refreshTable(aux);
        }

        if ( dismisses != null ) {
            tableModelDismisses.refreshTable(dismisses);
        } else {
            tableModelDismisses.refreshTable(aux);
        }
    }

    private void refreshForm() {
        txtUsername.setText("");
        pswPassword.setText("");
        rGuest.setSelected(true);
        rAdmin.setSelected(false);
        txtUsername.requestFocus();
    }

    private void userDismissRejoin(JTable table, boolean isDelete) {
        int selectedRow = table.getSelectedRow();
        if ( selectedRow != -1 ) {
            int idUser = (int) table.getValueAt(selectedRow, 0);
            int rowAffected = isDelete ? daoUser.delete(idUser) : daoUser.rejoin(idUser);
            refreshTables();
            System.out.println("[User Dismiss/Rejoin]" + rowAffected );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Not row selected!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void dismissUser(ActionEvent actionEvent) {
        userDismissRejoin(tUsers, true);
    }

    private void rejoinUser(ActionEvent actionEvent) {
        userDismissRejoin(tDismissed, false);
    }

    private void addUser(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = new String(pswPassword.getPassword());
        int type = Integer.parseInt(bgType.getSelection().getActionCommand());

        if ( !username.isBlank() && !password.isBlank() ) {
            User user = new User();
            user.setUsername(username);
            user.setPasswd(password);
            user.setUser_type_id_fk(type);

            int rowAffected = daoUser.insert(user);
            refreshTables();
            System.out.println("[User Add]" + rowAffected );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Empty Field!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        refreshForm();
    }

}
