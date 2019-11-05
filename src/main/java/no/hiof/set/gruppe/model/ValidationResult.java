package no.hiof.set.gruppe.model;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Constructors
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.util.Validation;
import org.jetbrains.annotations.Contract;

/**
 * A class representing the result from a validation{@link Validation} attempt.
 */
public class ValidationResult {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    public final String RESULT;
    public final boolean IS_VALID;

    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
    /**
     * @param result String
     * @param isValid String
     */
    @Contract(pure = true)
    public ValidationResult(String result, boolean isValid){
        RESULT = result;
        IS_VALID = isValid;
    }
}
