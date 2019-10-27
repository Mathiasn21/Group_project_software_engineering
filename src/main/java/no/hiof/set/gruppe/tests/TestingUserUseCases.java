package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testing the functional requirements by testing
 * the use cases of a user.
 */
 @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestingUserUseCases {
    private static final User user = User.USER;
    private static List<Arrangement> userArrangements;
    private static List<Arrangement> notUserArrangements;
    private static Arrangement arrangementToTest;

    //setup
    static {
        userArrangements = DataHandler.getUserArrangements(user);
        notUserArrangements = DataHandler.getArrangementsData();
        notUserArrangements.removeAll(userArrangements);
    }

     /**
      * Testing if adding a new arrangement is possible.
      */
    @Test
    @Order(1)
    void adduserToArrangement(){
        arrangementToTest = notUserArrangements.get(0);
        DataHandler.addUserToArrangement(arrangementToTest, user);
        assertTrue(DataHandler.getUserArrangements(user).contains(arrangementToTest));
    }

     /**
      * Testing if removal of user is possible.
      */
    @Test
    @Order(2)
    void removeUserFromArrangement(){
        DataHandler.deleteUserFromArrangement(arrangementToTest, user);
        assertFalse(DataHandler.getUserArrangements(user).contains(arrangementToTest));
    }
 }
