package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.Exceptions.InvalidLoginInformation;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.user.ILoginInformation;
import no.hiof.set.gruppe.model.user.LoginInformation;
import no.hiof.set.gruppe.model.user.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * This class is meant for testing validation and security around data access
 * as well as login access.
 */
class TestAccessValidation {

    /**
     * Generates illegal {@link LoginInformation}
     * @return {@link Stream}
     */
    @NotNull
    @Contract(pure = true)
    private static Stream<Arguments> GenIllegalLoginInformation() {
        return Stream.of(
                arguments(new LoginInformation("test", "soVerySafePass")),
                arguments(new LoginInformation("\00", "\00"))
        );
    }

    @Test
    void userloginSuccess(ILoginInformation loginInformation) throws InvalidLoginInformation {
        User userDetails = Repository.getUserDetails(loginInformation);
        assertTrue(userEqualsLoginInformation(userDetails, loginInformation));
    }

    @Test
    void userloginFailed(ILoginInformation loginInformation){
        assertThrows(InvalidLoginInformation.class, () -> Repository.getUserDetails(loginInformation));
    }

    @Test
    void userRegister(){

    }

    private boolean userEqualsLoginInformation(@NotNull User userDetails, @NotNull ILoginInformation loginInformation) {
        return userDetails.getName().equals(loginInformation.getUserID()) && userDetails.getPass().equals(loginInformation.getPassHash());
    }
}
