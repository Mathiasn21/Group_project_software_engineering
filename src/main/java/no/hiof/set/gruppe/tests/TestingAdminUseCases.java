package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.Exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing the functional requirements by testing
 * the use cases of a organizer.
 */
class TestingAdminUseCases {
    private static final Arrangement arrangement = new Arrangement(
            "Bernts Fantastiske Test",
            "Annet",
            420,
            "BergOgDalBaneVegen 46",
            false,
            "2019-10-15",
            "2019-10-16",
            "Dette varer i hele 1 dager. Og, server null formål.");
    private static final User user = User.ADMIN;

    /**
     * @throws IllegalDataAccess IllegalDateAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(1)
    void addArrangement() throws IllegalDataAccess {
        DataHandler.addArrangement(arrangement, user);
        assertTrue(DataHandler.getUserArrangements(user).contains(arrangement));
    }

    /**
     * @throws IllegalDataAccess IllegalDateAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(2)
    void deleteArrangement() throws IllegalDataAccess {
        DataHandler.deleteArrangement(arrangement, User.ORGANIZER);
        assertFalse(DataHandler.getArrangementsData().contains(arrangement));
    }


    //Add new sports

    //Remove sports

    //Remove Users

    //Add users

    //Set user as organizer

    //Remove user as organizer
}
