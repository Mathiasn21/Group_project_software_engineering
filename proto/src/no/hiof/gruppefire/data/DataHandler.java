package no.hiof.gruppefire.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import no.hiof.gruppefire.model.Arrangement;
import com.google.gson.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataHandler {

    public static void writeToJSONFile(ArrayList<Arrangement>arrangemenList, File filepath){

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        String jsonTextList = gson.toJson(arrangemenList);

        System.out.println(jsonTextList);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            bufferedWriter.write(jsonTextList);
        } catch (IOException ioexc) {
            System.out.println(ioexc.getMessage());
        }
    }
}