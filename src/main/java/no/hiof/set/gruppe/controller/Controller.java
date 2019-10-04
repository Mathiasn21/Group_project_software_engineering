package no.hiof.set.gruppe.controller;

import javafx.fxml.Initializable;
import no.hiof.set.gruppe.MainJavaFX;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller implements IControllerDataTransfer, Initializable {
    private MainJavaFX mainController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    public void setMainController(MainJavaFX mainController){
        this.mainController = mainController;
    }

    public void createNewView(Controller controller) {
        try {
            mainController.setupWindow(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createNewView(IControllerDataTransfer<Object> controller, Object object) {
        mainController.setupWindow(controller, object);
    }

    @Override
    public void onCloseStoreInformation(){
    }
    public boolean hasNewObject(){
        return false;
    }

}
