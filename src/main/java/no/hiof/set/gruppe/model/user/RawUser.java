package no.hiof.set.gruppe.model.user;

import org.jetbrains.annotations.Contract;

public class RawUser {
    private final String fName;
    private final String lName;
    private final String bDate;
    private final String cityCode;
    private final String streetAddress;
    private final String eMail;
    private final String passHash;

    @Contract(pure = true)
    public RawUser(String fName, String lName, String bDate, String cityCode, String streetAddress, String eMail, String pass) {
        this.fName = fName;
        this.lName = lName;
        this.bDate = bDate;
        this.cityCode = cityCode;
        this.streetAddress = streetAddress;
        this.eMail = eMail;
        this.passHash = pass;
    }

    public String getfName() {return fName;}
    public String getlName() {return lName;}
    public String getbDate() {return bDate;}
    public String getCityCode() {return cityCode;}
    public String getStreetAddress() {return streetAddress;}
    public String geteMail() {return eMail;}
    public String getPassHash() {return passHash;}
}
