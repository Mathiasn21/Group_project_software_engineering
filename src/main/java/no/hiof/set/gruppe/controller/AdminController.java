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
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminController extends Controller {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    String name = "Login";
    String title = "Login";


    // --------------------------------------------------//
    //                2.Private Methods                  //
    // --------------------------------------------------//



    // --------------------------------------------------//
    //                5.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
