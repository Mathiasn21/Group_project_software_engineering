package no.hiof.set.gruppe.tests.unitTesting;
/*Guide
 * 1. Import Statements
 * 2. Unit Tests
 * 3. Parameterized Tests
 * 4. Helper Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.exceptions.InvalidLoginInformation;
import no.hiof.set.gruppe.exceptions.UnableToRegisterUser;
import no.hiof.set.gruppe.core.Repository;
import no.hiof.set.gruppe.model.user.ILoginInformation;
import no.hiof.set.gruppe.model.user.LoginInformation;
import no.hiof.set.gruppe.model.user.ProtoUser;
import no.hiof.set.gruppe.model.user.RawUser;
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

    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//
    @Test
    void userLoginSuccess() throws InvalidLoginInformation {
        ILoginInformation loginInformation = new LoginInformation("ProtoUser", "Password2");
        ProtoUser protoUserDetails = Repository.queryUserDetailsWith(loginInformation);
        assertTrue(userEqualsLoginInformation(protoUserDetails, loginInformation));
    }

    @Test
    void userRegister()throws UnableToRegisterUser {
        RawUser rawUser = new RawUser("Bernt", "Åge", "2007-12-03", "1771", "NerdStreet 22", "It_Burns@When_I.PI", "TheInternet22");
        Repository.insertNewUser(rawUser);
    }

    // --------------------------------------------------//
    //                3.Parameterized Tests              //
    // --------------------------------------------------//
    @ParameterizedTest
    @MethodSource("GenIllegalLoginInformation")
    void userLoginFailed(ILoginInformation loginInformation){
        assertThrows(InvalidLoginInformation.class, () -> Repository.queryUserDetailsWith(loginInformation));
    }

    // --------------------------------------------------//
    //                4.Helper Methods                   //
    // --------------------------------------------------//
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
                arguments(new LoginInformation("ProtoUser", "\00"))
        );
    }

    private boolean userEqualsLoginInformation(@NotNull ProtoUser protoUserDetails, @NotNull ILoginInformation loginInformation) {
        return protoUserDetails.getName().equals(loginInformation.getUserID()) && protoUserDetails.getPass().equals(loginInformation.getPassHash());
    }
}
