package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.data.InputValidation;
import no.hiof.set.gruppe.model.Arrangement;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrrengementTests {


    //@BeforeEach
    public void setup() {
    }

    //@AfterAll
    public void wrapup() {
    }

    //@Test
    //@Order(1)
    public void inputValidationTest() {
        boolean test = InputValidation.arrangementInputValidation("pes","Annet","420","Hakkebakkeskogen", true, LocalDate.of(2019,10,10), LocalDate.of(2019,10,11));
        System.out.println(test);
    }

    //@Test
    //@Order(2)
    public void ArrangementAddTest() {
    }

    //@Test
    //@Order(3)
    public void ArrangementRemoveTest() {
    }

    //@Test
    public void DataLoadJsonTest() {

    }

    //@Test
    //@Order(4)
    public void DataSaveJsonTest() {

    }


    private boolean compareArrangementArrays(ArrayList<Arrangement> exArr, ArrayList<Arrangement> acArr) {

        /*if(exArr.size() != acArr.size())
            return false;

        for(int i = 0; i < exArr.size() && i < acArr.size() ;i++) {
            Arrangement ex = exArr.get(i);
            Arrangement ac = acArr.get(i);
            return ex.equals(ac);

        }

          return true;
        */

        return exArr.equals(acArr);
    }
}
