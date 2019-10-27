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
import java.util.UUID;

/**
 * Arrangement holds information about a possible arrangement.
 * @author Gruppe4
 */
public class Arrangement implements IArrangementSortering{


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
    public LocalDate getStartDate() {
        return LocalDate.parse(startDate);
    }
    public LocalDate getEndDate() {
        return LocalDate.parse(endDate);
    }
    public String getAddress() {
        return address;
    }
    public String getID(){return ID;}
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
    public void setAddress(String address) {
        this.address = address;
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
                address.equals(that.address) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                description.equals(that.getDescription());
    }

    @Override
    public String toString(){
        return name + ", " + sport + ", " + startDate + " til " + endDate;
    }

    @Override
    public int compareName(Arrangement comparingArr, boolean Ascending) {
        int result;
        result = this.getName().compareTo(comparingArr.getName());

        return Ascending ? result : result * -1;
    }

    @Override
    public int compareDate(Arrangement comparingArr, boolean Ascending) {
        int result;

        if(this.getStartDate().isBefore(comparingArr.getStartDate()))
            result = 1;
        else if(this.getStartDate().isAfter(comparingArr.getStartDate()))
            result = -1;
        else
            result = 0;

        if(!Ascending)
            result *= -1;

        return result;
    }

    @Override
    public int compareParticipants(Arrangement comparingArr, boolean Ascending) {
        int result;
        result = this.getParticipants() - comparingArr.getParticipants();

        if(!Ascending)
            result *= -1;

        return result;
    }

    @Override
    public int compareSport(Arrangement comparingArr, boolean Ascending) {
        int result;
        result = this.getSport().compareTo(comparingArr.getSport());

        if(!Ascending)
            result *= -1;

        return result;
    }
}


