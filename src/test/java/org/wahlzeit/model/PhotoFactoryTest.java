package org.wahlzeit.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PhotoFactoryTest {

    @Test
    public void testCreatePhoto() throws Exception {
        PhotoFactory factory = MyPhotoFactory.getInstance();
        Photo createdPhoto = factory.createPhoto();

        assertTrue(createdPhoto instanceof MyPhoto);

        Class<?> photoClass = createdPhoto.getClass();
        assertNotNull(photoClass.getMethod("getTitle"));
        assertNotNull(photoClass.getMethod("getDescription"));
    }

    @Test
    public void testCreatePhoto_withId() throws Exception{
        PhotoFactory factory = MyPhotoFactory.getInstance();
        Photo createdPhoto = factory.createPhoto(PhotoId.NULL_ID);

        assertTrue(createdPhoto instanceof MyPhoto);

        Class<?> photoClass = createdPhoto.getClass();
        assertNotNull(photoClass.getMethod("getTitle"));
        assertNotNull(photoClass.getMethod("getDescription"));
    }
}
