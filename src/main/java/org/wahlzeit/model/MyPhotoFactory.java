package org.wahlzeit.model;

import org.wahlzeit.annotations.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "AbstractFactory",
        participants = {
                "PhotoFactory"
        }
)
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

    /**
     * Factory method for creating a MyPhoto from an ID.
     * Calls the constructor for object creation.
     * @param id ID for new MyPhoto
     * @return new MyPhoto
     */
    @Override
    public MyPhoto createPhoto(PhotoId id) {
        return new MyPhoto(id);
    }

    /**
     * Factory method for creating a MyPhoto from a ResultSet.
     * Calls the constructor for object creation.
     * @param rs result set for creation
     * @return new MyPhoto
     * @throws SQLException exceptions from handling the ResultSet
     */
    @Override
    public MyPhoto createPhoto(ResultSet rs) throws SQLException {
        return new MyPhoto(rs);
    }
}
