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
import org.jetbrains.annotations.Contract;

import java.util.UUID;

public class RawUser {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private final String ID;
    private final String fName;
    private final String lName;
    private final String bDate;
    private final String cityCode;
    private final String streetAddress;
    private final String eMail;
    private final String passHash;

    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
    @Contract(pure = true)
    public RawUser(String fName, String lName, String bDate, String cityCode, String streetAddress, String eMail, String pass) {
        this.fName = fName;
        this.lName = lName;
        this.bDate = bDate;
        this.cityCode = cityCode;
        this.streetAddress = streetAddress;
        this.eMail = eMail;
        this.passHash = pass;
        ID = UUID.randomUUID().toString();
    }

    // --------------------------------------------------//
    //                4.Public Getter Methods            //
    // --------------------------------------------------//
    public String getfName() {return fName;}
    public String getlName() {return lName;}
    public String getbDate() {return bDate;}
    public String getCityCode() {return cityCode;}
    public String getStreetAddress() {return streetAddress;}
    public String geteMail() {return eMail;}
    public String getPassHash() {return passHash;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RawUser)) return false;

        RawUser that = (RawUser) o;

        if (!fName.equals(that.fName) || !lName.equals(that.lName)) return false;
        if (!bDate.equals(that.bDate)) return false;
        if (!cityCode.equals(that.cityCode)) return false;
        if (!streetAddress.equals(that.streetAddress)) return false;
        if (!eMail.equals(that.eMail)) return false;
        return passHash.equals(that.passHash);
    }
}
