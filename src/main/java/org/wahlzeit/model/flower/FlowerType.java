package org.wahlzeit.model.flower;

import org.wahlzeit.annotations.CollaborationBinding;
import org.wahlzeit.model.exceptions.SuperTypeAlreadyExistsException;
import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
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
 */
@CollaborationBinding(
        binds = {"TypeObject.Type"}
)
public class FlowerType extends DataObject {

    private Integer id;
    private String name;

    private FlowerType superType;

    private Set<FlowerType> subTypes;

    protected FlowerType(Integer id, String name) {
        this.name = name;
        this.id = id;
        superType = null;
        subTypes = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setSuperType(FlowerType superType) {
        if (this.superType == null) {
            this.superType = superType;
        } else {
            throw new SuperTypeAlreadyExistsException();
        }
    }

    /**
     * Method that creates a new Flower with type this and given ID.
     * Calls the Flower constructor for object creation.
     * @param id given ID
     * @return new Flower instance with this type
     */
    public Flower createInstance(Integer id) {
        return new Flower(id, this);
    }

    public String getName() {
        return name;
    }

    public FlowerType getSuperType() {
        return superType;
    }

    public Iterator<FlowerType> getSubTypeIterator() {
        return subTypes.iterator();
    }

    public void addSubType(FlowerType type) {
        assert type != null : "Type is null for new sub type";
        type.setSuperType(this);
        subTypes.add(type);
    }

    public boolean hasInstance(Flower flower) {
        assert flower != null;

        if (flower.getType() == this) {
            return true;
        } else {
            for (FlowerType subType : subTypes) {
                if (subType.hasInstance(flower)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isSubType(FlowerType type) {
        if (type == this) {
            return true;
        } else {
            for (FlowerType subType : subTypes) {
                if (subType.isSubType(type)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public String getIdAsString() {
        return String.valueOf(id);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = rset.getInt("id");
        name = rset.getString("name");
        Integer superTypeId = rset.getInt("supertype");
        FlowerType superTypeRset = FlowerManager.getInstance().getFlowerType(superTypeId);
        setSuperType(superTypeRset);
        superTypeRset.addSubType(this);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id);
        rset.updateString("name", name);
        rset.updateInt("supertype", superType.getId());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        stmt.setInt(pos, id);
    }
}
