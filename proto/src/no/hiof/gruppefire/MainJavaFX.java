package no.hiof.gruppefire;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.gruppefire.controller.NewArrangementController;
import no.hiof.gruppefire.model.Arrangement;
import java.io.IOException;

public class MainJavaFX extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        try{
            this.primaryStage = primaryStage;

            primaryStage.setTitle("Arrangements");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("view/Start.fxml"));
            Parent filmOversiktLayout = fxmlLoader.load();
            Scene hovedScene = new Scene(filmOversiktLayout, 500, 300);
            primaryStage.setScene(hovedScene);
            primaryStage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
