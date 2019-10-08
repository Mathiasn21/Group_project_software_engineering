package no.hiof.set.gruppe;

/*Guide Controllers
 * 1. Import Statements
 * 2. Local Fields
 * 3. Overridden Methods
 * 4. Public Methods
 * 5. Static Methods
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
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.controller.*;
import java.io.IOException;

/**
 * MainJavaFX is a class that controls all of the windows in the application.
 * Its primary goal is to controll and mediate between all other controllers.
 *
 * @author Gruppe4
 */
public class MainJavaFX extends Application implements SetupWindow {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private static Scene scene;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }


    // --------------------------------------------------//
    //                3.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(MainJavaFX.class.getResource("Arrangement.fxml"));
        Parent editLayout = loader.load();

        IController controller= (loader.getController());
        controller.setMainController(this);

        stage.setOnHidden((Event)-> controller.onCloseStoreInformation());
        Scene scene = new Scene(editLayout, 230, 450);
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.setTitle("Arrangementer");
        stage.show();
    }


    // --------------------------------------------------//
    //                4.Public Methods                   //
    // --------------------------------------------------//
    /**
     * This function is responsible for setting up a new window.
     * This is custom made for controllers that implement the interface: {@link IController}
     * @param controller {@link IController}
     */
    public void setupWindow(IController controller)throws IOException{
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainJavaFX.class.getResource(controller.getName()));
        Parent editLayout = loader.load();
        stage.setOnHidden((Event)-> controller.onCloseStoreInformation());
        Scene editScene = new Scene(editLayout, 400, 450);
        stage.setScene(editScene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stage);
        stage.setTitle(controller.getTitle());
        controller.setMainController(this);
        System.out.println(this);

        stage.show();
    }

    /**
     * This function is responsible for setting up a new window.
     * This is custom made for controllers that implement the interface: {@link IControllerDataTransfer}
     * And as such mediates between controllers that requires this.
     * @param controller {@link IControllerDataTransfer}
     * @param object {@link Object}
     */
    public void setupWindow(IControllerDataTransfer<Object> controller, Object object){
        try{
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainJavaFX.class.getResource(controller.getName()));
            Parent editLayout = loader.load();

            IControllerDataTransfer<Object> oldController = controller;
            controller = loader.getController();
            controller.setDataFields(object);
            IControllerDataTransfer<Object> finalController = controller;
            stage.setOnHidden((Event)->{
                if(finalController.hasNewObject()){
                    try {
                        oldController.setDataFields(finalController.getDataObject());
                    } catch (DataFormatException e) {
                        e.printStackTrace();
                    }
                }
                finalController.onCloseStoreInformation();
            });

            Scene editScene = new Scene(editLayout, 400, 450);
            stage.setScene(editScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.stage);
            stage.setTitle(controller.getTitle());
            controller.setMainController(this);
            System.out.println(this);

            stage.show();
        }catch (DataFormatException | IOException dataEx){
            dataEx.printStackTrace();
        }
    }


    // --------------------------------------------------//
    //                5.Static Methods                   //
    // --------------------------------------------------//
    /**
     * Let be. This will be used for later in order to dynamically switching between windows.
     * */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainJavaFX.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Let be. This will be used for later in order to dynamically switching between windows.
     * */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
}
