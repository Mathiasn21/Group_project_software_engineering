package no.hiof.set.gruppe.tests.others.UseCase;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Single Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.data.Repository;
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

    //setup
    static {
        userArrangements = Repository.getUserArrangements(PROTO_USER);
        notUserArrangements = Repository.getArrangementsData();
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
    void firstAddUserToArrangement(){
        arrangementToTest = notUserArrangements.get(0);
        userArrangements.add(arrangementToTest);

        Repository.addUserToArrangement(arrangementToTest, PROTO_USER);
        assertDataIntegrity(Repository.getUserArrangements(PROTO_USER));
    }

     /**
      * Testing if removal of PROTO_USER is possible.
      */
    @Test
    @Order(2)
    void thenRemoveUserFromArrangement(){
        Repository.deleteUserFromArrangement(arrangementToTest, PROTO_USER);
        userArrangements.remove(arrangementToTest);
        Repository.deleteUserFromArrangement(arrangementToTest, PROTO_USER);
        assertDataIntegrity(Repository.getUserArrangements(PROTO_USER));
    }

    private void assertDataIntegrity(List<Arrangement> expectedArrangementList) {
        Assertions.assertTrue(TestingProtoUserUseCases.userArrangements.containsAll(expectedArrangementList));
        assertEquals(expectedArrangementList.size(), TestingProtoUserUseCases.userArrangements.size());
    }
 }
