package no.hiof.set.gruppe.data;
/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Handle Json and File saving
 * 4. Public Mutators
 * 5. Public Data Removal
 * 6. Public Getters
 * */


// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.hiof.set.gruppe.core.validations.AccessValidate;
import no.hiof.set.gruppe.core.validations.Validation;
import no.hiof.set.gruppe.exceptions.*;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.ValidationResult;
import no.hiof.set.gruppe.model.constantInformation.DummyUsers;
import no.hiof.set.gruppe.model.user.ILoginInformation;
import no.hiof.set.gruppe.model.user.ProtoUser;
import no.hiof.set.gruppe.model.user.RawUser;
import no.hiof.set.gruppe.model.user.UserConnectedArrangement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main class and logic for handling data, both storage and retrieval.
 * Interacting with this object is done via the interface IDataHandler.
 * @author Gruppe4
 */
public class Repository {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private static final String arrangementFName = "arrangements.json";
    private static final String userHasArrangements = "userHasArrangements.json";
    private static final String groupsFName = "groups.json";
    private static List<Arrangement> listOfAllArrangements;
    private static List<Group> listOfAllGroups;
    private static List<UserConnectedArrangement> listOfAllUserConnectedArrangements;
    private static final ObjectMapToFiles[] objectMapToFilesArr = {
            new ObjectMapToFiles<>(Arrangement.class, arrangementFName),
            new ObjectMapToFiles<>(Group.class, groupsFName),
            new ObjectMapToFiles<>(UserConnectedArrangement.class, userHasArrangements)
    };

