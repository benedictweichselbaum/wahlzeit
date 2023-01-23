package org.wahlzeit.model.flower;

import org.wahlzeit.annotations.CollaborationBinding;
import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * public collaboration MyPhotoFlower {
 *     public role MyPhoto {
 *         // Owner of the flower
 *         // Is client of the flower
 *     }
 *
 *     public role Flower {
 *          // Domain role/class adding information to the Photo
 *          public Integer getId();
 *     }
 * }
 *
 * public collaboration TypeObject {
 *     public role Type {
 *          // Provides BaseObjects with common properties.
 *          BaseObject createInstance(Integer id);
 *          boolean hasInstance(BaseObject b);
 *          Integer getId();
 *     }
 *
 *     public role BaseObject {
 *          // Gets common properties from its Type.
 *          FlowerType getType();
 *          Integer getId();
 *     }
 * }
 *
 * public collaboration ConcreteObjectManager {
 *     public role ConcreteDataObject {
 *          // Gets managed by the ConcreteManager by storing the object with its ID as the key.
 *         Integer getId();
 *     }
 *
 *     public role ConcreteManager {
 *         // No methods. The Manager uses the ConcreteDataObjects without them using the Manager themselves.
 *         // The Manager is therefor a client of the DataObject.
 *     }
 * }
 */
@CollaborationBinding(
        binds = {"MyPhotoFlower.Flower", "TypeObject.BaseObject", "ConcreteObjectManger.ConcreteDataObject"}
)
public class Flower extends DataObject {

    private Integer id = null;

    private FlowerType type;

    /**
     * Constructor creating Flower from ID and FlowerType.
     * Fills respective fields with inputs.
     * @param id ID of new Flower
     * @param type type of new Flower
     */
    public Flower(Integer id, FlowerType type) {
        this.id = id;
        this.type = type;
        incWriteCount();
    }

    /**
     * Constructor creating Flower from ResultSet.
     * Calls the readFrom-method for filling the fields.
     * @param rset
     * @throws SQLException
     */
    protected Flower (ResultSet rset) throws SQLException {
        readFrom(rset);
        incWriteCount();
    }

    public FlowerType getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getIdAsString() {
        return String.valueOf(id);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = rset.getInt("id");
        type = FlowerManager.getInstance().getFlowerType(rset.getInt("flower_type"));
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id);
        rset.updateInt("flower_type", type.getId());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        stmt.setInt(pos, id);
    }
}
