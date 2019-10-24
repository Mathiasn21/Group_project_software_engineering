package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArrrengementTests {

    @Test
    @Order(1)
    public void LeggeTilArrangementTest() {
        List<Arrangement> testArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        Arrangement arr = new Arrangement("Vegars lefse løp","Annet",37,"Lefseveien 17c", false, "2019-10-11", "2019-10-12","Masse spising og løing med lefser");
        testArrangementer.add(arr);

        DataHandler.addArrangement(arr, User.USER);
        List<Arrangement> afterChangeArrangementer = new ArrayList<>(DataHandler.getArrangementsData());

        assertEquals(afterChangeArrangementer, testArrangementer);
    }

    @Test
    @Order(2)
    public void FjerneArrangementTest() {
        List<Arrangement> testArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        Arrangement arrangement = (testArrangementer).get(0);
        testArrangementer.remove(arrangement);
        DataHandler.deleteArrangement(arrangement);

        List<Arrangement> afterChangeArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        assertEquals(afterChangeArrangementer, testArrangementer);
    }

    @Test
    @Order(3)
    public void EndreArrangementTest() {
        List<Arrangement> testArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        Arrangement endreArrangement = testArrangementer.get(0);
        endreArrangement.setName("Øskars Diverse");

        assertEquals("Øskars Diverse", testArrangementer.get(0).getName());
    }

    @AfterAll
    public void Cleanup() {
        List<Arrangement> testArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        Arrangement arrangement = new Arrangement("Børres Brokkoli mani","Annet",37,"Brokkolivegen 28", false, "2019-10-11", "2019-10-12","Masse spising og løing med brokkoli");
        testArrangementer.add(arrangement);

        DataHandler.addArrangement(arrangement, User.USER);
    }
}
