package no.hiof.set.gruppe.model;
/*Guide
 * 1. Import Statements
 * 2. Constructors
 * 3. Getters
 * 4. Setters
 * 5.Public Methods
 * 6. Overridden Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import java.time.LocalDate;
import java.util.Objects;

/**
 * Arrangement holds information about a possible arrangement.
 * @author Gruppe4
 */
public class Arrangement {


    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private final int ID;
    private String name, sport, adress, startDate, endDate, description;
    private int participants;
    private boolean group;

    // --------------------------------------------------//
    //                2.Constructors                     //
    // --------------------------------------------------//
    public Arrangement(){
        this("", "", 0, "", false);
    }
    public Arrangement(String name, String sport, int participants, String adress, boolean gruppe){
        this(name, sport, participants, adress, gruppe, null, null, "");
    }

    /**
     * @param name String
     * @param sport String
     * @param participants int
     * @param adress String
     * @param gruppe boolean
     * @param startDate String
     * @param endDate String
     */
    public Arrangement(String name, String sport, int participants, String adress, boolean gruppe, String startDate, String endDate, String description) {
        this.name = name;
        this.sport = sport;
        this.participants = participants;
        this.adress = adress;
        this.group = gruppe;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        ID = hashCode();
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
    public LocalDate getStartDate() {
        return LocalDate.parse(startDate);
    }
    public LocalDate getEndDate() {
        return LocalDate.parse(endDate);
    }
    public String getAdress() {
        return adress;
    }
    public int getID(){return ID;}
    public String getDescription() {return description;}

    // --------------------------------------------------//
    //                4.Setters                          //
    // --------------------------------------------------//
    public void setName(String name) {this.name = name;}
    public void setGruppe(boolean gruppe) {
        this.group = gruppe;
    }
    public void setParticipants(int participants) {
        this.participants = participants;
    }
    public void setSport(String sport) {
        this.sport = sport;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public void setStartDate(String startDate) {this.startDate = startDate;}
    public void setEndDate(String endDate) {this.endDate = endDate;}
    public void setDescription(String description) {this.description = description;}


    // --------------------------------------------------//
    //                5.Public Methods                   //
    // --------------------------------------------------//
    public boolean isGroup() {
        return group;
    }


    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrangement that = (Arrangement) o;
        return participants == that.participants &&
                group == that.group &&
                name.equals(that.name) &&
                sport.equals(that.sport) &&
                adress.equals(that.adress) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sport, adress, startDate, endDate, participants, group);
    }

    @Override
    public String toString(){
        return name + " " + sport + " " + participants + " " + adress + " " + startDate + " til " + endDate + " " + description;
    }
}


