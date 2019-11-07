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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    private static List<Arrangement> userArrangements;
    private static List<Arrangement> notUserArrangements;
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
    void adduserToArrangement(){
        arrangementToTest = notUserArrangements.get(0);
        Repository.addUserToArrangement(arrangementToTest, PROTO_USER);
        assertTrue(Repository.getUserArrangements(PROTO_USER).contains(arrangementToTest));
    }

     /**
      * Testing if removal of PROTO_USER is possible.
      */
    @Test
    @Order(2)
    void removeUserFromArrangement(){
        Repository.deleteUserFromArrangement(arrangementToTest, PROTO_USER);
        assertFalse(Repository.getUserArrangements(PROTO_USER).contains(arrangementToTest));
    }
 }
