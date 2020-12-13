package org.elwazy.twitter_clone.gui.models;

import org.elwazy.twitter_clone.models.HashtagPublication;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PublicationsTableModel extends AbstractTableModel {

    private final String[] COLS = new String[]{"User", "Text", "Date", "Hashtags"};

    private List<HashtagPublication> publications;

    public PublicationsTableModel(List<HashtagPublication> publications) {
        this.publications = publications;
    }

    public void refreshTable(List<HashtagPublication> publications) {
        this.publications = publications;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return publications.size();
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
            case 1:
            case 2:
            case 3:
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
        HashtagPublication publication = publications.get(row);
        switch (col) {
            case 0:
            case 1:
            case 2:
                return publication.getPublication_id_fk();
            case 3:
                return publication.getHashtag_id_fk();
            default:
                return null;
        }
    }
}
