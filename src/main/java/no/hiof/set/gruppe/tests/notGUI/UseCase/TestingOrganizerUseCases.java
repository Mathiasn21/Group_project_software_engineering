package no.hiof.set.gruppe.tests.notGUI.UseCase;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Single Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.repository.IRepository;
import no.hiof.set.gruppe.core.repository.Repository;
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
    private static final IRepository repository = new Repository();

    // --------------------------------------------------//
    //                3.Unit Tests                       //
    // --------------------------------------------------//
    /**
     * @throws IllegalDataAccess IllegalDataAccess{@link IllegalDataAccess}
     */
    @Test
    @Order(1)
    void firstAddArrangement() throws IllegalDataAccess, DataFormatException {
        List<Arrangement> expectedList = repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER);
        expectedList.add(arrangement);
        repository.insertData(arrangement, PROTO_USER);

        assertDataIntegrity(expectedList, repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER));
    }

    /**
     * @throws IllegalDataAccess IllegalDataAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(2)
    void thenDeleteArrangement() throws IllegalDataAccess, DataFormatException {
        List<Arrangement> expectedList = repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER);
        expectedList.remove(arrangement);
        repository.deleteData(arrangement, PROTO_USER);

        assertDataIntegrity(expectedList, repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER));
    }

    /**
     * @param expectedArrangementList
     * @param userArrangements
     */
    private void assertDataIntegrity(List<Arrangement> expectedArrangementList, List<Arrangement> userArrangements) {
        assertTrue(userArrangements.containsAll(expectedArrangementList));
        assertEquals(expectedArrangementList.size(), userArrangements.size());
    }

    //Send out push notifications
}