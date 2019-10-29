package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.Exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing the functional requirements by testing
 * the use cases of a organizer.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    private static final ProtoUser PROTO_USER = ProtoUser.ADMIN;

    /**
     * @throws IllegalDataAccess IllegalDateAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(1)
    void addArrangement() throws IllegalDataAccess {
        Repository.addArrangement(arrangement, ProtoUser.ORGANIZER);
        assertTrue(Repository.getUserArrangements(ProtoUser.ORGANIZER).contains(arrangement));
    }

    /**
     * @throws IllegalDataAccess IllegalDateAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(2)
    void deleteArrangement() throws IllegalDataAccess {
        Repository.deleteArrangement(arrangement, PROTO_USER);
        assertFalse(Repository.getArrangementsData().contains(arrangement));
    }


    //Add new sports

    //Remove sports

    //Remove Users

    //Add users

    //Set PROTO_USER as organizer

    //Remove PROTO_USER as organizer
}
