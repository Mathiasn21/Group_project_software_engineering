package no.hiof.set.gruppe.model.user;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class LoginInformation implements ILoginInformation{

    private final String passHash;
    private final String userID;

    @Contract(pure = true)
    public LoginInformation(@NotNull String userID, @NotNull String passHash) {
        this.userID = userID;
        this.passHash = passHash;
    }

    @Contract(pure = true)
    @Override
    @NotNull
    public final String getUserID() {
        return this.userID;
    }

    @Contract(pure = true)
    @Override
    @NotNull
    public final String getPassHash() {
        return this.passHash;
    }
}
