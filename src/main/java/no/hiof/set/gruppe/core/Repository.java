package no.hiof.set.gruppe.core;
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
import no.hiof.set.gruppe.core.validations.AccessValidate;
import no.hiof.set.gruppe.core.validations.Validation;
import no.hiof.set.gruppe.data.HandleDataStorage;
import no.hiof.set.gruppe.data.IHandleData;
import no.hiof.set.gruppe.core.exceptions.*;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.ValidationResult;
import no.hiof.set.gruppe.model.constantInformation.DummyUsers;
import no.hiof.set.gruppe.model.user.ILoginInformation;
import no.hiof.set.gruppe.model.user.ProtoUser;
import no.hiof.set.gruppe.model.user.RawUser;
import no.hiof.set.gruppe.model.user.UserConnectedArrangement;
import no.hiof.set.gruppe.util.TypeClassMapToObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main class and logic for handling data, both storage and retrieval.
 * Interacting with this object is done via the interface IDataHandler.
 * @author Gruppe4
 */
public final class Repository {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private static List<Arrangement> listOfAllArrangements;
    private static List<Group> listOfAllGroups;
    private static List<UserConnectedArrangement> listOfAllUserConnectedArrangements;
    private static final IHandleData handleData = new HandleDataStorage();

    //Preload data.
    static{
        try{
            listOfAllArrangements = queryDataGivenType(Arrangement[].class);
            listOfAllUserConnectedArrangements = queryDataGivenType(UserConnectedArrangement[].class);
            listOfAllGroups = queryDataGivenType(Group[].class);

            TypeClassMapToObject.mutateObjectMapperList(
                    new TypeClassMapToLists(Arrangement[].class, listOfAllArrangements),
                    new TypeClassMapToLists(UserConnectedArrangement[].class, listOfAllUserConnectedArrangements),
                    new TypeClassMapToLists(Group[].class, listOfAllGroups)
            );

        }catch (IOException | DataFormatException e){
            try {ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_READING_DATA, e);}
            catch (IOException ex) {ex.printStackTrace();}
        }
    }

    // --------------------------------------------------//
    //                4.Private Handle Reading Data      //
    // --------------------------------------------------//
    private static void deleteUserConnectedArrangements(String ID){
        for (int i = 0; i < listOfAllUserConnectedArrangements.size(); i++){
            UserConnectedArrangement userArr = listOfAllUserConnectedArrangements.get(i);
            if(userArr.getID().equals(ID)){
                listOfAllUserConnectedArrangements.remove(userArr);
                i--;
            }
        }
    }

    private static <T> List<T> queryDataGivenType(Class<T[]> tClassArr) throws IOException, DataFormatException {
        return handleData.queryAllDataGivenType(tClassArr);
    }

    // --------------------------------------------------//
    //                4.Private Handle Saving Data       //
    // --------------------------------------------------//
    private static void storeArrangementsData() throws DataFormatException {
        handleData.storeDataGivenType(Arrangement[].class, listOfAllArrangements.toArray(Arrangement[]::new));
        storeUserArrangements();
    }

    private static void storeGroupData() throws DataFormatException {
        handleData.storeDataGivenType(Group[].class, listOfAllGroups.toArray(Group[]::new));
    }

    private static void storeUserArrangements() throws DataFormatException {
        handleData.storeDataGivenType(UserConnectedArrangement[].class, listOfAllUserConnectedArrangements.toArray(UserConnectedArrangement[]::new));
    }

    private static <T> void storeDataGivenType(Class<T[]> tClass) throws DataFormatException {
        //handleData.storeDataGivenType(tClass, TypeClassMapToObject.getCorrespondingMapperGivenType(tClass).getObject());
    }

    // --------------------------------------------------//
    //                4.Public Mutators                  //
    // --------------------------------------------------//
    /**
     * @param arrangement {@link Arrangement}
     * @param protoUser {@link ProtoUser}
     */
    public static void insertUserToArrangement(@NotNull Arrangement arrangement, @NotNull ProtoUser protoUser) throws DataFormatException {
        listOfAllUserConnectedArrangements.add(new UserConnectedArrangement(arrangement.getID(), protoUser.getName()));
        storeArrangementsData();
    }

    /**
     * @param arrangement {@link Arrangement}
     * @param protoUser {@link ProtoUser}
     * @throws IllegalDataAccess IllegalAccess{@link IllegalDataAccess}
     */
    public static void insertArrangement(Arrangement arrangement, @NotNull ProtoUser protoUser) throws IllegalDataAccess, DataFormatException {
        if(!AccessValidate.userCanCreateArrangement(protoUser))throw new IllegalDataAccess();

        listOfAllArrangements.add(arrangement);
        listOfAllUserConnectedArrangements.add(new UserConnectedArrangement(arrangement.getID(), protoUser.getName()));
        storeArrangementsData();
    }

    @Contract(pure = true)
    public static void insertNewUser(RawUser rawUser)throws UnableToRegisterUser{
        ValidationResult result = Validation.ofNewUser(rawUser);
        if(!result.IS_VALID)throw new UnableToRegisterUser(result);
    }

    public static void insertGroup(@NotNull Group group) throws DataFormatException {
        listOfAllGroups.add(group);
        storeGroupData();
    }

    public static <T> void mutateObject(T t)throws DataFormatException {
        if(t instanceof Arrangement){
            Arrangement thatArrangement = (Arrangement) t;
            listOfAllArrangements.removeIf((thisArrangement) -> thisArrangement.getID().equals(thatArrangement.getID()));
            listOfAllArrangements.add(thatArrangement);
            storeArrangementsData();

        }else if (t instanceof Group){
            Group thatGroup = (Group) t;
            listOfAllGroups.removeIf((thisGroup) -> thisGroup.getId().equals(thatGroup.getId()));
            listOfAllGroups.add(thatGroup);
            storeGroupData();
        }else{throw new DataFormatException();}
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
    public static List<Arrangement> queryAllArrangements(){ return new ArrayList<>(listOfAllArrangements); }

    /**
     * @param protoUser {@link ProtoUser}
     * @return {@link List}
     */
    @NotNull
    public static List<Arrangement> queryAllUserRelatedArrangements(@NotNull ProtoUser protoUser) {
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
    public static ProtoUser queryUserDetailsWith(@NotNull ILoginInformation loginInformation) throws InvalidLoginInformation {
        String userID = loginInformation.getUserID();
        String passHash = loginInformation.getPassHash();

        ProtoUser protoUser = ProtoUser.getUser(userID);
        if(protoUser == null || !protoUser.getName().equals(userID) || !protoUser.getPass().equals(passHash)) throw new InvalidLoginInformation();
        return protoUser;
    }

    @Contract(" -> new")
    @NotNull
    public static ArrayList<DummyUsers> queryAllUsers(){
        return new ArrayList<>(Arrays.asList(DummyUsers.values()));
    }

    @NotNull
    @Contract(value = " -> new")
    public static ArrayList<Group> queryAllGroups(){
       return new ArrayList<>(listOfAllGroups);
    }

    /**
     * This method interacts with an database to check if the address exists.
     * Temp solution is for it to return true when length is below 50.
     * @param streetAddress String
     * @return boolean
     */
    @Contract(pure = true)
    public static boolean queryAddress(@NotNull String streetAddress) {return !(streetAddress.length() < 50);}

    @Contract(pure = true)
    public static boolean queryEmailExists(String email) {return false;}
}