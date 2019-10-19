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
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

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
    private static List<Arrangement> listOfAllArrangements;
    private static List<UserConnectedArrangement> listOfAllUserConnectedArrangements;

    //Preloads the data.
    static{
        listOfAllArrangements = readArrangementsData();
        listOfAllUserConnectedArrangements = getUserConnectedArrangements();
    }

    // --------------------------------------------------//
    //                3.Private Static Methods           //
    // --------------------------------------------------//

    /**
     * Standard reading from a file. Utilizes a relative path given a filename.extension.
     * Files must exist in the top level directory.
     * @param fName String
     * @return String
     */
    @NotNull
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
    @NotNull
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

    @NotNull
    private static List<UserConnectedArrangement>generateUserConnectedArrangements(@NotNull List<Arrangement> arrList, User user){
        List<UserConnectedArrangement> result = new ArrayList<>();
        for(Arrangement arr : arrList){
            result.add(new UserConnectedArrangement(arr.getID(), user.getName()));
        }
        return result;
    }

    // --------------------------------------------------//
    //                4.Public Methods                   //
    // --------------------------------------------------//
    public static void deleteArrangement(Arrangement arrangement){
        listOfAllArrangements.remove(arrangement);
    }

    public static List<Arrangement> getArrangementsData(){
        return listOfAllArrangements;
    }

    public static void addArrangement(Arrangement arrangement, User user){
        listOfAllArrangements.add(arrangement);
        listOfAllUserConnectedArrangements.add(new UserConnectedArrangement(arrangement.getID(), user.getName()));
    }

    /**
     * Grabs the arrangement data from a file and converts
     * those into a usable collection of arrangements.
     * @return {@link List} ? extends {@link Arrangement}
     */
    private static List<Arrangement> readArrangementsData() {
        return listFromJson(Arrangement[].class, readFromFile(arrangementFName));
    }

    public static List<Arrangement> getUserArrangements(User user) {
        List<Arrangement> result = new ArrayList<>();
        String userName = user.getName();

        outer:for(Arrangement arrangement : listOfAllArrangements){
            String arrID = arrangement.getID();
            for(UserConnectedArrangement userArrangement : listOfAllUserConnectedArrangements){
                if(userName.equals(userArrangement.getUSERNAME()) && arrID.equals(userArrangement.getID())){
                    result.add(arrangement);
                    continue outer;
                }
            }
        }
        return result;
    }

    private static List<UserConnectedArrangement> getUserConnectedArrangements() {
        String jsonFromFile = readFromFile(userHasArrangements);
        return listFromJson(UserConnectedArrangement[].class, jsonFromFile);
    }

    private static void storeUserArrangements(List<UserConnectedArrangement> listOfData){
        List<UserConnectedArrangement> allUserConnData = new ArrayList<>(getUserConnectedArrangements());
        for(UserConnectedArrangement userArr : listOfData){
            for (UserConnectedArrangement userArrToComp : allUserConnData){
                if(userArr.equals(userArrToComp)){
                    allUserConnData.remove(userArrToComp);
                    break;
                }
            }
        }
        listOfData.addAll(allUserConnData);
        writeToFile(toJson(UserConnectedArrangement[].class,  listOfData.toArray(UserConnectedArrangement[]::new)), userHasArrangements);
    }

    /**
     * Converts a given list into json given a template,
     * and the stores that string to a file.
     * @param arrangements {@link List}<{@link Arrangement}>
     */
    @Override
    public void storeArrangementsData(List<Arrangement> arrangements, User user) {
        List<Arrangement> oldArrData = new ArrayList<>(getArrangementsData());
        for(int i = 0; i < arrangements.size(); i++){
            Arrangement newArr = arrangements.get(i);
            for(Arrangement oldArr : oldArrData){
                if(oldArr.getID().equals(newArr.getID()) ){
                    oldArrData.remove(oldArr);
                    break;
                }
            }
            if(newArr.getDeleted()){
                arrangements.remove(newArr);
                i--;
            }
        }
        oldArrData.addAll(arrangements);
        writeToFile(toJson(Arrangement[].class, oldArrData.toArray(Arrangement[]::new)), arrangementFName);
        storeUserArrangements(generateUserConnectedArrangements(arrangements, user));
    }
}