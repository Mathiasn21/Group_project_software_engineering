package no.hiof.set.gruppe.data;
/*Guide
 * 1. Import Statements
 * 2. Constructors
 * 3. Getters
 * 4. Setters
 * 5. Overridden Methods
 * */


// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.hiof.set.gruppe.model.Arrangement;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This class handles data. Interacting with this object is done via the interface IDataHandler.
 * @author Gruppe4
 */
public class DataHandler implements IDataHandler {
    //static preLoader


    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    /**
     * A list with arrangements.
     */
    private static ArrayList<Arrangement> arrangements = new ArrayList<>();
    private static String arrangementFName = "arrangements.json";



    private static String readFromFile(String fName){
        String line;
        StringBuilder textFromFile = new StringBuilder();

        String filepath = "/files/" + fName;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("").getAbsolutePath() + filepath))) {
            while ((line = bufferedReader.readLine()) != null) {
                textFromFile.append(line);
            }
            return textFromFile.toString();
        }catch (IOException ioexc) {
            ioexc.printStackTrace();
        }
        return "";
    }
    private static void writeToFile(String str, String fName){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("").getAbsolutePath() + fName))) {
            bufferedWriter.write(str);
        } catch (IOException ioexc) {
            System.out.println(ioexc.getMessage());
        }
    }

    private static<T> List<T> listFromJson(Class<T[]> type, String jsonTextFromFile) {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        T[] arrangementArray = gson.fromJson(jsonTextFromFile, type);
        return (Arrays.asList(arrangementArray));
    }

    private static<T> String toJson(Class<T[]> type, T[] array){
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(array, type);
    }
    public static Collection<? extends Arrangement> getArrangementsData() {
        return listFromJson(Arrangement[].class, readFromFile(arrangementFName));
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

    @Override
    public void storeArrangementsData(Arrangement arrangement) {

    }

    @Override
    public void storeArrangementsData(Collection<Arrangement> arrangement) {
        writeToFile(toJson(Arrangement[].class, (Arrangement[]) arrangement.toArray()), arrangementFName);
    }

    /**
     * Adds arrangements to the arrangements list.
     * @param a
     */
    public static void addArrangementer(Arrangement a) {
        arrangements.add(a);
    }

    /**
     * Removes an arrangement from the arrangements list.
     * @param a
     */
    public static void removeArrangementer(Arrangement a) {
        arrangements.remove(a);
    }

}