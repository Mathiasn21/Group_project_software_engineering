package no.hiof.set.gruppe.model;

/**
 * This enum represents the possible answer to the question:
 * Does this meant for groups?
 */
public enum GroupCategory {

    TRUE(true, "Gruppe"),
    FALSE(false, "Individuell");

    public final boolean isGroup;
    public final String str;

    GroupCategory(boolean bool, String str){
        isGroup = bool;
        this.str = str;
    }
    @Override
    public String toString(){
        return str;
    }
}
