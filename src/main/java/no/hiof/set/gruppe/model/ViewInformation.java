package no.hiof.set.gruppe.model;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Constructors
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Information about the view. Mainly location and title
 */
public final class ViewInformation {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    public final String viewName;
    public final String viewTitle;

    // --------------------------------------------------//
    //                3.Constructors                     //
    // --------------------------------------------------//
    /**
     * @param viewName String
     * @param viewTitle String
     */
    @Contract(pure = true)
    public ViewInformation(@NotNull String viewName, @NotNull String viewTitle){
        this.viewName = viewName;
        this.viewTitle = viewTitle;
    }
}
