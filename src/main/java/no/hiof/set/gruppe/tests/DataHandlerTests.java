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
    }


    @Test
    public void ReadFromJsonTest() {
        Collection<Arrangement> testArrangementerer = DataHandler.getArrangementsData();

        assertTrue("No objects were loaded. Either becuase the file is empty or because it didn't load properly",testArrangementerer.size() > 0);
    }

    @Test
    public void writeToJsonTest() {
    }
}
