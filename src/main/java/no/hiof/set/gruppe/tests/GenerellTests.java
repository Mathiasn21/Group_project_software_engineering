package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.data.InputValidation;
import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GenerellTests {

    private ArrayList<Arrangement> expectedArrangementer = new ArrayList<>();
    private Arrangement addRemoveTest = new Arrangement("Test Arrengement3","Basketball", 69,"Hakkebakken 420",false,"2019-10-02)","2019-10-04");

    @BeforeEach
    public void setup() {
        expectedArrangementer.clear();
        expectedArrangementer.add(new Arrangement("Test Arrengement","Annet", 420,"Oppibakken 15",false,"2019-10-02)","2019-10-04"));
        expectedArrangementer.add(new Arrangement("Test Arrengement2","Basketball", 69,"Hakkebakken 420",false,"2019-10-02)","2019-10-04"));
    }

    @AfterAll
    public void end() {
        //DataHandler.addArrangementer(new Arrangement("Test Arrengement","Annet", 420,"Oppibakken 15",false,"2019-09-02","2019-09-04"));
        //DataHandler.addArrangementer(new Arrangement("Test Arrengement2","Basketball", 69,"Hakkebakken 420",false,"2019-10-02)","2019-10-04"));
    }

    @Test
    @Order(1)
    public void inputValidationTest() {
        //assertTrue(InputValidation.arrangementInputValidation("Some kind of name", "Annet", "420" , "Somewhere rd 5", false, LocalDate.of(2019,9,30),LocalDate.of(2019,10,1)));
        //assertTrue(InputValidation.arrangementInputValidation("Some kind of name", "Fotball", "420" , "Somewhere rd 5", false, LocalDate.of(2019,9,30),LocalDate.of(2019,9,29)));
        assertFalse(InputValidation.arrangementInputValidation("b", "Niet Comrade", "-1" , "Somewhere rd. 5", false, LocalDate.of(2019,9,30),LocalDate.of(2019,9,1)));
        assertFalse(InputValidation.arrangementInputValidation("This tittle is way to long which is why it will not work", "Something else", "1000000000" , "Rainbow roooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooad", false, LocalDate.of(2019,9,30),LocalDate.of(2019,9,1)));
    }

    @Test
    @Order(2)
    public void ArrangementAddTest() {
        //expectedArrangementer.add(addRemoveTest);
        //DataHandler.addArrangementer(addRemoveTest);
        /*
        if(compareArrangementArrays(expectedArrangementer,DataHandler.getArrangementer())) {
            System.out.println("Arrangement added succesfully!");
        } else {
            fail("Arrangement was not added?");
        }
        */
    }

    @Test
    @Order(3)
    public void ArrangementRemoveTest() {
        /*
        DataHandler.removeArrangementer(addRemoveTest);
        if(compareArrangementArrays(expectedArrangementer,DataHandler.getArrangementer())) {
            System.out.println("Arrengement removed succesfully!");
        } else {
            fail("Arrangement was not removed?");
        }
        */
    }

    @Test
    @Order(4)
    public void DataSaveJsonTest() {
        /*
        Arrangement saveArrangement = new Arrangement("Test Arrengement","Skirenn", 12,"Hakkebakken 420",false,LocalDate.of(2019,10,2),LocalDate.of(2019,10,4));
        expectedArrangementer.add(saveArrangement);
        DataHandler.addArrangementer(saveArrangement);
        DataHandler.writeToJSONFile(new File("src/no/hiof/gruppefire/files/arrangementsTest.json"));
        DataHandler.readFromJSONFil("/files/arrangementsTest.json");
        if(compareArrangementArrays(expectedArrangementer,DataHandler.getArrangementer())) {
            System.out.println("Data saved succesfully!");
        } else {
            fail("Data did not save properly?");
        }
        */
    }

    private boolean compareArrangementArrays(ArrayList<Arrangement> exArr, ArrayList<Arrangement> acArr) {

        if(exArr.size() != acArr.size())
            return false;

        for(int i = 0; i < exArr.size() && i < acArr.size() ;i++) {
            Arrangement ex = exArr.get(i);
            Arrangement ac = acArr.get(i);
            if(     ex.getName().equals(ac.getName()) &&
                    ex.getSport().equals(ac.getSport()) &&
                    ex.getParticipants() == ac.getParticipants() &&
                    ex.getAdress().equals(ac.getAdress()) &&
                    ex.isGruppe() == ac.isGruppe() &&
                    ex.getStartDate().equals(ac.getStartDate()) &&
                    ex.getEndDate().equals(ac.getEndDate())) {
            } else {
                return false;
            }
        }
        return true;
    }
}
