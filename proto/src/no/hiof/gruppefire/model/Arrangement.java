package no.hiof.gruppefire.model;

import javafx.application.Application;

import java.time.LocalDate;

public class Arrangement {

    private String name;
    private String sport;
    private int participants;
    private String adress;
    private boolean gruppe;
    private LocalDate startDate;
    private LocalDate endDate;

    public Arrangement(){
    }

    public Arrangement(String name, String sport, int participants, String adress, boolean gruppe){
        this(name, sport, participants, adress, gruppe, null, null);
    }

    public Arrangement(String name, String sport, int participants, String adress, boolean gruppe, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.sport = sport;
        this.participants = participants;
        this.adress = adress;
        this.gruppe = gruppe;
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
        return gruppe;
    }

    public void setGruppe(boolean gruppe) {
        this.gruppe = gruppe;
    }

    @Override
    public String toString(){
        return name + " " + sport + " " + participants + " " + adress + " " + startDate + " " + endDate;
    }
}


