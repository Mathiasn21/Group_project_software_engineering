package no.hiof.set.gruppe.model.constantInformation;

import no.hiof.set.gruppe.util.Validation;
import org.jetbrains.annotations.Contract;

/**
 * A class representing the result from a validation{@link Validation} attempt.
 */
public class ValidationResult {
    public final String RESULT;
    public final boolean IS_VALID;

    @Contract(pure = true)
    public ValidationResult(String result, boolean isValid){
        RESULT = result;
        IS_VALID = isValid;
    }
}
