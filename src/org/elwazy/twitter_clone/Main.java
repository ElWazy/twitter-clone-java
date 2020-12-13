package org.elwazy.twitter_clone;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.elwazy.twitter_clone.gui.Login;
import org.elwazy.twitter_clone.persistence.DefaultDbConnection;
import org.elwazy.twitter_clone.persistence.MyMariaConnection;

import javax.swing.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
            DefaultDbConnection connection = new MyMariaConnection(
                    "localhost",
                    "prueba_03_java",
                    "root",
                    "1324"
            );
            SwingUtilities.invokeLater(() -> new Login(connection));
        } catch (ClassNotFoundException | SQLException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
