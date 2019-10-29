package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.Exceptions.InvalidLoginInformation;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.RegisterUser;
import no.hiof.set.gruppe.model.user.ILoginInformation;
import no.hiof.set.gruppe.model.user.LoginInformation;
import no.hiof.set.gruppe.model.user.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * This class is meant for testing validation and security around data access
 * as well as login access.
 */
class TestAccessValidation {

    @Test
    void userloginSuccess() throws InvalidLoginInformation {
        ILoginInformation loginInformation = new LoginInformation("User", "Password2");
        User userDetails = Repository.getUserDetails(loginInformation);
        assertTrue(userEqualsLoginInformation(userDetails, loginInformation));
    }

    @ParameterizedTest
    @MethodSource("GenIllegalLoginInformation")
    void userloginFailed(ILoginInformation loginInformation){
        assertThrows(InvalidLoginInformation.class, () -> Repository.getUserDetails(loginInformation));
    }

    @Test
    void userRegister(){
        Repository.addNewUser(new RegisterUser());
    }


    /**
     * Generates illegal {@link LoginInformation}
     * @return {@link Stream}
     */
    @NotNull
    @Contract(pure = true)
    private static Stream<Arguments> GenIllegalLoginInformation() {
        return Stream.of(
                arguments(new LoginInformation("test", "soVerySafePass")),
                arguments(new LoginInformation("\00", "\00")),
                arguments(new LoginInformation("User", "\00"))
        );
    }

    private boolean userEqualsLoginInformation(@NotNull User userDetails, @NotNull ILoginInformation loginInformation) {
        return userDetails.getName().equals(loginInformation.getUserID()) && userDetails.getPass().equals(loginInformation.getPassHash());
    }
}
