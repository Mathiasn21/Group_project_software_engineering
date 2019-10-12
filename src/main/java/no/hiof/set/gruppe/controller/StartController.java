package no.hiof.set.gruppe.controller;

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
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.data.DataHandler;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller controls all functionality and logic pertaining
 * to the ArrangementView
 * @author Gruppe4
 */
public class StartController extends Controller {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String title = "";
    private String name = "NewAlterArrangement.fxml";
    private ObservableList<Arrangement>arrangementListObservable;
    private FilteredList<Arrangement> filteredList;
    private Arrangement currentArrangement = null;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private Button newArrangementBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Text arrangementNameView, arrangementAdressView, arrangementDateView, arrangementParticipantsView, arrangementGorIView, arrangementSportView;
    @FXML
    private TextField arrSearch;
    @FXML
    private ListView<Arrangement>listview = new ListView<>();

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//
    /*
    * Needs to be extended with logic for the following items:
    * Search AND sort after category
    * Add numb of participants for text field
    *
    * */


    private void search(ActionEvent actionEvent){
        filteredList.setPredicate(this::lowerCaseTitleSearch);
        listview.setItems(filteredList);
        listview.refresh();
    }

    private void onClick(ActionEvent event) {
        title = "Ny";
        createNewView(this, null);
    }

    private void onEditClick(ActionEvent event){
        if(listview.getSelectionModel().getSelectedItem() != null){
            title = "Rediger";
            createNewView(this, currentArrangement);
        }
        else
            System.out.println("Du har ikke valgt et arrangement");
    }

    private void onDelete(ActionEvent event){
        if(listview.getSelectionModel().getSelectedItem() == null)
            return;
        deleteArrangement();
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//
    private void changedView(){
        Arrangement arrangement = listview.getSelectionModel().getSelectedItem();
        if(currentArrangement == null || !currentArrangement.equals(arrangement)){
            currentArrangement = arrangement;
        }
        setInformationAboutArrangementInView(currentArrangement);
        listview.refresh();
    }

    private void setupFilteredList(){
        filteredList = arrangementListObservable.filtered(arrangement -> true);
        listview.setItems(filteredList);
        arrSearch.setText("");
    }


    /**
     * Returns a Boolean based on if the Arrangement name contains
     * the given search string.
     * @param arrangement Arrangement
     * @return boolean
     */
    private boolean lowerCaseTitleSearch(Arrangement arrangement){
        String title = arrangement.getName().toLowerCase();
        String search = arrSearch.getText().toLowerCase();
        return title.contains(search);
    }

    private void populateListView() {
        arrangementListObservable = FXCollections.observableArrayList(DataHandler.getArrangementsData());
        setupFilteredList();
        listview.refresh();
    }

    private void deleteArrangement(){
        Arrangement selectedItem = listview.getSelectionModel().getSelectedItem();
        arrangementListObservable.remove(selectedItem);
        listview.getSelectionModel().selectFirst();
    }

    private void onClickListView(MouseEvent mouseEvent) {
        changedView();
        System.out.println(currentArrangement);
        listview.refresh();
    }

    private void setInformationAboutArrangementInView(Arrangement arrangement){
        arrangementNameView.setText(arrangement.getName());
        arrangementSportView.setText(arrangement.getSport());
        arrangementAdressView.setText(arrangement.getAdress());
        arrangementDateView.setText(arrangement.getStartDate().toString() + " til " + arrangement.getEndDate().toString());
        arrangementParticipantsView.setText(Integer.toString(arrangement.getParticipants()));
        arrangementGorIView.setText(groupsOrIndividuals(arrangement));
    }

    private String groupsOrIndividuals(Arrangement arrangement){
        if(arrangement.isGroup())
            return "Lagkonkurranse";
        return "Individuell konkurranse";
    }

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//
    //handles the title and name of current view. Here name is the local path
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
        return listview.getSelectionModel().getSelectedItem();
    }


    /**
     * Handles data setup and interaction from other controllers.
     * @param object Object
     * @throws DataFormatException Exception
     */
    @Override
    public void setDataFields(Object object) throws DataFormatException {
        if (!(object instanceof Arrangement)){
            throw new DataFormatException();
        }
        System.out.println("Adding arrangement: " + object);
        arrangementListObservable.add((Arrangement) object);
        listview.refresh();
        changedView();
    }

    @Override
    public void onCloseStoreInformation() {
        new DataHandler().storeArrangementsData(arrangementListObservable);
    }
    @Override
    public void updateView(){
        if(currentArrangement == null){return;}
        changedView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteBtn.setOnAction(this::onDelete);
        editBtn.setOnAction(this::onEditClick);
        newArrangementBtn.setOnAction(this::onClick);
        listview.setOnMouseClicked(this::onClickListView);
        arrSearch.setOnAction(this::search);
        populateListView();
    }
}
