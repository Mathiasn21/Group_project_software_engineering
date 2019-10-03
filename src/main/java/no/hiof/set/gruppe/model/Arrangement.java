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

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    private int participants;
    private boolean group;

    private LocalDate startDate;
    private LocalDate endDate;


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
     * @param startDate LocalDate
     * @param endDate LocalDate
     */
    public Arrangement(String name, String sport, int participants, String adress, boolean gruppe, LocalDate startDate, LocalDate endDate) {
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
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
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

    @Override
    public String toString(){
        return name + " " + sport + " " + participants + " " + adress + " " + startDate + " til " + endDate;
    }
}


