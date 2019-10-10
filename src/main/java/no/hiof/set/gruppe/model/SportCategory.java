package no.hiof.set.gruppe.model;

/**
 * This enum represents the different sports categories
 */
public enum SportCategory {

    OTHER("Annet"),
    BICYCLING("Sykkelritt"),
    SKIING("Skirenn"),
    MARATHON("Maraton"),
    ;

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
