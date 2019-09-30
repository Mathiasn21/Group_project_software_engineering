package no.hiof.gruppefire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.gruppefire.controller.NewAlterArrangementController;
import no.hiof.gruppefire.data.DataHandler;
import no.hiof.gruppefire.model.Arrangement;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.IOException;

public class MainJavaFX extends Application {

    private Stage primaryStage;

    private static MainJavaFX application;

    public Arrangement arrangementToSend;

    public MainJavaFX(){
        application = this;
    }

    @Override
    public void start(Stage primaryStage){
        try{
            this.primaryStage = primaryStage;

            primaryStage.setTitle("Arrangements");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("view/Start.fxml"));
            Parent startView = fxmlLoader.load();
            Scene mainScene = new Scene(startView, 450, 300);
            primaryStage.setScene(mainScene);
            primaryStage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newAlterWindow(Arrangement arrangement, String title) throws IOException{

        arrangementToSend = arrangement;

        Stage editStage = new Stage();

        FXMLLoader fxmlInnlaster = new FXMLLoader();
        fxmlInnlaster.setLocation(getClass().getResource("view/NewAlterArrangement.fxml"));
        Parent editLayout = fxmlInnlaster.load();

        NewAlterArrangementController newAlterArrangementController = fxmlInnlaster.getController();

        Scene editScene = new Scene(editLayout, 230, 450);
        newAlterArrangementController.setStage(editStage);

        editStage.setScene(editScene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.initOwner(primaryStage);
        editStage.setTitle(title);
        editStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static MainJavaFX getApplication() {
        return application;
    }

    public Arrangement getArrangementToEdit() {
        return arrangementToSend;
    }

    public void close(Stage s){
        s.close();
    }

    @Override
    public void stop(){
        String arrangementsFilepath = "src/no/hiof/gruppefire/files/arrangements.json";
        DataHandler.writeToJSONFile(new File(arrangementsFilepath));
    }
}
