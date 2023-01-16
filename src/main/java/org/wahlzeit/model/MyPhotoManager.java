package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyPhotoManager extends PhotoManager {

    private static final MyPhotoManager photoManager = new MyPhotoManager();

    public static MyPhoto getPhoto(String id) {
        return (MyPhoto) getInstance().getPhotoFromId(PhotoId.getIdFromString(id));
    }

    public static MyPhoto getPhoto(PhotoId id) {
         return (MyPhoto) getInstance().getPhotoFromId(id);
    }

    private MyPhotoManager() {
        super();
        photoTagCollector = MyPhotoFactory.getInstance().createPhotoTagCollector();
    }

    public static boolean hasPhoto(String id) {
        return hasPhoto(PhotoId.getIdFromString(id));
    }

    /**
     *
     */
    public static boolean hasPhoto(PhotoId id) {
        return getPhoto(id) != null;
    }

    public static PhotoManager getInstance() {
        return photoManager;
    }

    /**
     * Method for creating a MyPhoto from a database result set.
     * Calls the factory for the creation of the instance.
     * @param rset result set from the database
     * @return Instance of MyPhoto with attributes from the result set.
     * @throws SQLException exception with handling the result set
     */
    @Override
    protected MyPhoto createObject(ResultSet rset) throws SQLException  {
        return MyPhotoFactory.getInstance().createPhoto(rset);
    }
}
