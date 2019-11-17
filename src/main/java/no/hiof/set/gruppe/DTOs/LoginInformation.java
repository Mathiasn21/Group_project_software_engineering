package no.hiof.set.gruppe.DTOs;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Contracts
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.interfaces.ILoginInformation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class LoginInformation implements ILoginInformation {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private final String passHash;
    private final String userID;

    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
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
