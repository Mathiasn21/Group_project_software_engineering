package no.hiof.set.gruppe.tests;

import javafx.scene.text.Text;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.model.Arrangement;
import org.junit.jupiter.api.Test;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static org.junit.Assert.*;

public class ControllerTests {

    //Vet ikke helt om disse testene er nyttige..Men tenkte at de ikke var som dumme
    public class ControllerTest extends Controller {
        @Override
        public void initialize(URL location, ResourceBundle resources) { }
        @Override
        public Object getDataObject() {
            return null;
        }
        @Override
        public void setDataFields(Object controller) throws DataFormatException { }
        @Override
        public String getTitle() {
            return null;
        }
        @Override
        public String getName() {
            return null;
        }
    }

    @Test
    public void group_false (){
        ControllerTest controllerTest = new ControllerTest();
        Arrangement arrangement = new Arrangement("name", "Sykkelritt", 55, "Nissevegen 5", false, "2019-11-03", "2019-11-05", "blablablabla");

        assertEquals("Individuell konkurranse", controllerTest.groupsOrIndividuals(arrangement));
    }

    private Text name, sport, adress, date, participants, groups, description;
    @Test
    public void arrangementData_same_length_as_viewFields(){

        Controller controllerTest = new ControllerTest();
        Arrangement arrangement = new Arrangement("name", "Sykkelritt", 55, "Nissevegen 5", false, "2019-11-03", "2019-11-05", "blablablabla");

        ArrayList viewFields = controllerTest.viewFields(name, sport, adress, date, participants, groups, description);
        ArrayList dataFields = controllerTest.arrangementData(arrangement);

        assertEquals(0, viewFields.size() - dataFields.size());
    }
}
