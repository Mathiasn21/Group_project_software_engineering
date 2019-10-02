package no.hiof.set.gruppe.controller;
/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. FXML Fields
 * 4. FXML Methods
 * 5. Overridden Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.model.Arrangement;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * NewArrangementController is a class that controls the "NewAlterArrangement" view.
 * Holds information about stage, application and arrangement.
 *
 * @author Gruppe4
 */
public class NewAlterArrangementController extends Controller{



    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String name = "NewAlterArrangement.fxml";
    private String title = "arrangement";
    private Arrangement arrangementToEdit;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private TextField nameInput, participantsInput, adressInput;
    @FXML
    private DatePicker startDateInput, endDateInput;
    @FXML
    private ComboBox<String> groupInput = new ComboBox<>();
    @FXML
    private ComboBox<String> sportComboBoxInput;
    @FXML
    public Button saveBtn;
    @FXML
    public Button cancelBtn;

    // --------------------------------------------------//
    //                4.FXML Methods                     //
    // --------------------------------------------------//
    @FXML
    public void saveClicked(){
        //Any changes tracked are saved
    }

    @FXML
    public void cancelClicked(){
        //Calls hide on current stage, does not track changes
    }

    // --------------------------------------------------//
    //                5.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Get current selected Arrangement
    }

    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public Object getDataObject() {
        return arrangementToEdit;
    }

    @Override
    public void setDataFields(Object object) throws DataFormatException {
        if (!(object == null || object instanceof Arrangement)){
            throw new DataFormatException();
        }
        Arrangement arrangement = (Arrangement)object;
        arrangementToEdit = arrangement;
        nameInput.setText(arrangement.getName());
        adressInput.setText(arrangement.getAdress());
        participantsInput.setText(Integer.toString(arrangement.getParticipants()));
        startDateInput.setValue(arrangement.getStartDate());
        endDateInput.setValue(arrangement.getEndDate());
    }

    // --------------------------------------------------//
    //                2.Private Methods                  //
    // --------------------------------------------------//
}
