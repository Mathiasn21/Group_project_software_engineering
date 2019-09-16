package no.hiof.gruppefire.model;

public class Arrangement {

    private String navn;
    private String idrett;
    private int antallPlasser;

    public Arrangement(){
    }

    public Arrangement(String navn, int antallPlasser, String idrett){
        this.navn = navn;
        this.idrett = idrett;
        this.antallPlasser = antallPlasser;
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
}
