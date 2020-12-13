package org.elwazy.twitter_clone.util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class ImageUtil {

    private static String RELATIVE_URL = "/org/elwazy/twitter_clone/resources/";

    private static ImageUtil _instance;

    private ImageUtil(){
    }

    public static ImageUtil getInstance(){
        if(_instance == null){
            _instance = new ImageUtil();
        }
        return _instance;
    }

    public ImageIcon getIcon(String resourceName) throws IllegalArgumentException {
        String location = RELATIVE_URL + resourceName;
        URL imgUrl = getClass().getResource(location);
        if (imgUrl != null)
            return new ImageIcon(imgUrl);
        else
            throw new IllegalArgumentException("This icon file does not exist on: " + location);
    }

}