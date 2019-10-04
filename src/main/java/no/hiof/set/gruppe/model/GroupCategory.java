package no.hiof.set.gruppe.model;

public enum GroupCategory {

    TRUE(true, "true"),
    FALSE(false, "false");

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
