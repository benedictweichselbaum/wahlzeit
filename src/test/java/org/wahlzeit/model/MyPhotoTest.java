package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.wahlzeit.services.DatabaseConnection;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.services.SessionManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyPhotoTest {

    private ResultSet resultSet;

    private LocationManager locationManager;

    private SessionManager sessionManager;

    private DatabaseConnection databaseConnection;

    private PreparedStatement preparedStatement;

    @Before
    public void setUpTestResultSetAsMock() throws Exception {
        databaseConnection = mock(DatabaseConnection.class);
        preparedStatement = mock(PreparedStatement.class);

        when(preparedStatement.toString()).thenReturn("SELECT * FROM test;");
        when(databaseConnection.getReadingStatement(anyString())).thenReturn(preparedStatement);

        try(MockedStatic<SessionManager> sessionManagerMockedStatic = Mockito.mockStatic(SessionManager.class)) {
            sessionManagerMockedStatic.when(SessionManager::getDatabaseConnection).thenReturn(databaseConnection);
            resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            locationManager = mock(LocationManager.class);
        }

        when(resultSet.getInt("id")).thenReturn(0);

        when(resultSet.getInt("owner_id")).thenReturn(1);
        when(resultSet.getString("owner_name")).thenReturn("Test Name");

        when(resultSet.getBoolean("owner_notify_about_praise")).thenReturn(true);
        when(resultSet.getString("owner_email_address")).thenReturn("owner@test.de");
        when(resultSet.getInt("owner_language")).thenReturn(0);
        when(resultSet.getString("owner_home_page")).thenReturn("https://www.homepage.de");

        when(resultSet.getInt("width")).thenReturn(200);
        when(resultSet.getInt("height")).thenReturn(100);

        when(resultSet.getString("tags")).thenReturn("Tag, tag, tags");

        when(resultSet.getInt("status")).thenReturn(0);
        when(resultSet.getInt("praise_sum")).thenReturn(1);
        when(resultSet.getInt("no_votes")).thenReturn(1);

        when(resultSet.getLong("creation_time")).thenReturn(1000L);

        when(resultSet.getInt("location")).thenReturn(1);

        when(resultSet.getString("title")).thenReturn("Title");
        when(resultSet.getString("description")).thenReturn("Description");

        when(locationManager.getLocation(any())).thenReturn(new Location(1,1, 2, 3));

        doNothing().when(resultSet).updateInt(anyString(), anyInt());
        doNothing().when(resultSet).updateString(anyString(), anyString());
        doNothing().when(resultSet).updateBoolean(anyString(), anyBoolean());
        doNothing().when(resultSet).updateLong(anyString(), anyLong());
        doNothing().when(resultSet).updateDouble(anyString(), anyDouble());
    }

    @Test
    public void testConstructorWithNoArgs() {
        Photo photo = new MyPhoto();

        assertNotNull(photo);

        // id is set -> therefore dirty
        assertTrue(photo.isDirty());
    }

    @Test
    public void testConstructorWithId() {
        Photo photo = new MyPhoto(new PhotoId(3));

        assertNotNull(photo);

        // id is set -> therefore dirty
        assertTrue(photo.isDirty());
        assertEquals(3, photo.getId().asInt());
    }

    @Test
    public void testConstructorWithRSet() throws Exception {
        try (MockedStatic<LocationManager> staticLocationManager = Mockito.mockStatic(LocationManager.class)) {
            staticLocationManager.when(LocationManager::getInstance).thenReturn(locationManager);
            Photo photo = new MyPhoto(resultSet);

            // Check if creation is successful
            // Constructor calls readFrom which is tested below
            assertNotNull(photo);
        }
    }

    @Test
    public void testReadFrom() throws Exception {

        try (MockedStatic<LocationManager> staticLocationManager = Mockito.mockStatic(LocationManager.class)) {
            staticLocationManager.when(LocationManager::getInstance).thenReturn(locationManager);

            MyPhoto photo = new MyPhoto();

            photo.readFrom(resultSet);

            assertEquals(0, photo.getId().asInt());
            assertEquals(1, photo.getOwnerId());
            assertEquals("Test Name", photo.getOwnerName());
            assertTrue(photo.getOwnerNotifyAboutPraise());
            assertEquals("owner@test.de", photo.getOwnerEmailAddress().asString());
            assertEquals(Language.ENGLISH, photo.getOwnerLanguage());
            assertEquals("https://www.homepage.de", photo.getOwnerHomePage().toString());
            assertEquals(200, photo.getWidth());
            assertEquals(100, photo.getHeight());

            assertEquals(2, photo.getTags().tags.size());

            assertEquals(PhotoStatus.VISIBLE, photo.getStatus());
            assertEquals(1 / 1, photo.getPraise(), 0);

            assertEquals(1000L, photo.getCreationTime());

            assertEquals(PhotoSize.THUMB, photo.getMaxPhotoSize());

            assertEquals("Title", photo.getTitle());
            assertEquals("Description", photo.getDescription());
        }
    }

    @Test
    public void testWriteOn() throws Exception {
        try (MockedStatic<LocationManager> staticLocationManager = Mockito.mockStatic(LocationManager.class)) {
            staticLocationManager.when(LocationManager::getInstance).thenReturn(locationManager);
            Photo photo = new MyPhoto(resultSet);

            photo.writeOn(resultSet);

            verify(resultSet).updateInt(eq("id"), anyInt());
            verify(resultSet).updateInt(eq("owner_id"), anyInt());
            verify(resultSet).updateString(eq("owner_name"), anyString());
            verify(resultSet).updateBoolean(eq("owner_notify_about_praise"), anyBoolean());
            verify(resultSet).updateString(eq("owner_email_address"), anyString());
            verify(resultSet).updateInt(eq("owner_language"), anyInt());
            verify(resultSet).updateString(eq("owner_home_page"), anyString());
            verify(resultSet).updateInt(eq("width"), anyInt());
            verify(resultSet).updateInt(eq("height"), anyInt());
            verify(resultSet).updateString(eq("tags"), anyString());
            verify(resultSet).updateInt(eq("status"), anyInt());
            verify(resultSet).updateInt(eq("praise_sum"), anyInt());
            verify(resultSet).updateInt(eq("no_votes"), anyInt());
            verify(resultSet).updateLong(eq("creation_time"), anyLong());
            verify(resultSet).updateString(eq("title"), anyString());
            verify(resultSet).updateString(eq("description"), anyString());
        }
    }

    @Test
    public void testGetIdAsString() {
        Photo photo = new MyPhoto(new PhotoId(100));

        String actualStringId = photo.getIdAsString();

        assertEquals("100", actualStringId);
    }

    @Test
    public void testGetPraise() {
        Photo photo = new MyPhoto();
        photo.praiseSum = 500;
        photo.noVotes = 25;

        assertEquals(500 / 25, photo.getPraise(), 0);
    }

    @Test
    public void testSetOwnerId() {
        Photo photo = new MyPhoto();

        // make not dirty
        photo.resetWriteCount();

        photo.setOwnerId(10);

        assertEquals(10, photo.getOwnerId());
        assertTrue(photo.isDirty());
    }

    @Test
    public void testSetOwnerName() {
        Photo photo = new MyPhoto();

        // make not dirty
        photo.resetWriteCount();

        photo.setOwnerName("Name");

        assertEquals("Name", photo.getOwnerName());
        assertTrue(photo.isDirty());
    }

    @Test
    public void testGetSummary() throws Exception {
            Photo photo = new MyPhoto(PhotoId.NULL_ID);
            photo.setOwnerName("Test Name");

            assertEquals("Foto von Test Name", photo.getSummary(new GermanModelConfig()));
    }

    @Test
    public void testGetCaption() throws Exception {
        try (MockedStatic<LocationManager> staticLocationManager = Mockito.mockStatic(LocationManager.class)) {
            staticLocationManager.when(LocationManager::getInstance).thenReturn(locationManager);
            Photo photo = new MyPhoto(resultSet);

            assertEquals("Foto von <a href=\"https://www.homepage.de\" rel=\"nofollow\">Test Name</a>", photo.getCaption(new GermanModelConfig()));
        }
    }

    @Test
    public void testSetOwnerNotifyAboutPraise() {
        Photo photo = new MyPhoto();

        // make not dirty
        photo.resetWriteCount();

        photo.setOwnerNotifyAboutPraise(false);

        assertFalse(photo.getOwnerNotifyAboutPraise());
        assertTrue(photo.isDirty());
    }

    @Test
    public void testSetOwnerEmailAddress() {
        Photo photo = new MyPhoto();

        // make not dirty
        photo.resetWriteCount();

        photo.setOwnerEmailAddress(EmailAddress.getFromString("mail@test.de"));

        assertEquals("mail@test.de", photo.getOwnerEmailAddress().asString());
        assertTrue(photo.isDirty());
    }

    @Test
    public void testSetOwnerLanguage() {
        Photo photo = new MyPhoto();

        // make not dirty
        photo.resetWriteCount();

        photo.setOwnerLanguage(Language.GERMAN);

        assertEquals(Language.GERMAN, photo.getOwnerLanguage());
        assertTrue(photo.isDirty());
    }

    @Test
    public void testSetOwnerHomePage() throws MalformedURLException {
        Photo photo = new MyPhoto();

        // make not dirty
        photo.resetWriteCount();

        photo.setOwnerHomePage(new URL("https://test.test.de"));

        assertEquals("https://test.test.de", photo.getOwnerHomePage().toString());
        assertTrue(photo.isDirty());
    }

    @Test
    public void testHasSameOwner() {
        Photo photo1 = new MyPhoto();
        Photo photo2 = new MyPhoto();

        photo1.setOwnerEmailAddress(EmailAddress.getFromString("mail@test.de"));
        photo2.setOwnerEmailAddress(EmailAddress.getFromString("mail@test.de"));

        assertTrue(photo1.hasSameOwner(photo2));

        photo2.setOwnerEmailAddress(EmailAddress.getFromString("mail2@test.de"));

        assertFalse(photo1.hasSameOwner(photo2));
    }

    @Test
    public void testIsWiderThanHigher() {
        Photo photo1 = new MyPhoto();
        photo1.setWidthAndHeight(100, 200);

        Photo photo2 = new MyPhoto();
        photo2.setWidthAndHeight(200, 100);

        assertTrue(photo2.isWiderThanHigher());
        assertFalse(photo1.isWiderThanHigher());
    }

    @Test
    public void testGetThumbWidth_widerThanHigher() {
        Photo photo = new MyPhoto();
        photo.setWidthAndHeight(200, 100);

        assertEquals(Photo.MAX_THUMB_PHOTO_WIDTH, photo.getThumbWidth());
    }

    @Test
    public void testGetThumbWidth_not_widerThanHigher() {
        Photo photo = new MyPhoto();
        photo.setWidthAndHeight(100, 200);

        assertEquals(100 * Photo.MAX_THUMB_PHOTO_HEIGHT / 200, photo.getThumbWidth());
    }

    @Test
    public void testGetThumbHight_widerThanHigher() {
        Photo photo = new MyPhoto();
        photo.setWidthAndHeight(100, 200);

        assertEquals(Photo.MAX_THUMB_PHOTO_HEIGHT, photo.getThumbHeight());
    }

    @Test
    public void testGetThumbHeight_widerThanHigher() {
        Photo photo = new MyPhoto();
        photo.setWidthAndHeight(200, 100);

        assertEquals(100 * Photo.MAX_THUMB_PHOTO_WIDTH / 200, photo.getThumbHeight());
    }

    @Test
    public void testSetWidthAndHight() {
        Photo photo = new MyPhoto();

        // make not dirty
        photo.resetWriteCount();

        photo.setWidthAndHeight(200, 300);

        assertEquals(200, photo.getWidth());
        assertEquals(300, photo.getHeight());
        assertEquals(PhotoSize.EXTRA_SMALL, photo.maxPhotoSize);
        assertTrue(photo.isDirty());
    }

    @Test
    public void testHasPhotoSize() {
        Photo photo = new MyPhoto();
        photo.setWidthAndHeight(200, 300);

        assertTrue(photo.hasPhotoSize(PhotoSize.THUMB));
        assertTrue(photo.hasPhotoSize(PhotoSize.EXTRA_SMALL));
        assertFalse(photo.hasPhotoSize(PhotoSize.LARGE));
    }

    @Test
    public void testGetPraiseAsString() {
        Photo photo = new MyPhoto();
        photo.addToPraise(20);

        assertEquals("15.00", photo.getPraiseAsString(new GermanModelConfig()));
    }

    @Test
    public void testAddToPraise() {
        Photo photo = new MyPhoto();

        photo.resetWriteCount();

        photo.addToPraise(1);

        assertTrue(photo.isDirty());
        assertEquals(11 / 2.0, photo.getPraise(), 0);
    }

    @Test
    public void testSetStatus() {
        Photo photo = new MyPhoto();

        // make not dirty
        photo.resetWriteCount();

        photo.setStatus(PhotoStatus.VISIBLE);

        assertEquals(PhotoStatus.VISIBLE, photo.getStatus());
        assertTrue(photo.isDirty());
    }

    @Test
    public void testSetTags() {
        Photo photo = new MyPhoto();

        // make not dirty
        photo.resetWriteCount();

        photo.setTags(new Tags("tag"));

        assertEquals(1, photo.getTags().getSize());
        assertTrue(photo.isDirty());
    }
}
