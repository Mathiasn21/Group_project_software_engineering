package no.hiof.gruppefire.tests;

import static org.junit.jupiter.api.Assertions.*;

import no.hiof.gruppefire.data.DataHandler;
import no.hiof.gruppefire.data.InputValidation;
import no.hiof.gruppefire.model.Arrangement;
import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GenerellTests {

    private ArrayList<Arrangement> actualArragementer = new ArrayList<>();
    private ArrayList<Arrangement> expectedArrangementer = new ArrayList<>();
    private Arrangement addRemoveTest = new Arrangement("Test Arrengement3","Basketball", 69,"Hakkebakken 420",false,LocalDate.of(2019,10,2),LocalDate.of(2019,10,4));

    @BeforeEach
    public void setup() {
        expectedArrangementer.clear();
        expectedArrangementer.add(new Arrangement("Test Arrengement","Annet", 420,"Oppibakken 15",false,LocalDate.of(2019,9,2),LocalDate.of(2019,9,4)));
        expectedArrangementer.add(new Arrangement("Test Arrengement2","Basketball", 69,"Hakkebakken 420",false,LocalDate.of(2019,10,2),LocalDate.of(2019,10,4)));
    }

    @AfterAll
    public void end() {
        DataHandler.clearArrangementer();
        DataHandler.addArrangementer(new Arrangement("Test Arrengement","Annet", 420,"Oppibakken 15",false,LocalDate.of(2019,9,2),LocalDate.of(2019,9,4)));
        DataHandler.addArrangementer(new Arrangement("Test Arrengement2","Basketball", 69,"Hakkebakken 420",false,LocalDate.of(2019,10,2),LocalDate.of(2019,10,4)));
        DataHandler.writeToJSONFile(new File("src/no/hiof/gruppefire/files/arrangementsTest.json"));
    }

    @Test
    @Order(1)
    public void inputValidationTest() {
        assertTrue(InputValidation.arrangementInputValidation("Some kind of name", "Annet", "420" , "Somewhere rd 5", false, LocalDate.of(2019,9,30),LocalDate.of(2019,10,1)));
        assertTrue(InputValidation.arrangementInputValidation("Some kind of name", "Fotball", "420" , "Somewhere rd 5", false, LocalDate.of(2019,9,30),LocalDate.of(2019,9,30)));
        assertFalse(InputValidation.arrangementInputValidation("b", "Niet Comrade", "-1" , "Somewhere rd. 5", false, LocalDate.of(2019,9,30),LocalDate.of(2019,9,1)));
        assertFalse(InputValidation.arrangementInputValidation("This tittle is way to long which is why it will not work", "Something else", "1000000000" , "Rainbow roooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooad", false, LocalDate.of(2019,9,30),LocalDate.of(2019,9,1)));
    }

    @Test
    @Order(2)
    public void DataRetrievalJsonTest() {
        DataHandler.readFromJSONFil("src/no/hiof/gruppefire/files/arrangementsTest.json");

        if(compareArrangementArrays(expectedArrangementer, DataHandler.getArrangementer())) {
            System.out.println("Data succesfully retrieved!");
        } else {
            fail("Data retrieval failed?");
        }
    }

    @Test
    @Order(3)
    public void ArrangementAddTest() {
        expectedArrangementer.add(addRemoveTest);
        DataHandler.addArrangementer(addRemoveTest);
        if(compareArrangementArrays(expectedArrangementer,DataHandler.getArrangementer())) {
            System.out.println("Arrangement added succesfully!");
        } else {
            fail("Arrangement was not added?");
        }
    }

    @Test
    @Order(4)
    public void ArrangementRemoveTest() {
        DataHandler.removeArrangementer(addRemoveTest);
        if(compareArrangementArrays(expectedArrangementer,DataHandler.getArrangementer())) {
            System.out.println("Arrengement removed succesfully!");
        } else {
            fail("Arrangement was not removed?");
        }
    }

    @Test
    @Order(5)
    public void DataSaveJsonTest() {
        Arrangement saveArrangement = new Arrangement("Test Arrengement","Skirenn", 12,"Hakkebakken 420",false,LocalDate.of(2019,10,2),LocalDate.of(2019,10,4));
        expectedArrangementer.add(saveArrangement);
        DataHandler.addArrangementer(saveArrangement);
        DataHandler.writeToJSONFile(new File("src/no/hiof/gruppefire/files/arrangementsTest.json"));
        DataHandler.readFromJSONFil("src/no/hiof/gruppefire/files/arrangementsTest.json");
        if(compareArrangementArrays(expectedArrangementer,DataHandler.getArrangementer())) {
            System.out.println("Data saved succesfully!");
        } else {
            fail("Data did not save properly?");
        }

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
