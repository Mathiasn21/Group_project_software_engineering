package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import static org.junit.Assert.*;





public class DataHandlerTests {

    @AfterAll
    public static void CleanUp() {
        ArrayList<Arrangement> cleanUpArrangementer = new ArrayList<>();
        cleanUpArrangementer.add(new Arrangement("Børres langrenn","Skirenn",40,"Walkersgate 8",false, "2019-10-11","2019-10-12","Sumthin"));
        cleanUpArrangementer.add(new Arrangement("Oskars diverse","Annet",72,"Hakkebakkeskogen 73",false, "2019-11-11","2019-11-12","Sumthin else"));
        new DataHandler().storeArrangementsData(cleanUpArrangementer, User.USER);
    }


    @Test
    public void ReadFromJsonTest() {
        Collection<Arrangement> testArrangementerer = DataHandler.getArrangementsData();

        assertTrue("No objects were loaded. Either becuase the file is empty or because it didn't load properly",testArrangementerer.size() > 0);
    }

    @Test
    public void writeToJsonTest() {
        ArrayList<Arrangement> beforeSaveArrangementer = new ArrayList<>(DataHandler.getArrangementsData());
        beforeSaveArrangementer.add(new Arrangement("Børres langrenn","Skirenn",40,"Walkersgate 8",false, "2019-10-11","2019-10-12","Suuumthin"));

        new DataHandler().storeArrangementsData(beforeSaveArrangementer, User.USER);
        ArrayList<Arrangement> afterSaveArrangementer = new ArrayList<>(DataHandler.getArrangementsData());

        assertTrue("These two arrays are different",beforeSaveArrangementer.equals(afterSaveArrangementer));
    }
}
