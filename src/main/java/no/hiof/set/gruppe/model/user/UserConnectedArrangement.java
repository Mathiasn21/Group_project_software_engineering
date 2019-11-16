package no.hiof.set.gruppe.model.user;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Contracts
 * 4. Public Getter Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.EntityConnectedToUser;
import no.hiof.set.gruppe.core.IBaseEntity;
import org.jetbrains.annotations.Contract;

/**
 * This class represents information about the connection between
 * a user and a arrangement.
 */
public class UserConnectedArrangement implements IBaseEntity, EntityConnectedToUser {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private final String ID;
    private final String USERNAME;

    /**
     * @param id String
     * @param userName String
     */

    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
    @Contract(pure = true)
    public UserConnectedArrangement(String id, String userName) {
        ID = id;
        this.USERNAME = userName;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserConnectedArrangement)) return false;
        UserConnectedArrangement that = (UserConnectedArrangement) o;
        return USERNAME.equals(that.USERNAME) && ID.equals(that.ID);
    }

    // --------------------------------------------------//
    //                4.Public Getter Methods            //
    // --------------------------------------------------//
    /**
     * @return String
     */
    public String getID() { return ID; }

    /**
     * @return String
     */
    @Override
    public String getUSERNAME() { return USERNAME; }
}
