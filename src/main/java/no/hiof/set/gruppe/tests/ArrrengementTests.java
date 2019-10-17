package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArrrengementTests {

    @Test
    @Order(1)
    public void LeggeTilArrangementTest() {
        Collection<Arrangement> testArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        testArrangementer.add(new Arrangement("Vegars lefse løp","Annet",37,"Lefseveien 17c", false, "2019-10-11", "2019-10-12","Masse spising og løing med lefser"));

        new DataHandler().storeArrangementsData(testArrangementer);

        Collection<Arrangement> afterChangeArrangementer = new ArrayList<>(DataHandler.getArrangementsData());

        assertEquals(afterChangeArrangementer, testArrangementer);
    }

    @Test
    @Order(2)
    public void FjerneArrangementTest() {
        Collection<Arrangement> testArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        testArrangementer.remove(((ArrayList<Arrangement>) testArrangementer).get(0));

        new DataHandler().storeArrangementsData(testArrangementer);

        Collection<Arrangement> afterChangeArrangementer = new ArrayList<>(DataHandler.getArrangementsData());

        assertEquals(afterChangeArrangementer, testArrangementer);
    }

    @Test
    @Order(3)
    public void EndreArrangementTest() {
        Collection<Arrangement> testArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        Arrangement endreArrangement = ((ArrayList<Arrangement>) testArrangementer).get(0);
        testArrangementer.remove(endreArrangement);
        endreArrangement.setName("Øskars Diverse");
        testArrangementer.add(endreArrangement);

        new DataHandler().storeArrangementsData(testArrangementer);

        Collection<Arrangement> afterChangeArrangementer = new ArrayList<>(DataHandler.getArrangementsData());

        assertEquals(afterChangeArrangementer, testArrangementer);
    }

    @AfterAll
    public void Cleanup() {
        Collection<Arrangement> testArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        testArrangementer.add(new Arrangement("Børres Brokkoli mani","Annet",37,"Brokkolivegen 28", false, "2019-10-11", "2019-10-12","Masse spising og løing med brokkoli"));

        new DataHandler().storeArrangementsData(testArrangementer);
    }

}
