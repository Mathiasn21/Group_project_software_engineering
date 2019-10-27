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

    //Adding user to data
    @Test
    @Order(1)
    void adduserToArrangement(){
        arrangementToTest = notUserArrangements.get(0);
        DataHandler.addUserToArrangement(arrangementToTest, user);
        assertTrue(DataHandler.getUserArrangements(user).contains(arrangementToTest));
    }

    //Remove user from data
    @Test
    @Order(2)
    void removeUserFromArrangement(){
        DataHandler.deleteUserFromArrangement(arrangementToTest, user);
        assertFalse(DataHandler.getUserArrangements(user).contains(arrangementToTest));
    }

    //sortData by definition
}
