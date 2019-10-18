package no.hiof.set.gruppe.data;
/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Private Static Methods
 * 4. Public Methods
 * */


// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.User;
import no.hiof.set.gruppe.model.UserConnectedArrangement;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main class and logic for handling data, both storage and retrieval.
 * Interacting with this object is done via the interface IDataHandler.
 * @author Gruppe4
 */
public class DataHandler implements IDataHandler {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private static String arrangementFName = "arrangements.json";
    private static String userHasArrangements = "userHasArrangements.json";

    // --------------------------------------------------//
    //                3.Private Static Methods           //
    // --------------------------------------------------//

    /**
     * Standard reading from a file. Utilizes a relative path given a filename.extension.
     * Files must exist in the top level directory.
     * @param fName String
     * @return String
     */
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

    /**
     * Standard method for writing data to a given file.
     * This methods does not append but overwrites!
     * Utilizes a relative path for top level directory plus a filename.extension
     * @param str String
     * @param fName String
     */
    private static void writeToFile(String str, String fName){
        String filepath = "/files/" + fName;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("").getAbsolutePath() + filepath))) {
            bufferedWriter.write(str);
        } catch (IOException ioexc) {
            ioexc.printStackTrace();
        }
    }

    /**
     * This method retrieves a list, given a type Class and a json string.
     * @param type T[]
     * @param jsonTextFromFile String
     * @param <T> T
     * @return {@link List}<T>
     */
    private static<T> List<T> listFromJson(Class<T[]> type, String jsonTextFromFile) {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        T[] arrangementArray = gson.fromJson(jsonTextFromFile, type);
        return (Arrays.asList(arrangementArray));
    }

    /**
     * Utilizes parametrization combined with generics, in order to
     * convert a given T[] object and its specified Class template to json format.
     * @param type ClassT[]
     * @param array T[]
     * @param <T> T
     * @return String
     */
    private static<T> String toJson(Class<T[]> type, T[] array){
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(array, type);
    }


    // --------------------------------------------------//
    //                4.Public Methods                   //
    // --------------------------------------------------//

    /**
     * Grabs the arrangement data from a file and converts
     * those into a usable collection of arrangements.
     * @return {@link List} ? extends {@link Arrangement}
     */
    public static List<Arrangement> getArrangementsData() {
        return listFromJson(Arrangement[].class, readFromFile(arrangementFName));
    }

    public static List<Arrangement> getUserArrangements(User user) {
        String jsonFromFile = readFromFile(userHasArrangements);
        List<UserConnectedArrangement> userArrangementColl = listFromJson(UserConnectedArrangement[].class, jsonFromFile);

        List<Arrangement> result = new ArrayList<>();
        List<Arrangement> allArrangements = getArrangementsData();
        String userName = user.getName();

        outer:for(Arrangement arrangement : allArrangements){
            int arrID = arrangement.getID();
            for(UserConnectedArrangement userArrangement : userArrangementColl){
                if(userName.equals(userArrangement.getUSERNAME()) && arrID == userArrangement.getID()){
                    result.add(arrangement);
                    continue outer;
                }
            }
        }
        return result;
    }

    private static void storeUserArrangements(List<UserConnectedArrangement> colletionOfData, User user){
        writeToFile(toJson(UserConnectedArrangement[].class,  colletionOfData.toArray(UserConnectedArrangement[]::new)), userHasArrangements);
    }

    /**
     * Converts a given collection into json given a template,
     * and the stores that string to a file.
     * @param arrangements {@link List}<{@link Arrangement}>
     */
    @Override
    public void storeArrangementsData(List<Arrangement> arrangements, User user) {
        List<Arrangement> oldArrData = new ArrayList<>(getArrangementsData());
        for(Arrangement oldArr : oldArrData){
            for(Arrangement newArr : arrangements){
                if(oldArr.getID() == newArr.getID()){
                    arrangements.remove(newArr);
                    break;
                }
            }
        }
        oldArrData.addAll(arrangements);
        writeToFile(toJson(Arrangement[].class, oldArrData.toArray(Arrangement[]::new)), arrangementFName);
    }
}