package no.hiof.set.gruppe.model;
/*Guide
 * 1. Import Statements
 * 2. Constructors
 * 3. Getters
 * 4. Setters
 * 5. Overridden Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import org.jetbrains.annotations.Contract;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Arrangement holds information about a possible arrangement.
 * @author Gruppe4
 */
public class Arrangement implements IGetAllData{


    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private final String ID;
    private String name, sport, address, startDate, endDate, description;
    private int participants;
    private boolean group;

    // --------------------------------------------------//
    //                2.Constructors                     //
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
    //                3.Getters                          //
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

    // --------------------------------------------------//
    //                4.Setters                          //
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
    //                5.Overridden Methods               //
    // --------------------------------------------------//
    /**
     * @param o {@link Object}
     * @return boolean
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrangement that = (Arrangement) o;
        return participants == that.participants &&
                group == that.group &&
                name.equals(that.name) &&
                sport.equals(that.sport) &&
                address.equals(that.address) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                description.equals(that.getDescription());
    }

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
}


