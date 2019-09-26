package no.hiof.gruppefire.tests;

import static org.junit.jupiter.api.Assertions.*;

import no.hiof.gruppefire.data.DataHandler;
import no.hiof.gruppefire.model.Arrangement;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataHandlingTest {

    @Test
    public void DataRetrievalJsonTest() {
        DataHandler.readFromJSONFil("src/no/hiof/gruppefire/files/arrangements.json");
        ArrayList<Arrangement> actualArragementer = DataHandler.getArrangementer();
        ArrayList<Arrangement> expectedArrangementer = new ArrayList<>();
        expectedArrangementer.add(new Arrangement("Test Arrengement","Annet", 420,"Oppibakken 15",false,LocalDate.of(2019,9,2),LocalDate.of(2019,9,4)));

        if(expectedArrangementer.size() != actualArragementer.size())
            fail("Størrelsen på disse to er forskjellig");

        for(int i = 0; i < expectedArrangementer.size() && i < actualArragementer.size() ;i++) {
            Arrangement ex = expectedArrangementer.get(i);
            Arrangement ac = actualArragementer.get(i);
            if(     ex.getName().equals(ac.getName()) &&
                    ex.getSport().equals(ac.getSport()) &&
                    ex.getParticipants() == ac.getParticipants() &&
                    ex.getAdress().equals(ac.getAdress()) &&
                    ex.isGruppe() == ac.isGruppe() &&
                    ex.getStartDate().equals(ac.getStartDate()) &&
                    ex.getEndDate().equals(ac.getEndDate())) {

                System.out.println("It's the same");
                return;
            } else {
                fail();
            }
        }
        // Av en SYKT rar grunn så greier ikke assertSame eller AsserEquals å sammenligne disse to objektene selvom de er identiske. Må derfor lage en egen sammenligning
        //assertSame(expectedArrangementer.get(0),DataHandler.getArrangementer().get(0));
    }
}
