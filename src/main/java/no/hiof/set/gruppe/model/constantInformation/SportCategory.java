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
    final String category;

    /**
     * @param category String
     */
    @Contract(pure = true)
    SportCategory(@NotNull String category){
        this.category = category;
    }

    /**
     * @return String
     */
    @Contract(pure = true)
    @Override
    public String toString(){
        return category;
    }
}
