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

import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.infrastructure.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.infrastructure.exceptions.InvalidLoginInformation;
import no.hiof.set.gruppe.core.infrastructure.factory.DataFactory;
import no.hiof.set.gruppe.core.infrastructure.factory.IFactory;
import no.hiof.set.gruppe.core.interfaces.ILoginInformation;
import no.hiof.set.gruppe.core.interfaces.IRepository;
import no.hiof.set.gruppe.core.infrastructure.repository.Repository;
import no.hiof.set.gruppe.DTOs.LoginInformation;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    void assert_Repository_UserLogin_IsSuccessful() {
        assertDoesNotThrow(() -> {
            for (ProtoUser user : ProtoUser.values()) {
                ILoginInformation loginInformation = new LoginInformation(user.getName(), user.getPass());
                ProtoUser protoUserDetails = repository.queryUserDetailsWith(loginInformation);
                assertTrue(userEqualsLoginInformation(protoUserDetails, loginInformation));
            }
        });
    }

    @Test
    void assert_Repository_deleteData_throws_IllegalDataAccess_WrongUser_access_WrongObject(){
        IFactory factory = new DataFactory();
        Arrangement arrangement = factory.generateType(Arrangement.class);
        assertThrows(IllegalDataAccess.class, () -> repository.insertData(arrangement, ProtoUser.USER));
        assertThrows(IllegalDataAccess.class, () -> repository.deleteData(arrangement, ProtoUser.USER));
    }

    // --------------------------------------------------//
    //                3.Parameterized Tests              //
    // --------------------------------------------------//
    @ParameterizedTest
    @MethodSource("GenIllegalLoginInformation")
    void assert_Repository_UserLogin_Failed(ILoginInformation loginInformation){
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
