package no.hiof.set.gruppe.model.user;

import org.jetbrains.annotations.Contract;

/**
 * This class represents information about the connection between
 * a user and a arrangement.
 */
public class UserConnectedArrangement {
    private final String ID;
    private final String USERNAME;

    /**
     * @param id String
     * @param userName String
     */
    @Contract(pure = true)
    public UserConnectedArrangement(String id, String userName) {
        ID = id;
        this.USERNAME = userName;
    }

    /**
     * @return String
     */
    public String getID() { return ID; }

    /**
     * @return String
     */
    public String getUSERNAME() { return USERNAME; }

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
}
