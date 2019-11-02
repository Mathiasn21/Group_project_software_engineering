package no.hiof.set.gruppe.model.constantInformation;

/*Guide
 * 1. Import Statements
 * 2. Constants
 * 3. Contracts
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import org.jetbrains.annotations.Contract;

/**
 * This enum represents the possible answer to the question:
 * Is this meant for groups?
 */
public enum GroupCategory {
    // --------------------------------------------------//
    //                2.Constants                        //
    // --------------------------------------------------//
    TRUE(true, "Lagkonkurranse"),
    FALSE(false, "Individuell konkurranse");

    public final boolean isGroup;
    public final String str;


    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
    /**
     * @param bool boolean
     * @param str String
     */
    @Contract(pure = true)
    GroupCategory(boolean bool, String str){
        isGroup = bool;
        this.str = str;
    }

    /**
     * @return String
     */
    @Contract(pure = true)
    @Override
    public String toString(){
        return str;
    }
}
