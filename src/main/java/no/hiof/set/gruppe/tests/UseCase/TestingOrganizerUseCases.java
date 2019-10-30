package UseCase;

import no.hiof.set.gruppe.Exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing the functional requirements by testing
 * the use cases of a organizer.
 */
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
    private static final ProtoUser PROTO_USER = ProtoUser.ORGANIZER;
    private static final List<Arrangement> arrangementList = Repository.getUserArrangements(PROTO_USER);


    /**
     * @throws IllegalDataAccess IllegalDataAccess{@link IllegalDataAccess}
     */
    @Test
    @Order(1)
    void addArrangement() throws IllegalDataAccess {
        Repository.addArrangement(arrangement, PROTO_USER);
        List<Arrangement> newListOfArrangements = Repository.getUserArrangements(PROTO_USER);
        assertTrue(newListOfArrangements.contains(arrangement));
        assertTrue(newListOfArrangements.containsAll(arrangementList) && newListOfArrangements.size() == arrangementList.size() + 1);
    }

    /**
     * @throws IllegalDataAccess IllegalDataAccess {@link IllegalDataAccess}
     */
    @Test
    @Order(2)
    void deleteArrangement() throws IllegalDataAccess {
        Repository.deleteArrangement(arrangement, ProtoUser.ORGANIZER);
        assertFalse(Repository.getArrangementsData().contains(arrangement));
    }

    //Send out push notifications
    //test returning arrangements only belonging to this PROTO_USER
}