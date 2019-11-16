package no.hiof.set.gruppe.model.user;

/*Guide
 * 1. Import Statements
 * 2. Constants
 * 3. Contracts
 * 4. Public getter methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * An enum representing legal users. Just a dummy class
 */
public enum ProtoUser implements IUser{
    // --------------------------------------------------//
    //                2.Constants                        //
    // --------------------------------------------------//
    ADMIN("Admin", "Password1", "Admin.fxml"),
    USER("ProtoUser", "Password2", "User.fxml"),
    ORGANIZER("Organizer", "Password3", "Organizer.fxml");

    private final String user;
    private final String password;
    private final String viewName;


    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
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
    public String getPass() {return password;}

    /**
     * @return String
     */
    @Contract(pure = true)
    public String getViewName() {return viewName;}


    /**
     * @return String
     */
    @Contract(pure = true)
    @Override
    public String getName() {return user;}
    // --------------------------------------------------//
    //                4.Public getter methods            //
    // --------------------------------------------------//

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
