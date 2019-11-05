package UseCase;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Single Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.Exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing the functional requirements by testing
 * the use cases of a organizer.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestingAdminUseCases {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private static final ProtoUser PROTO_USER = ProtoUser.ADMIN;
    private static final Arrangement arrangement = new Arrangement(
            "Bernts Fantastiske Test",
            "Annet",
            420,
            "BergOgDalBaneVegen 46",
            false,
            "2019-10-15",
            "2019-10-16",
            "Dette varer i hele 1 dager. Og, server null formål."
    );

    // --------------------------------------------------//
    //                3.Single Tests                     //
    // --------------------------------------------------//
    /**
     * @throws IllegalDataAccess IllegalDateAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(1)
    void addArrangement() throws IllegalDataAccess {
        Repository.addArrangement(arrangement, ProtoUser.ORGANIZER);
        assertTrue(Repository.getUserArrangements(ProtoUser.ORGANIZER).contains(arrangement));
        //needs a tests to ensure that no excessive information is given and no less as well
    }

    /**
     * @throws IllegalDataAccess IllegalDateAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(2)
    void deleteArrangement() throws IllegalDataAccess {
        Repository.deleteArrangement(arrangement, PROTO_USER);
        assertFalse(Repository.getArrangementsData().contains(arrangement));
        //need a test to ensure only this data is deleted
    }

    //Add new sports

    //Remove sports

    //Remove Users

    //Add users

    //Set PROTO_USER as organizer

    //Remove PROTO_USER as organizer
}
