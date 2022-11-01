package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyPhotoFactory extends PhotoFactory {

    private MyPhotoFactory() {
        // Do nothing
    }

    private static MyPhotoFactory photoFactory = null;

    public static synchronized MyPhotoFactory getInstance() {
        if (photoFactory == null) {
            photoFactory = new MyPhotoFactory();
        }
        return photoFactory;
    }

    public static void initialize() {
        getInstance();
    }

    @Override
    public MyPhoto createPhoto() {
        return new MyPhoto();
    }

    @Override
    public MyPhoto createPhoto(PhotoId id) {
        return new MyPhoto(id);
    }

    @Override
    public MyPhoto createPhoto(ResultSet rs) throws SQLException {
        return new MyPhoto(rs);
    }
}