    //Preloads data.
    static{
        try{
            listOfAllArrangements = readDataGivenTypeArr(Arrangement[].class);;
            listOfAllUserConnectedArrangements = readDataGivenTypeArr(UserConnectedArrangement[].class);
            listOfAllGroups = readDataGivenTypeArr(Group[].class);

        }catch (IOException | DataFormatException e){
            try {ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_READING_DATA, e);}
            catch (IOException ex) {ex.printStackTrace();}
        }
    }

    // --------------------------------------------------//
    //                3.Handle Json and File saving      //
    // --------------------------------------------------//
    /**
     * Standard reading from a file. Utilizes a relative path given a filename.extension.
     * Files must exist in the top level directory.
     * @param fName String
     * @throws IOException IOException {@link IOException}
     * @return String
     */
    @NotNull
    private static String readFromFile(String fName) throws IOException {
        String line;
        StringBuilder textFromFile = new StringBuilder();

        String filepath = "/files/" + fName;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("").getAbsolutePath() + filepath), StandardCharsets.UTF_8));
            while ((line = bufferedReader.readLine()) != null) {
                textFromFile.append(line);
            }
        return textFromFile.toString();
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
     * @return {@link List}
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

    private static void deleteUserConnectedArrangements(String ID){
        for (int i = 0; i < listOfAllUserConnectedArrangements.size(); i++){
            UserConnectedArrangement userArr = listOfAllUserConnectedArrangements.get(i);
            if(userArr.getID().equals(ID)){
                listOfAllUserConnectedArrangements.remove(userArr);
                i--;
            }
        }
    }

    // --------------------------------------------------//
    //                4.Private Handle Reading Data      //
    // --------------------------------------------------//
    private static <T> List<T> readDataGivenTypeArr(Class<T[]> tClassArr) throws IOException, DataFormatException {
        String jsonFromFile = readFromFile(ObjectMapToFiles.getCorrespondingMapperGivenType(tClassArr).fileName);
        return new ArrayList<>(listFromJson(tClassArr, jsonFromFile));
    }


    // --------------------------------------------------//
    //                4.Private Handle Saving Data       //
    // --------------------------------------------------//
    /**
     * Stores all arrangements and their user connection
     */
    private static void storeArrangementsData() throws DataFormatException {
        storeDataUsing(Arrangement[].class, listOfAllArrangements.toArray(Arrangement[]::new));
        storeUserArrangements();
    }

    private static void storeGroupData() throws DataFormatException {
        storeDataUsing(Group[].class, listOfAllGroups.toArray(Group[]::new));
    }

    /**
     * Stores all arrangements in buffer to file.json. Called
     * after every modification of said buffer.
     */
    private static void storeUserArrangements() throws DataFormatException {
        storeDataUsing(UserConnectedArrangement[].class,  listOfAllUserConnectedArrangements.toArray(UserConnectedArrangement[]::new));
    }

    private static <T> void storeDataUsing(Class<T[]> tClass, T[] tArray) throws DataFormatException {
        writeToFile(toJson(tClass,  tArray), ObjectMapToFiles.getCorrespondingMapperGivenType(tClass).fileName);
    }


    // --------------------------------------------------//
    //                4.Public Mutators                  //
    // --------------------------------------------------//
    /**
     * @param arrangement {@link Arrangement}
     * @param protoUser {@link ProtoUser}
     */
    public static void addUserToArrangement(@NotNull Arrangement arrangement, @NotNull ProtoUser protoUser) throws DataFormatException {
        listOfAllUserConnectedArrangements.add(new UserConnectedArrangement(arrangement.getID(), protoUser.getName()));
        storeArrangementsData();
    }

    /**
     * @param arrangement {@link Arrangement}
     * @param protoUser {@link ProtoUser}
     * @throws IllegalDataAccess IllegalAccess{@link IllegalDataAccess}
     */
    public static void addArrangement(Arrangement arrangement, @NotNull ProtoUser protoUser) throws IllegalDataAccess, DataFormatException {
        if(!AccessValidate.userCanCreateArrangement(protoUser))throw new IllegalDataAccess();

        listOfAllArrangements.add(arrangement);
        listOfAllUserConnectedArrangements.add(new UserConnectedArrangement(arrangement.getID(), protoUser.getName()));
        storeArrangementsData();
    }

    @Contract(pure = true)
    public static void addNewUser(RawUser rawUser)throws UnableToRegisterUser{
        ValidationResult result = Validation.ofNewUser(rawUser);
        if(!result.IS_VALID)throw new UnableToRegisterUser(result);
    }

    public static void addGroup(@NotNull Group group) throws DataFormatException {
        listOfAllGroups.add(group);
        storeGroupData();
    }

    public static void mutateObject(Object object)throws DataFormatException {
        if(object instanceof Arrangement){
            Arrangement thatArrangement = (Arrangement) object;
            listOfAllArrangements.removeIf((thisArrangement) -> thisArrangement.getID().equals(thatArrangement.getID()));
            listOfAllArrangements.add(thatArrangement);

        }else if (object instanceof Group){
            Group thatGroup = (Group) object;
            listOfAllGroups.removeIf((thisGroup) -> thisGroup.getId() == thatGroup.getId());
            listOfAllGroups.add(thatGroup);
        }else{throw new DataFormatException();}
        storeArrangementsData();
    }

    // --------------------------------------------------//
    //                5.Public Data Removal              //
    // --------------------------------------------------//
    /**
     * @param arrangement {@link Arrangement}
     * @param protoUser {@link ProtoUser}
     * @throws IllegalDataAccess illegalAccess{@link IllegalDataAccess}
     */
    public static void deleteArrangement(Arrangement arrangement, ProtoUser protoUser) throws IllegalDataAccess, DataFormatException {
        if(!AccessValidate.userCanModifyArrangement(arrangement, protoUser))throw new IllegalDataAccess();

        listOfAllArrangements.remove(arrangement);
        deleteUserConnectedArrangements(arrangement.getID());
        storeArrangementsData();
    }

    public static void deleteGroup(Group group) throws DataFormatException {
        listOfAllGroups.remove(group);
        storeGroupData();
    }

    /**
     * @param arrangement {@link Arrangement}
     * @param protoUser {@link ProtoUser}
     */
    public static void deleteUserFromArrangement(@NotNull Arrangement arrangement,@NotNull ProtoUser protoUser) throws DataFormatException {
        for(int i = 0; i < listOfAllUserConnectedArrangements.size(); i++){
            UserConnectedArrangement userArrangement = listOfAllUserConnectedArrangements.get(i);
            if(userArrangement.getID().equals(arrangement.getID()) && userArrangement.getUSERNAME().equals(protoUser.getName())){
                listOfAllUserConnectedArrangements.remove(userArrangement);
                break;
            }
        }
        storeArrangementsData();
    }

    // --------------------------------------------------//
    //                6.Public Getters                   //
    // --------------------------------------------------//
    /**
     * @return {@link List}
     */
    @NotNull
    @Contract(" -> new")
    public static List<Arrangement> getArrangementsData(){ return new ArrayList<>(listOfAllArrangements); }

    /**
     * @param protoUser {@link ProtoUser}
     * @return {@link List}
     */
    @NotNull
    public static List<Arrangement> getUserArrangements(@NotNull ProtoUser protoUser) {
        List<Arrangement> result = new ArrayList<>();
        String userName = protoUser.getName();

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

    @NotNull
    public static ProtoUser getUserDetails(@NotNull ILoginInformation loginInformation) throws InvalidLoginInformation {
        String userID = loginInformation.getUserID();
        String passHash = loginInformation.getPassHash();

        ProtoUser protoUser = ProtoUser.getUser(userID);
        if(protoUser == null || !protoUser.getName().equals(userID) || !protoUser.getPass().equals(passHash)) throw new InvalidLoginInformation();
        return protoUser;
    }

    @Contract(" -> new")
    @NotNull
    public static ArrayList<DummyUsers> getAllUsers(){
        return new ArrayList<>(Arrays.asList(DummyUsers.values()));
    }

    @NotNull
    @Contract(value = " -> new")
    public static ArrayList<Group>getAllGroups(){
       return new ArrayList<>(listOfAllGroups);
    }

    /**
     * This method interacts with an database to check if the address exists.
     * Temp solution is for it to return true when length is below 50.
     * @param streetAddress String
     * @return boolean
     */
    @Contract(pure = true)
    public static boolean addressExists(@NotNull String streetAddress) {return !(streetAddress.length() < 50);}

    @Contract(pure = true)
    public static boolean emailExists(String email) {return false;}


    private static class ObjectMapToFiles<T> {
        private final Class<T> tClass;
        private final String fileName;

        ObjectMapToFiles(Class<T> tClass, String fileName){
            this.fileName = fileName;
            this.tClass = tClass;
        }

        Class<T> getTypeClass() {return tClass;}

        static <T> ObjectMapToFiles getCorrespondingMapperGivenType(Class<T[]> t) throws DataFormatException {
            for(ObjectMapToFiles objectMapper : Repository.objectMapToFilesArr){
                if(objectMapper.getTypeClass() == t.getComponentType()){
                    return objectMapper;
                }
            }
            throw new DataFormatException();
        }
    }
}