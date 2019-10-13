package no.hiof.set.gruppe.controller;

/*Guide Controllers
 * 1. Import Statements
 * 2. Local Fields
 * 3. FXML Fields
 * 4. FXML Methods
 * 5. Overridden Methods
 * */


// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminController extends Controller {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    String name = "Login";
    String title = "Login";

     @FXML
     private Button logOut;

    // --------------------------------------------------//
    //                2.Private Methods                  //
    // --------------------------------------------------//
    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)logOut.getScene().getWindow()).close();
        createNewView(this);
    }

    // --------------------------------------------------//
    //                5.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logOut.setOnAction(this::returnToMainWindow);
    }

    @Override
    public Object getDataObject() {
        return null;
    }

    @Override
    public void setDataFields(Object object) throws DataFormatException {

    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getName() {
        return name;
    }
}
