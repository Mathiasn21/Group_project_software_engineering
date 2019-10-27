package no.hiof.set.gruppe.model.constantInformation;

import org.jetbrains.annotations.Contract;

/**
 * This enum represents the possible answer to the question:
 * Is this meant for groups?
 */
public enum GroupCategory {

    TRUE(true, "Lagkonkurranse"),
    FALSE(false, "Individuell konkurranse");

    public final boolean isGroup;
    public final String str;

    @Contract(pure = true)
    GroupCategory(boolean bool, String str){
        isGroup = bool;
        this.str = str;
    }
    @Contract(pure = true)
    @Override
    public String toString(){
        return str;
    }
}
