package no.hiof.set.gruppe.model;

public enum SportCategory {

    OTHER("Annet"),
    FOOTBALL("Fotball"),
    BASKETBALL("Basketball"),
    ATHLETICS("Friidrett"),
    BICYCLING("Sykkelritt"),
    SKIING("Skirenn");

    String category;
    int id;
    SportCategory(String category){
        this.category = category;
    }

    @Override
    public String toString(){
        return category;
    }
}
