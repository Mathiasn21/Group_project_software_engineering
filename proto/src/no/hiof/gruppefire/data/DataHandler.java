package no.hiof.gruppefire.data;

import no.hiof.gruppefire.model.Arrangement;
import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DataHandler {

    public static void writeToJSONFile(ArrayList<Arrangement>arrangemenList, File filepath){

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        String jsonTextList = gson.toJson(arrangemenList);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            bufferedWriter.write(jsonTextList);
        } catch (IOException ioexc) {
            System.out.println(ioexc.getMessage());
        }
    }

    public static ArrayList<Arrangement> readFromJSONFil(String filepath) {

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        ArrayList<Arrangement> arrangementListFromFile = new ArrayList<>();

        String line = "";

        StringBuilder jsonTextFromFile = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            while ((line = bufferedReader.readLine()) != null) {
                jsonTextFromFile.append(line);
            }

            Arrangement[] arrangementArray = gson.fromJson(jsonTextFromFile.toString(), Arrangement[].class);

            arrangementListFromFile.addAll(Arrays.asList(arrangementArray));

        }catch (IOException ioexc) {
            System.out.println(ioexc.getMessage());
        }

        return arrangementListFromFile;
    }
}