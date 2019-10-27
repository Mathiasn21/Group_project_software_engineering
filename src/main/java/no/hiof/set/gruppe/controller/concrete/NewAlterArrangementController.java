package no.hiof.set.gruppe.controller.concrete;
/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. FXML Fields
 * 4. On Action Methods
 * 5. Private Methods
 * 6. Overridden Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.AccessibleAction;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.model.ValidationResult;
import no.hiof.set.gruppe.model.ViewInformation;
import no.hiof.set.gruppe.util.Validation;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.constantInformation.GroupCategory;
import no.hiof.set.gruppe.model.constantInformation.SportCategory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller deals with the logic pertaining to either editing
 * or a new creation of an arrangement. Validation of arrangement happens through
 * {@link Validation} class.
 * @author Gruppe4
 */
public class NewAlterArrangementController extends Controller {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String name = "NewAlterArrangement.fxml";
    private String title = "Arrangement";
    private Arrangement arrangementToEdit = null;
    private boolean createdNewObject = false;
    private String arrName;
    private String sport;
    private String partic;
    private String desc;
    private String address;
    private boolean group;
    private LocalDate startDate;
    private LocalDate endDate;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private TextField nameInput, participantsInput, adressInput;
    @FXML
    private TextArea descriptionInput, ErrorField;
    @FXML
    private DatePicker startDateInput, endDateInput;
    @FXML
    private ComboBox<GroupCategory> groupInput;
    @FXML
    private ComboBox<SportCategory> sportComboBoxInput;
    @FXML
    public Button saveBtn, cancelBtn;

    /**
     * Only saves the information if given information in the view is valid.
     * Uses {@link Validation} in order to validate the information.
     */
    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    private void saveClicked(ActionEvent event){
        getArrangementData();
        if(checkLengthOfAllFields())return;
        if(illegalNumberFormat())return;
        setArrangementData();
        if(validateArrangementData())return;
        closeWindow();
    }

    public void cancelClicked(ActionEvent event){
        closeWindow();
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//

    private void setupActionHandlers(){
        saveBtn.setOnAction(this::saveClicked);
        cancelBtn.setOnAction(this::cancelClicked);
    }

    private void setupComboBoxes(){
        sportComboBoxInput.setItems(FXCollections.observableArrayList(SportCategory.values()));
        groupInput.setItems(FXCollections.observableArrayList(GroupCategory.values()));
        sportComboBoxInput.getSelectionModel().select(0);
        groupInput.getSelectionModel().select(0);
    }

    private int getSportIndex(){
        ObservableList list = FXCollections.observableArrayList(SportCategory.values());
       for(int i = 0; i < list.size(); i++){
           if(list.get(i).toString() == arrangementToEdit.getSport())return i;
       }
       return 0;
    }

    private void getArrangementData(){
        arrName = nameInput.getText();
        sport = sportComboBoxInput.getSelectionModel().getSelectedItem().toString();
        partic = participantsInput.getText();
        desc = descriptionInput.getText();
        address = adressInput.getText();
        group = groupInput.getSelectionModel().getSelectedItem().isGroup;
        startDate = startDateInput.getValue();
        endDate = endDateInput.getValue();
    }

    private void setArrangementData(){
        if(arrangementToEdit == null) {
            arrangementToEdit = new Arrangement();
            createdNewObject = true;
        }
        arrangementToEdit.setName(arrName);
        arrangementToEdit.setParticipants(Integer.parseInt(partic));
        arrangementToEdit.setAddress(address);
        arrangementToEdit.setDescription(desc);
        arrangementToEdit.setGruppe(group);
        arrangementToEdit.setSport(sport);
        arrangementToEdit.setStartDate(startDate.toString());
        arrangementToEdit.setEndDate(endDate.toString());
    }

    private boolean checkLengthOfAllFields(){
        if(arrName.length() == 0 || sport.length() == 0 || partic.length() == 0 || desc.length() == 0 || address.length() == 0 || startDate == null || endDate == null)
            return true;
        return false;
    }

    private boolean illegalNumberFormat(){
        //must throw exception in the future
        //"Ugyldig nummer format.\n";
        if(!Validation.ofNumber(partic)){
            setErrorField("Antall deltakere har ikke gyldig nummer format.");
            return true;
        }
        return false;
    }

    private boolean validateArrangementData(){
        ValidationResult result = Validation.ofArrangement(arrangementToEdit);
        if(!result.IS_VALID){
            setErrorField(result.RESULT);
            return true;
        }
        return false;
    }

    private void closeWindow(){
        ((Stage)cancelBtn.getScene().getWindow()).close();
    }

    /**
     * @param result String
     */
    private void setErrorField(String result) {
        ErrorField.setText(result);
        ErrorField.setVisible(true);
        ErrorField.setDisable(false);
    }

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//
    /**
     * @param url {@link URL}
     * @param resourceBundle {@link ResourceBundle}
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setupActionHandlers();
        setupComboBoxes();
    }

    /**
     * @return String
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return Object
     */
    @Override
    public Object getDataObject() {
        return arrangementToEdit;
    }

    /**
     * @return boolean
     */
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
            adressInput.setText(arrangement.getAddress());
            sportComboBoxInput.getSelectionModel().select(getSportIndex());
            participantsInput.setText(Integer.toString(arrangement.getParticipants()));
            startDateInput.setValue(arrangement.getStartDate());
            endDateInput.setValue(arrangement.getEndDate());
            descriptionInput.setText(arrangement.getDescription());
        }
    }

    /**
     * @return {@link ViewInformation}
     */
    //new method for returning information about the view
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }
}
