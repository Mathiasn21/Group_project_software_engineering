package no.hiof.gruppefire.data;

import no.hiof.gruppefire.model.Arrangement;
import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DataHandler is a class that handles data.
 *
 * @author Gruppe4
 */

public class DataHandler {

    /**
     * A list with arrangements.
     */
    private static ArrayList<Arrangement> arrangements = new ArrayList<>();


    /**
     * Writes arrangement data to a JSON file.
     * @param filepath
     */
    public static void writeToJSONFile(File filepath){

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String jsonTextList = gson.toJson(arrangements);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            bufferedWriter.write(jsonTextList);
        } catch (IOException ioexc) {
            System.out.println(ioexc.getMessage());
        }
    }

    /**
     * Reads data from a JSON file.
     * @param filepath
     */
    public static void readFromJSONFil(String filepath) {

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        ArrayList<Arrangement> arrangementListFromFile = new ArrayList<>();
        String line;
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

    /**
     * Adds sport alternatives to a list.
     * @return arrayList with sports
     */
    public static ArrayList<String>sportsToComboBox(){

        ArrayList arraylist = new ArrayList();

        //Skal egentlig hentes fra fil. Dette er bare midlertidig
        arraylist.add("Fotball");
        arraylist.add("Basketball");
        arraylist.add("Friidrett");
        arraylist.add("Sykkelritt");
        arraylist.add("Skirenn");
        arraylist.add("Annet");

        return arraylist;

    }

    /**
     * Adds arrangements to the arrangements list.
     * @param a
     */
    public static void addArrangementer(Arrangement a) {
        arrangements.add(a);
    }

    /**
     * Removes an arrangement the arrangements list.
     * @param a
     */
    public static void removeArrangementer(Arrangement a) {
        arrangements.remove(a);
    }

    /**
     * Clears the arrangements list.
     */
    public static void clearArrangementer() {
        arrangements.clear();
    }

    public static ArrayList<Arrangement> getArrangementer() {
        return arrangements;
    }

    public static void setArrangementer(ArrayList<Arrangement> arrangementer) {
        DataHandler.arrangements = arrangementer;
    }
}