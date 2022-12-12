package org.wahlzeit.model;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.utils.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "AbstractFactory",
        participants = {
                "Photo"
        }
)
public class MyPhoto extends Photo {

    protected String title;

    protected String description;

    public MyPhoto() {
        super();
    }

    public MyPhoto(ResultSet rset) throws SQLException {
        super(rset);
    }

    public MyPhoto(PhotoId id) {
        super(id);
    }

    /**
     *
     */
    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = PhotoId.getIdFromInt(rset.getInt("id"));

        ownerId = rset.getInt("owner_id");
        ownerName = rset.getString("owner_name");

        ownerNotifyAboutPraise = rset.getBoolean("owner_notify_about_praise");
        ownerEmailAddress = EmailAddress.getFromString(rset.getString("owner_email_address"));
        ownerLanguage = Language.getFromInt(rset.getInt("owner_language"));
        ownerHomePage = StringUtil.asUrl(rset.getString("owner_home_page"));

        width = rset.getInt("width");
        height = rset.getInt("height");

        tags = new Tags(rset.getString("tags"));

        status = PhotoStatus.getFromInt(rset.getInt("status"));
        praiseSum = rset.getInt("praise_sum");
        noVotes = rset.getInt("no_votes");

        creationTime = rset.getLong("creation_time");

        maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);

        location = LocationManager.getInstance().getLocation(rset.getInt("location"));

        title = rset.getString("title");
        description = rset.getString("description");
    }

    /**
     *
     */
    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id.asInt());
        rset.updateInt("owner_id", ownerId);
        rset.updateString("owner_name", ownerName);
        rset.updateBoolean("owner_notify_about_praise", ownerNotifyAboutPraise);
        rset.updateString("owner_email_address", ownerEmailAddress.asString());
        rset.updateInt("owner_language", ownerLanguage.asInt());
        rset.updateString("owner_home_page", ownerHomePage.toString());
        rset.updateInt("width", width);
        rset.updateInt("height", height);
        rset.updateString("tags", tags.asString());
        rset.updateInt("status", status.asInt());
        rset.updateInt("praise_sum", praiseSum);
        rset.updateInt("no_votes", noVotes);
        rset.updateLong("creation_time", creationTime);
        rset.updateInt("location", location.getId());
        rset.updateString("title", title);
        rset.updateString("description", description);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
