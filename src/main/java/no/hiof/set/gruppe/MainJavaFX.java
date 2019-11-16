package no.hiof.set.gruppe;

/*Guide Controllers
 * 1. Import Statements
 * 2. Local Fields
 * 3. Start Method
 * 4. SetupWindow Interface Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.set.gruppe.core.IBaseEntity;
import no.hiof.set.gruppe.core.Repository;
import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.GUI.controller.abstractions.Controller;
import no.hiof.set.gruppe.GUI.controller.abstractions.IController;
import no.hiof.set.gruppe.GUI.controller.abstractions.IControllerDataTransfer;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * MainJavaFX is a class that controls all of the windows in the application.
 * Its primary goal is to control and mediate between all other controllers.
 * @author Gruppe4
 */
public class MainJavaFX extends Application implements SetupWindow {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private Stage stage;
    public static void main(String[] args) {
        launch(args);
    }


    // --------------------------------------------------//
    //                3.Start Method                     //
    // --------------------------------------------------//
    @Override
    public void start(@NotNull Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(MainJavaFX.class.getResource("Login.fxml"));
        Parent editLayout = loader.load();

        IController controller = (loader.getController());
        controller.setMainController(this);

        Scene scene = new Scene(editLayout);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Logg inn");
        stage.show();

        List<Arrangement> test = new Repository().queryAllEntityConnectedToUserData(Arrangement.class, ProtoUser.ORGANIZER);
        List<Arrangement> test2 = new Repository().queryAllDataOfGivenType(Arrangement.class);
    }


    // --------------------------------------------------//
    //          4.SetupWindow Interface Methods          //
    // --------------------------------------------------//
    /**
     * This function is responsible for setting up a new window.
     * This is custom made for controllers that implement the interface: {@link IController}
     *
     * @param controller {@link IController}
     */
    public void setupWindow(@NotNull IController controller) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainJavaFX.class.getResource(controller.getViewInformation().viewName));
        Parent editLayout = loader.load();
        setupStage(controller, stage, editLayout);

        //setting mainController on next controller switch
        controller = loader.getController();
        controller.setMainController(this);
    }

    /**
     * This function is responsible for setting up a new window.
     * This is custom made for controllers that implement the interface: {@link IControllerDataTransfer}
     * And as such mediates between controllers that requires this.
     *
     * @param controller {@link IControllerDataTransfer}
     * @param object     {@link Object}
     */
    public void setupWindow(@NotNull IControllerDataTransfer controller, Object object) {
        boolean errorOccurred = true;
        ErrorExceptionHandler err = null;
        Throwable thrown = null;
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainJavaFX.class.getResource(controller.getViewInformation().viewName));
            Parent editLayout = loader.load();

            controller = handleControllerSpecifics(controller, object, stage, loader);
            setupStage(controller, stage, editLayout);

            controller.setMainController(this);
            errorOccurred = false;

        } catch (DataFormatException datFormEx) {
            err = ErrorExceptionHandler.ERROR_WRONG_DATA_OBJECT;
            thrown = datFormEx;

        } catch (IOException IOEx) {
            err = ErrorExceptionHandler.ERROR_LOAD_RESOURCE;
            thrown = IOEx;

        } finally {
            try {
                if (errorOccurred && (err != null)) ErrorExceptionHandler.createLogWithDetails(err, thrown);
            } catch (Exception e) {
                Controller.createAlert(ErrorExceptionHandler.ERROR_LOGGING_ERROR);
            }
        }
    }


    private IControllerDataTransfer handleControllerSpecifics(@NotNull IControllerDataTransfer controller, Object object, Stage stage, FXMLLoader loader) throws DataFormatException {
        IControllerDataTransfer oldController = controller;
        controller = loader.getController();
        controller.setDataFields(object);
        IControllerDataTransfer finalController = controller;
        stage.setOnHidden((Event) -> {
            if (finalController.hasNewObject()) {
                try {
                    oldController.setDataFields(finalController.getDataObject());
                } catch (DataFormatException e) {
                    e.printStackTrace();
                }
            }
            oldController.updateView();
        });
        return controller;
    }
    private void setupStage(@NotNull IController controller, Stage stage, Parent editLayout) {
        Scene editScene = new Scene(editLayout);
        stage.setScene(editScene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stage);
        stage.setTitle(controller.getViewInformation().viewTitle);
        stage.setResizable(false);

        stage.show();
    }
}