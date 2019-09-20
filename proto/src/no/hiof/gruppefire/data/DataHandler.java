package no.hiof.gruppefire.data;

import no.hiof.gruppefire.model.Arrangement;
import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;



public class DataHandler {

    private static ArrayList<Arrangement> arrangementer = new ArrayList<>();

    public static void writeToJSONFile(File filepath){

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        String jsonTextList = gson.toJson(arrangementer);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            bufferedWriter.write(jsonTextList);
        } catch (IOException ioexc) {
            System.out.println(ioexc.getMessage());
        }
    }

    public static void readFromJSONFil(String filepath) {

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

        setArrangementer(arrangementListFromFile);
    }

    public static ArrayList<Arrangement> getArrangementer() {
        return arrangementer;
    }

    public static void setArrangementer(ArrayList<Arrangement> arrangementer) {
        DataHandler.arrangementer = arrangementer;
    }

    public static void addArrangementer(Arrangement a) {
        arrangementer.add(a);
    }

    public static void printArrangementer() {
        System.out.println(arrangementer);
    }
}