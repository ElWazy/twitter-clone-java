package org.elwazy.twitter_clone.gui.render;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.ui.FlatLabelUI;
import org.elwazy.twitter_clone.dao.DaoUserType;
import org.elwazy.twitter_clone.util.ImageUtil;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class UsersTableRenderer extends JLabel implements TableCellRenderer {

    private DaoUserType daoUserType;

    private final Color[] COLORS = new Color[]{
            Color.decode("#4B6EAF"),
            Color.decode("#bbbbbb"),
            Color.decode("#45494A"),
            Color.decode("#bbbbbb")
    };

    public UsersTableRenderer(DaoUserType daoUserType){
        this.daoUserType = daoUserType;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int col) {
        switch (col) {
            case 0:
            case 1:
                setOpaque(true);
                if ( isSelected ) {
                    setBackground(COLORS[0]);
                    setForeground(COLORS[1]);
                } else {
                    setBackground(COLORS[2]);
                    setForeground(COLORS[3]);
                }
                setIcon(null);
                setText( String.valueOf(value) );
                break;

            case 2:
                if ( isSelected ) {
                    setBackground(COLORS[0]);
                    setForeground(COLORS[1]);
                } else {
                    setBackground(COLORS[2]);
                    setForeground(COLORS[3]);
                }

                int userTypeIdFk = (int) value;
                if ( userTypeIdFk == 1 ) {
                    setIcon(ImageUtil.getInstance().getIcon("shield_icon.png"));
                } else {
                    setIcon(ImageUtil.getInstance().getIcon("user_icon.png"));
                }

                String name = daoUserType.getById(userTypeIdFk).getName();
                setText( name );
                break;
            default:
                System.out.println(col);
        }
        return this;
    }
}
