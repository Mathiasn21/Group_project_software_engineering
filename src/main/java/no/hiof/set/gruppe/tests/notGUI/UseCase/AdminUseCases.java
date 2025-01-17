package no.hiof.set.gruppe.tests.notGUI.UseCase;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Single Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import no.hiof.set.gruppe.core.infrastructure.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.infrastructure.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.infrastructure.factory.DataFactory;
import no.hiof.set.gruppe.core.interfaces.IRepository;
import no.hiof.set.gruppe.core.infrastructure.repository.Repository;
import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing the functional requirements by testing
 * the use cases of a organizer.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminUseCases {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private static final ProtoUser PROTO_USER_ADMIN = ProtoUser.ADMIN;
    private static final ProtoUser PROTO_USER_ORGANIZER = ProtoUser.ORGANIZER;
    private static final IRepository repository = new Repository();
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
    void mutate_arrangement_and_keepDataIntegrity() throws IllegalDataAccess, DataFormatException {
        Arrangement genArrangement = new DataFactory().generateType(Arrangement.class);

        List<Arrangement> expectedArrangementList = repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER_ORGANIZER);
        expectedArrangementList.add(genArrangement);
        repository.insertData(genArrangement, PROTO_USER_ORGANIZER);

        genArrangement.setName(arrangement.getName());
        genArrangement.setAddress(arrangement.getAddress());
        genArrangement.setSport(arrangement.getSport());
        
        repository.mutateData(genArrangement);
        assertDataIntegrity(expectedArrangementList, repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER_ORGANIZER));
    }

    /**
     * @throws IllegalDataAccess IllegalDateAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(2)
    void delete_arrangement_keepDataIntegrity() throws IllegalDataAccess, DataFormatException {
        List<Arrangement> expectedArrangementList = repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER_ORGANIZER);
        expectedArrangementList.remove(arrangement);

        repository.deleteData(arrangement, PROTO_USER_ADMIN);
        assertDataIntegrity(expectedArrangementList, repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER_ORGANIZER));
    }

    /**
     * @param expectedArrangementList {@link List}
     * @param userArrangements {@link List}
     */
    private void assertDataIntegrity(List<Arrangement> expectedArrangementList, List<Arrangement> userArrangements) {
        assertTrue(userArrangements.containsAll(expectedArrangementList));
        assertEquals(expectedArrangementList.size(), userArrangements.size());
    }
}