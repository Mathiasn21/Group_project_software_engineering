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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.GroupCategory;
import no.hiof.set.gruppe.model.SportCategory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller deals with the logic pertaining to either editing
 * or a new creation of an arrangement.
 * @author Gruppe4
 */
public class NewAlterArrangementController extends Controller{

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String name = "NewAlterArrangement.fxml";
    private String title = "arrangement";
    private Arrangement arrangementToEdit = null;
    private boolean createdNewObject = false;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private TextField nameInput, participantsInput, adressInput;
    @FXML
    private DatePicker startDateInput, endDateInput;
    @FXML
    private ComboBox<GroupCategory> groupInput;
    @FXML
    private ComboBox<SportCategory> sportComboBoxInput;
    @FXML
    public Button saveBtn;
    @FXML
    public Button cancelBtn;

    // --------------------------------------------------//
    //                4.FXML Methods                     //
    // --------------------------------------------------//
    @FXML
    public void saveClicked(){
        if(arrangementToEdit == null) {
            arrangementToEdit = new Arrangement();
            createdNewObject = true;
        }
        arrangementToEdit.setName(nameInput.getText());
        arrangementToEdit.setParticipants(Integer.parseInt(participantsInput.getText()));
        arrangementToEdit.setAdress(adressInput.getText());

        arrangementToEdit.setGruppe(groupInput.getSelectionModel().getSelectedItem().isGroup);
        arrangementToEdit.setSport(sportComboBoxInput.getSelectionModel().getSelectedItem().toString());
        arrangementToEdit.setStartDate(startDateInput.getValue().toString());
        arrangementToEdit.setEndDate(endDateInput.getValue().toString());

        System.out.println("saving arrangement: " + arrangementToEdit);
        ((Stage)saveBtn.getScene().getWindow()).close();
    }

    @FXML
    public void cancelClicked(){
        ((Stage)cancelBtn.getScene().getWindow()).close();
    }

    // --------------------------------------------------//
    //                5.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        //setting up combo boxes
        sportComboBoxInput.setItems(FXCollections.observableArrayList(SportCategory.values()));
        groupInput.setItems(FXCollections.observableArrayList(GroupCategory.values()));

        sportComboBoxInput.getSelectionModel().select(0);
        groupInput.getSelectionModel().select(0);
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
    public boolean hasNewObject(){
        return createdNewObject;
    }

    /**
     * Setups the view and its data fields.
     * @param object Object
     */
    @Override
    public void setDataFields(Object object){
        if (object instanceof Arrangement){
            Arrangement arrangement = (Arrangement)object;
            arrangementToEdit = arrangement;
            nameInput.setText(arrangement.getName());
            adressInput.setText(arrangement.getAdress());
            participantsInput.setText(Integer.toString(arrangement.getParticipants()));
            startDateInput.setValue(arrangement.getStartDate());
            endDateInput.setValue(arrangement.getEndDate());
        }
    }
}
