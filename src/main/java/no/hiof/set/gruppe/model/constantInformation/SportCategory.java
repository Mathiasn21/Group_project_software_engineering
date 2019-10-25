package no.hiof.set.gruppe.model.constantInformation;

/**
 * This enum represents the different sports categories
 */
public enum SportCategory {

    ALL("Alle"),
    OTHER("Annet"),
    BICYCLING("Sykkelritt"),
    SKIING("Skirenn"),
    MARATHON("Maraton"),
    ;

    String category;
    SportCategory(String category){
        this.category = category;
    }

    @Override
    public String toString(){
        return category;
    }
}
