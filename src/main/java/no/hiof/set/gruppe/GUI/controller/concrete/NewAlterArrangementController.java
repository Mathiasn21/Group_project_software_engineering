package no.hiof.set.gruppe.GUI.controller.concrete;

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
import javafx.scene.control.*;
import no.hiof.set.gruppe.GUI.controller.abstractions.ControllerTransferData;
import no.hiof.set.gruppe.GUI.model.ViewInformation;
import no.hiof.set.gruppe.core.Repository;
import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.core.validations.Validation;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.ValidationResult;
import no.hiof.set.gruppe.model.constantInformation.GroupCategory;
import no.hiof.set.gruppe.model.constantInformation.SportCategory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * This controller deals with the logic pertaining to either editing
 * or a new creation of an arrangement. Validation of arrangement happens through
 * {@link Validation} class.
 * @author Gruppe4
 */
@SuppressWarnings("FieldCanBeLocal")
public class NewAlterArrangementController extends ControllerTransferData {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private final String name = "NewAlterArrangement.fxml";
    private final String title = "Arrangement";
    private boolean createdNewObject = false;
    private boolean group;
    private Arrangement arrangementToEdit = null;
    private String arrName;
    private String sport;
    private String partic;
    private String desc;
    private String address;
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

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//
    /**
     * Only saves the information if given information in the view is valid.
     * Uses {@link Validation} in order to validate the information.
     * @param event {@link ActionEvent}
     */
    private void saveClicked(ActionEvent event){
        getArrangementData();
        if(checkLengthOfAllFields())return;
        if(illegalNumberFormat())return;
        setArrangementData();
        if(validateArrangementData())return;
        closeWindow(cancelBtn);

        try {if(!createdNewObject)Repository.mutateObject(arrangementToEdit);
        } catch (DataFormatException e) {
            Throwable throwable = e;
            try { ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, e);
            } catch (IOException IOException) { throwable = IOException; }
            createAlert(throwable instanceof DataFormatException ? ErrorExceptionHandler.ERROR_ACCESSING_DATA : ErrorExceptionHandler.ERROR_LOGGING_ERROR);
        }
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void cancelClicked(ActionEvent event){
        arrangementToEdit = null;
        createdNewObject  = false;
        closeWindow(cancelBtn);
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
        return arrName.length() == 0 || sport.length() == 0 || partic.length() == 0 || desc.length() == 0 || address.length() == 0 || startDateInput.getValue() == null || endDateInput.getValue() == null;
    }

    private boolean illegalNumberFormat(){
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
        }
        return !result.IS_VALID;
    }

    private int getSportIndex(){
        ObservableList list = FXCollections.observableArrayList(SportCategory.values());
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).toString().equals(arrangementToEdit.getSport()))return i;
        }
        return 0;
    }

    private int getGroupCategoryIndex(){
        if(arrangementToEdit.isGroup())return 0;
        return 1;
    }

    /**
     * @param result String
     */
    private void setErrorField(String result) {
        ErrorField.setText(result);
        ErrorField.setVisible(true);
        ErrorField.setDisable(false);
        ErrorField.setEditable(false);
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
     * @return Object
     */
    @Override
    public Object getDataObject() {return arrangementToEdit;}

    /**
     * @return boolean
     */
    @Override
    public boolean hasNewObject(){return createdNewObject;}

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
            groupInput.getSelectionModel().select(getGroupCategoryIndex());
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
    public ViewInformation getViewInformation() { return new ViewInformation(name, title); }

}
