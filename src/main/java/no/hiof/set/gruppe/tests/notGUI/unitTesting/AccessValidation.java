package no.hiof.set.gruppe.tests.notGUI.unitTesting;
/*Guide
 * 1. Import Statements
 * 2. Unit Tests
 * 3. Parameterized Tests
 * 4. Helper Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import no.hiof.set.gruppe.core.infrastructure.exceptions.InvalidLoginInformation;
import no.hiof.set.gruppe.core.infrastructure.exceptions.UnableToRegisterUser;
import no.hiof.set.gruppe.core.interfaces.ILoginInformation;
import no.hiof.set.gruppe.core.interfaces.IRepository;
import no.hiof.set.gruppe.core.infrastructure.repository.Repository;
import no.hiof.set.gruppe.DTOs.LoginInformation;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;
import no.hiof.set.gruppe.core.entities.user.RawUser;
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
class AccessValidation {
    private final IRepository repository = new Repository();

    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//
    @Test
    void userLoginSuccess() throws InvalidLoginInformation {
        for(ProtoUser user : ProtoUser.values()){
            ILoginInformation loginInformation = new LoginInformation(user.getName(), user.getPass());
            ProtoUser protoUserDetails = repository.queryUserDetailsWith(loginInformation);
            assertTrue(userEqualsLoginInformation(protoUserDetails, loginInformation));
        }
    }

    @Test
    void userRegister()throws UnableToRegisterUser {
        RawUser rawUser = new RawUser("Bernt", "Åge", "2007-12-03", "1771", "NerdStreet 22", "It_Burns@When_I.PI", "TheInternet22");
        repository.insertNewUser(rawUser);
    }

    // --------------------------------------------------//
    //                3.Parameterized Tests              //
    // --------------------------------------------------//
    @ParameterizedTest
    @MethodSource("GenIllegalLoginInformation")
    void userLoginFailed(ILoginInformation loginInformation){
        assertThrows(InvalidLoginInformation.class, () -> repository.queryUserDetailsWith(loginInformation));
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
        String userPass = ProtoUser.USER.getPass();
        String userName = ProtoUser.USER.getName();
        return Stream.of(
                arguments(new LoginInformation("\00", userPass)),
                arguments(new LoginInformation("\00s", userPass)),
                arguments(new LoginInformation("s\00", userPass)),
                arguments(new LoginInformation("\00ssss", userPass)),
                arguments(new LoginInformation("sssss\00", userPass)),
                arguments(new LoginInformation("test", userPass)),
                arguments(new LoginInformation(userName + "\00", userPass)),
                arguments(new LoginInformation( "\00" + userName, userPass)),
                arguments(new LoginInformation("\00", "\00")),
                arguments(new LoginInformation(userName, "\00sssss")),
                arguments(new LoginInformation(userName, "sssss\00")),
                arguments(new LoginInformation(userName, "\00s")),
                arguments(new LoginInformation(userName, "s\00")),
                arguments(new LoginInformation(userName, userPass + "\00")),
                arguments(new LoginInformation(userName, "\00" + userPass)),
                arguments(new LoginInformation(userName, "\00"))
        );
    }

    /**
     * @param protoUserDetails {@link ProtoUser}
     * @param loginInformation {@link ILoginInformation}
     * @return boolean
     */
    private boolean userEqualsLoginInformation(@NotNull ProtoUser protoUserDetails, @NotNull ILoginInformation loginInformation) {
        return protoUserDetails.getName().equals(loginInformation.getUserID()) && protoUserDetails.getPass().equals(loginInformation.getPassHash());
    }
}
