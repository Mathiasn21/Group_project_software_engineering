package no.hiof.set.gruppe.model;
/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Constructors
 * 4. Public Getter Methods
 * 5. Public Setter Methods
 * 6. Overridden Methods
 * 7. Overridden Contracts
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Arrangement holds information about a possible arrangement.
 * @author Gruppe4
 */
public class Arrangement implements IGetAllData, IGetAllDataRaw{


    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private final String ID;
    private String name, sport, address, startDate, endDate, description;
    private int participants;
    private boolean group;

    // --------------------------------------------------//
    //                3.Constructors                     //
    // --------------------------------------------------//
    public Arrangement(){
        this("", "", 0, "", false);
    }

    /**
     * @param name String
     * @param sport String
     * @param participants int
     * @param address String
     * @param gruppe boolean
     */
    public Arrangement(String name, String sport, int participants, String address, boolean gruppe){
        this(name, sport, participants, address, gruppe, null, null, "");
    }

    /**
     * @param name String
     * @param sport String
     * @param participants int
     * @param address String
     * @param gruppe boolean
     * @param startDate String
     * @param endDate String
     * @param description String
     */
    public Arrangement(String name, String sport, int participants, String address, boolean gruppe, String startDate, String endDate, String description) {
        this.name = name;
        this.sport = sport;
        this.participants = participants;
        this.address = address;
        this.group = gruppe;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        ID = UUID.randomUUID().toString();
    }

    // --------------------------------------------------//
    //                4.Public Getter Methods            //
    // --------------------------------------------------//
    public String getName() {
        return name;
    }
    public String getSport() {
        return sport;
    }
    public int getParticipants() {
        return participants;
    }
    public LocalDate getStartDate() {return LocalDate.parse(startDate);}
    public LocalDate getEndDate() {return LocalDate.parse(endDate);}
    public String getAddress() {
        return address;
    }
    public String getID(){return ID;}
    public String getDescription() {return description;}
    /**
     * @return boolean
     */
    public boolean isGroup() {
        return group;
    }
    public String getGroup(){return group ? "Lagkonkurranse" : "Individuell konkurranse";}
    public String getDateIntervall() {return startDate + " til " + endDate;}
    // --------------------------------------------------//
    //                5.Public Setter Methods            //
    // --------------------------------------------------//
    /**
     * @param name String
     */
    public void setName(String name) {this.name = name;}

    /**
     * @param gruppe String
     */
    public void setGruppe(boolean gruppe) {
        this.group = gruppe;
    }

    /**
     * @param participants String
     */
    public void setParticipants(int participants) {
        this.participants = participants;
    }

    /**
     * @param sport String
     */
    public void setSport(String sport) {
        this.sport = sport;
    }

    /**
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param startDate String
     */
    public void setStartDate(String startDate) {this.startDate = startDate;}

    /**
     * @param endDate String
     */
    public void setEndDate(String endDate) {this.endDate = endDate;}

    /**
     * @param description String
     */
    public void setDescription(String description) {this.description = description;}

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//
    /**
     * @return String
     */
    @Override
    public String toString(){
        return name + ", " + sport + ", " + startDate + " til " + endDate;
    }

    /**
     * @return String[]
     */
    @Override
    public String[] getAllDataAsStringArr() {
        return new String[]{name, sport, address, startDate + " til " + endDate, String.valueOf(participants), description};
    }

    // --------------------------------------------------//
    //                7.Overridden Contracts            //
    // --------------------------------------------------//
    /**
     * @param o {@link Object}
     * @return boolean
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrangement that = (Arrangement) o;
        return participants == that.participants &&
                this.group == that.group &&
                this.name.equals(that.name) &&
                this.sport.equals(that.sport) &&
                this.address.equals(that.address) &&
                this.startDate.equals(that.startDate) &&
                this.endDate.equals(that.endDate) &&
                this.description.equals(that.getDescription());
    }

    /**
     * Returns all raw data in this sequence:
     * name, address, participants, description
     * @return String[]
     */
    @Override
    public String[] getAllStringDataArrRaw() {
        return new String[]{name, address, String.valueOf(participants), description};
    }
}