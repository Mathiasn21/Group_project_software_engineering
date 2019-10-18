package no.hiof.set.gruppe.model;

public class UserConnectedArrangement {
    private final String ID;
    private final String USERNAME;
    public UserConnectedArrangement(String id, String userName) {
        ID = id;
        this.USERNAME = userName;
    }

    public String getID() { return ID; }
    public String getUSERNAME() { return USERNAME; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserConnectedArrangement)) return false;
        UserConnectedArrangement that = (UserConnectedArrangement) o;
        return USERNAME.equals(that.USERNAME) && ID.equals(that.ID);
    }
}
