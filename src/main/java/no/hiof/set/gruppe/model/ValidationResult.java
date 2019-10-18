package no.hiof.set.gruppe.model;

/**
 * A class representing the result from a validation{@link no.hiof.set.gruppe.data.Validation} attempt.
 */
public class ValidationResult {
    public final String RESULT;
    public final boolean IS_VALID;

    public ValidationResult(String result, boolean isValid){
        RESULT = result;
        IS_VALID = isValid;
    }
}
