package no.hiof.set.gruppe.model.constantInformation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    @Contract(pure = true)
    SportCategory(@NotNull String category){
        this.category = category;
    }

    @Contract(pure = true)
    @Override
    public String toString(){
        return category;
    }
}
