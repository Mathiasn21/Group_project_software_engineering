package no.hiof.set.gruppe.tests.others.UseCase;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Single Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the functional requirements by testing
 * the use cases of a organizer.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestingOrganizerUseCases {
    // --------------------------------------------------//
    //                2.Local Fields                      //
    // --------------------------------------------------//
    private static final Arrangement arrangement = new Arrangement(
            "Bernts Fantastiske Test",
            "Annet",
            420,
            "BergOgDalBaneVegen 46",
            false,
            "2019-10-15",
            "2019-10-16",
            "Dette varer i hele 1 dager. Og, server null formål.");
    private static final ProtoUser PROTO_USER = ProtoUser.ORGANIZER;

    // --------------------------------------------------//
    //                3.Unit Tests                       //
    // --------------------------------------------------//
    /**
     * @throws IllegalDataAccess IllegalDataAccess{@link IllegalDataAccess}
     */
    @Test
    @Order(1)
    void firstAddArrangement() throws IllegalDataAccess {
        List<Arrangement> expectedList = Repository.getUserArrangements(PROTO_USER);
        expectedList.add(arrangement);
        Repository.addArrangement(arrangement, PROTO_USER);

        assertDataIntegrity(expectedList, Repository.getUserArrangements(PROTO_USER));
    }

    /**
     * @throws IllegalDataAccess IllegalDataAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(2)
    void thenDeleteArrangement() throws IllegalDataAccess {
        List<Arrangement> expectedList = Repository.getUserArrangements(PROTO_USER);
        expectedList.remove(arrangement);
        Repository.deleteArrangement(arrangement, PROTO_USER);

        assertDataIntegrity(expectedList, Repository.getUserArrangements(PROTO_USER));
    }


    private void assertDataIntegrity(List<Arrangement> expectedArrangementList, List<Arrangement> userArrangements) {
        assertTrue(userArrangements.containsAll(expectedArrangementList));
        assertEquals(expectedArrangementList.size(), userArrangements.size());
    }

    //Send out push notifications
}