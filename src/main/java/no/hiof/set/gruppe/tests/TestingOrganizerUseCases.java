package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.Exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestingOrganizerUseCases {
    private static final Arrangement arrangement = new Arrangement(
            "Bernts Fantastiske Test",
            "Annet",
            420,
            "BergOgDalBaneVegen 46",
            false,
            "2019-10-15",
            "2019-10-16",
            "Dette varer i hele 1 dager. Og, server null formål.");

    private static final User user = User.ORGANIZER;

    //Add arrangement
    @Test
    @Order(1)
    void addArrangement() throws IllegalDataAccess {
        DataHandler.addArrangement(arrangement, user);
        assertTrue(DataHandler.getUserArrangements(user).contains(arrangement));
    }

    //remove arrangement
    @Test
    @Order(2)
    void deleteArrangement() throws IllegalDataAccess {
        DataHandler.deleteArrangement(arrangement, User.ORGANIZER);
        assertFalse(DataHandler.getArrangementsData().contains(arrangement));
    }

    //Send out push notifications
    //test returning arrangements only belonging to this user
}
