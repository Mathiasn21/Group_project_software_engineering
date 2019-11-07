package no.hiof.set.gruppe.Exceptions;

import no.hiof.set.gruppe.model.ValidationResult;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class UnableToRegisterUser extends Error {
    private final ValidationResult result;

    public UnableToRegisterUser(@NotNull ValidationResult result) {
        super("Unable to register new user.\n" + result.RESULT);
        this.result = result;
    }

    @Contract(pure = true)
    public ValidationResult getResult() {return result;}
}
