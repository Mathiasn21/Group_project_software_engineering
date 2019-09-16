package no.hiof.gruppefire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.gruppefire.controller.NewArrangementController;
import no.hiof.gruppefire.model.Arrangement;

import java.io.IOException;

public class MainJavaFX extends Application {

    private static MainJavaFX application;
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

    public boolean newArrangement(Arrangement newArrangement){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/NewArrangement"));
            Parent layout = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New arrangement");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene =new Scene(layout);
            stage.setScene(scene);

            NewArrangementController newArrangementController = loader.getController();



        }catch (IOException | IllegalStateException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
