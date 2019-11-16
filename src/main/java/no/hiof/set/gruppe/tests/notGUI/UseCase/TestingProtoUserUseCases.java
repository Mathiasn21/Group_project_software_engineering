package no.hiof.set.gruppe.tests.notGUI.UseCase;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Single Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.repository.IRepository;
import no.hiof.set.gruppe.core.repository.Repository;
import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing the functional requirements by testing
 * the use cases of a PROTO_USER.
 */
 @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestingProtoUserUseCases {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private static final ProtoUser PROTO_USER = ProtoUser.USER;
    private static final List<Arrangement> userArrangements;
    private static final List<Arrangement> notUserArrangements;
    private static Arrangement arrangementToTest;
    private static final IRepository repository = new Repository();


    //setup
    static {
        userArrangements = repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER);
        notUserArrangements = repository.queryAllDataOfGivenType(Arrangement.class);
        notUserArrangements.removeAll(userArrangements);
    }

    // --------------------------------------------------//
    //                3.Single Tests                     //
    // --------------------------------------------------//
     /**
      * Testing if adding a new arrangement is possible.
      */
    @Test
    @Order(1)
    void firstAddUserToArrangement() throws DataFormatException {
        arrangementToTest = notUserArrangements.get(0);
        userArrangements.add(arrangementToTest);

        repository.insertUserRelationToData(arrangementToTest, PROTO_USER);
        assertDataIntegrity(repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER));
    }

     /**
      * Testing if removal of PROTO_USER is possible.
      */
    @Test
    @Order(2)
    void thenRemoveUserFromArrangement() throws DataFormatException {
        repository.deleteUserConnectionToData(arrangementToTest, PROTO_USER);
        userArrangements.remove(arrangementToTest);
        assertDataIntegrity(repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER));
    }

    private void assertDataIntegrity(List<Arrangement> expectedArrangementList) {
        assertTrue(userArrangements.containsAll(expectedArrangementList));
        assertEquals(expectedArrangementList.size(), userArrangements.size());
    }
 }
