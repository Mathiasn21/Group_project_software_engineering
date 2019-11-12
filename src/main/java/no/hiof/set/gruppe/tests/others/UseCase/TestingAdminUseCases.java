package no.hiof.set.gruppe.tests.others.UseCase;

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
import no.hiof.set.gruppe.core.Repository;
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
class TestingAdminUseCases {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private static final ProtoUser PROTO_USER_ADMIN = ProtoUser.ADMIN;
    private static final ProtoUser PROTO_USER_ORGANIZER = ProtoUser.ORGANIZER;
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
    void addArrangement() throws IllegalDataAccess, DataFormatException {
        List<Arrangement> expectedArrangementList = Repository.queryAllUserRelatedArrangements(PROTO_USER_ORGANIZER);
        expectedArrangementList.add(arrangement);
        Repository.insertArrangement(arrangement, PROTO_USER_ORGANIZER);

        assertDataIntegrity(expectedArrangementList, Repository.queryAllUserRelatedArrangements(PROTO_USER_ORGANIZER));
    }

    private void assertDataIntegrity(List<Arrangement> expectedArrangementList, List<Arrangement> userArrangements) {
        assertTrue(userArrangements.containsAll(expectedArrangementList));
        assertEquals(expectedArrangementList.size(), userArrangements.size());
    }

    /**
     * @throws IllegalDataAccess IllegalDateAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(2)
    void deleteArrangement() throws IllegalDataAccess, DataFormatException {
        List<Arrangement> expectedArrangementList = Repository.queryAllUserRelatedArrangements(PROTO_USER_ORGANIZER);
        expectedArrangementList.remove(arrangement);

        Repository.deleteArrangement(arrangement, PROTO_USER_ADMIN);
        assertDataIntegrity(expectedArrangementList, Repository.queryAllArrangements());
    }

    //Add new sports

    //Remove sports

    //Remove Users

    //Add users

    //Set PROTO_USER as organizer

    //Remove PROTO_USER as organizer
}
