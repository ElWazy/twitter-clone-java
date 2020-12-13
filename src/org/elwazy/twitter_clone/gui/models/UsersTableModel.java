package org.elwazy.twitter_clone.gui.models;

import org.elwazy.twitter_clone.models.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UsersTableModel extends AbstractTableModel {

    private final String[] COLS = new String[]{"Id", "Username", "Type"};
    private List<User> users;

    public UsersTableModel(List<User> users) {
        this.users = users;
    }

    public void refreshTable(List<User> users) {
        this.users = users;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return COLS.length;
    }

    @Override
    public String getColumnName(int i) {
        return COLS[i];
    }

    @Override
    public Class<?> getColumnClass(int i) {
        switch (i) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int col) {
        User user = users.get(row);
        switch (col){
            case 0:
                return user.getId();
            case 1:
                return user.getUsername();
            case 2:
                return user.getUser_type_id_fk();
            default:
                return null;
        }
    }

}
