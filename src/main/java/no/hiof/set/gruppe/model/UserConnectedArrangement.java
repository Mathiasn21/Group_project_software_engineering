package no.hiof.set.gruppe.model;

public class UserConnectedArrangement {
    private final int ID;
    private final String USERNAME;
    public UserConnectedArrangement(int id, String userName) {
        ID = id;
        this.USERNAME = userName;
    }

    public int getID() { return ID; }
    public String getUSERNAME() { return USERNAME; }

    //@Override
    public boolean equals(UserConnectedArrangement obj) {
        return (ID == obj.getID() && USERNAME.equals(obj.getUSERNAME()));
    }
}
