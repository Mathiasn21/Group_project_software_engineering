package no.hiof.set.gruppe.model;

import java.time.LocalDate;

/**
 * Arrangement is a class that represent an arrangement.
 * Holds information about name, sport, participants, adress, group, startDate and endDate.
 *
 * @author Gruppe4
 */
public class Arrangement {

    private String name;
    private String sport;
    private String adress;

    public void setName(String name) {
        this.name = name;
    }

    private int participants;
    private boolean group;

    private String startDate;
    private String endDate;


    public Arrangement(){
        this("", "", 0, "", false);
    }

    public Arrangement(String name, String sport, int participants, String adress, boolean gruppe){
        this(name, sport, participants, adress, gruppe, null, null);
    }

    /**
     * Constructor used to make an Arrangement instance.
     * @param name String
     * @param sport String
     * @param participants int
     * @param adress String
     * @param gruppe boolean
     * @param startDate String
     * @param endDate String
     */
    public Arrangement(String name, String sport, int participants, String adress, boolean gruppe, String startDate, String endDate) {
        this.name = name;
        this.sport = sport;
        this.participants = participants;
        this.adress = adress;
        this.group = gruppe;
        this.startDate = startDate;
        this.endDate = endDate;
    }


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

    public boolean isGruppe() {
        return group;
    }

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

    public void setGroup(boolean group) {
        this.group = group;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }




    @Override
    public String toString(){
        return name + " " + sport + " " + participants + " " + adress + " " + startDate + " til " + endDate;
    }
}


