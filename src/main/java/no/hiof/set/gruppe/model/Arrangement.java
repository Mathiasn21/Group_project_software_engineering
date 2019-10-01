package no.hiof.gruppefire.model;

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
    private int participants;
    private String adress;
    private boolean group;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Empty constructor.
     */
    public Arrangement(){
    }

    public Arrangement(String name, String sport, int participants, String adress, boolean gruppe){
        this(name, sport, participants, adress, gruppe, null, null);
    }

    /**
     * Constructor used to make an Arrangement instance.
     * @param name
     * @param sport
     * @param participants
     * @param adress
     * @param gruppe
     * @param startDate
     * @param endDate
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

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public boolean isGruppe() {
        return group;
    }

    public void setGruppe(boolean gruppe) {
        this.group = gruppe;
    }

    @Override
    public String toString(){
        return name + " " + sport + " " + participants + " " + adress + " " + startDate + " til " + endDate;
    }
}


