package org.wahlzeit.model;

public class MyPhotoManager extends PhotoManager {

    private static final MyPhotoManager photoManager = new MyPhotoManager();

    public static Photo getPhoto(String id) {
        return getInstance().getPhotoFromId(PhotoId.getIdFromString(id));
    }

    public static Photo getPhoto(PhotoId id) {
        return getInstance().getPhotoFromId(id);
    }

    private MyPhotoManager() {
        super();
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
}
