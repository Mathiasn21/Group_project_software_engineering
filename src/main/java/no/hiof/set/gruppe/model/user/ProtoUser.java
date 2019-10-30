package no.hiof.set.gruppe.model.user;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * An enum representing legal users. Just a dummy class
 */
public enum ProtoUser {
    ADMIN("Admin", "Password1", "Admin.fxml"),
    USER("ProtoUser", "Password2", "User2.fxml"),
    ORGANIZER("Organizer", "Password3", "Organizer.fxml");

    String user;
    String password;
    String viewName;

    /**
     * @param user String
     * @param password String
     * @param viewName String
     */
    @Contract(pure = true)
    ProtoUser(String user, String password, String viewName){
        this.user = user;
        this.password = password;
        this.viewName = viewName;
    }

    /**
     * @return String
     */
    @Contract(pure = true)
    public String getName() {return user;}

    /**
     * @return String
     */
    @Contract(pure = true)
    public String getPass() {return password;}

    /**
     * @return String
     */
    @Contract(pure = true)
    public String getViewName() {return viewName;}

    /**
     * @param userName String
     * @return {@link ProtoUser}
     */
    @Nullable
    public static ProtoUser getUser(String userName){
        for(ProtoUser u: values()) if(u.user.equals(userName))return u;
        return null;
    }
}
