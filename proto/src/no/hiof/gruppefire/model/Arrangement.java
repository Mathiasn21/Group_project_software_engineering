package no.hiof.gruppefire.model;

import java.time.LocalDate;

public class Arrangement {

    private String navn;
    private String idrett;
    private int antallPlasser;
    private LocalDate startDato;
    private LocalDate sluttDato;
    //hughgrahguareugarhigaruhhguari

    public Arrangement(){
    }

    public Arrangement(String navn, String idrett, int antallPlasser, LocalDate startDato, LocalDate sluttDato) {
        this.navn = navn;
        this.idrett = idrett;
        this.antallPlasser = antallPlasser;
        this.startDato = startDato;
        this.sluttDato = sluttDato;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getIdrett() {
        return idrett;
    }

    public void setIdrett(String idrett) {
        this.idrett = idrett;
    }

    public int getAntallPlasser() {
        return antallPlasser;
    }

    public void setAntallPlasser(int antallPlasser) {
        this.antallPlasser = antallPlasser;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    public LocalDate getSluttDato() {
        return sluttDato;
    }

    public void setSluttDato(LocalDate sluttDato) {
        this.sluttDato = sluttDato;
    }
}
