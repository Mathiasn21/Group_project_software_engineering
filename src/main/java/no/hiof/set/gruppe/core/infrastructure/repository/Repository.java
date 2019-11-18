package no.hiof.set.gruppe.core.infrastructure.repository;
/*Guide
 * 1. Import Statements
 * 2. Static Fields
 * 3. Private Query Data
 * 4. Private Data Handling
 * 5. Public Data Removal
 * 6. Public Query Data
 * */


// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import com.google.api.client.util.ArrayMap;
import no.hiof.set.gruppe.core.infrastructure.exceptions.*;
import no.hiof.set.gruppe.core.interfaces.*;
import no.hiof.set.gruppe.core.infrastructure.validations.AccessValidate;
import no.hiof.set.gruppe.core.infrastructure.validations.Validation;
import no.hiof.set.gruppe.data.HandleDataStorage;
import no.hiof.set.gruppe.core.infrastructure.factory.DataFactory;
import no.hiof.set.gruppe.core.infrastructure.factory.IFactory;
import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.entities.ValidationResult;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;
import no.hiof.set.gruppe.core.entities.user.RawUser;
import no.hiof.set.gruppe.core.entities.UserConnectedArrangement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Core Repository for interacting with data.
 * @author Gruppe4
 */
public final class Repository implements IRepository {

    // --------------------------------------------------//
    //                2.Static Fields                    //
    // --------------------------------------------------//
    private static final IHandleData handleData = new HandleDataStorage();
    private static final IFactory factory = new DataFactory();
    private static final Map<Class<? extends IBaseEntity>, List<? extends IBaseEntity>> objectMappedToList = new ArrayMap<>();
    private static final Map<Class<? extends IBaseEntity>, Class<? extends EntityConnectedToUser>> baseEntityMappedToEntity = new ArrayMap<>();

    //Setting up data related to each class, by mapping class to a list containing related data.
    static{
        try{
            Reflections reflections = new Reflections("no.hiof.set.gruppe.core.entities");
            Set<Class<? extends IBaseEntity>> clazzes = reflections.getSubTypesOf(IBaseEntity.class);
            List<Class<? extends IBaseEntity>> listOfDataClasses = new ArrayList<>(clazzes);

            for (Class<? extends IBaseEntity> aClass : listOfDataClasses) {
                List<? extends IBaseEntity> list = queryDataGivenType(aClass);
                if(aClass.isEnum())list = Arrays.asList(aClass.getEnumConstants());
                if (list.size() == 0) {
                    if (!factory.canGenerateFromClass(aClass)) continue;
                    list = (queryGenDataGivenType(aClass));
                }
                objectMappedToList.put(aClass, list);
            }
            baseEntityMappedToEntity.put(Arrangement.class, UserConnectedArrangement.class);

        }catch (IOException | DataFormatException e){
            ErrorExceptionHandler handle = e instanceof DataFormatException ? ErrorExceptionHandler.ERROR_WRONG_DATA_OBJECT : ErrorExceptionHandler.ERROR_READING_DATA;
            try {ErrorExceptionHandler.createLogWithDetails(handle, e);}
            catch (IOException ex) {ex.printStackTrace();}
        }
    }


    // --------------------------------------------------//
    //                4.Private Query Data               //
    // --------------------------------------------------//
    /**
     * @param tClassArr T[].class
     * @param <T> T
     * @return List T
     * @throws IOException IOException
     * @throws DataFormatException DataFormatException
     */
    private static  <T> List<T> queryDataGivenType(Class<T> tClassArr) throws IOException, DataFormatException {
        return handleData.queryAllDataGivenType(tClassArr);
    }

    /**
     * @param tClassArr T[].class
     * @param <T> T
     * @return List T
     */
    private static  <T> List<T> queryGenDataGivenType(Class<T> tClassArr) {
        return factory.generateManyTypes(tClassArr, (int) (Math.random()*6));
    }

    @SuppressWarnings("unchecked")//objectMapper contains explicitly only List<IBaseEntity>
    private <T extends IBaseEntity> List<IBaseEntity> getList(Class<T> thatBaseEntity) {
        return (List<IBaseEntity>) objectMappedToList.get(thatBaseEntity);
    }

    @SuppressWarnings("unchecked")//Safe due to that the mapping is explicitly stated indirectly through the mappers
    private <T extends IBaseEntity> List<EntityConnectedToUser> getDataConnectedToUsersList(Class<T> baseEntity) {
        Class<? extends EntityConnectedToUser> entity = baseEntityMappedToEntity.get(baseEntity);
        return (List<EntityConnectedToUser>) objectMappedToList.get(objectMappedToList.get(entity) == null ? baseEntity : entity);
    }


    // --------------------------------------------------//
    //                4.Private Data Handling            //
    // --------------------------------------------------//
    private <T extends IBaseEntity> void storeData(Class<T> aClass) throws DataFormatException {
        List<? extends IBaseEntity> list = getList(aClass);
        handleData.storeDataGivenType(aClass, list);
    }

    private <T extends IBaseEntity> void deleteAllEntityConnectedToUserData(String ID, Class<T> tClass) throws DataFormatException {
        Class<? extends EntityConnectedToUser> entity = baseEntityMappedToEntity.get(tClass);
        List<EntityConnectedToUser> dataConnectedToUsers = getDataConnectedToUsersList(entity);
        if(dataConnectedToUsers == null) return;

        for (int i = 0; i < dataConnectedToUsers.size(); i++){
            EntityConnectedToUser data = dataConnectedToUsers.get(i);
            if(data.getID().equals(ID)){
                dataConnectedToUsers.remove(data);
                i--;
            }
        }
        storeData(entity);
    }


    // --------------------------------------------------//
    //                4.Public Insert Queries            //
    // --------------------------------------------------//
    @Override
    public <T extends IBaseEntity, E extends IUser> void insertData(T iBaseEntity, E user) throws IllegalDataAccess, DataFormatException {
        if(!AccessValidate.ThatUserCanCreateNewBaseEntity(user, iBaseEntity.getClass()))throw new IllegalDataAccess();
        (getList(iBaseEntity.getClass())).add(iBaseEntity);

        Class<? extends EntityConnectedToUser> relation = baseEntityMappedToEntity.get(iBaseEntity.getClass());
        storeData(iBaseEntity.getClass());
        if(relation != null){
            insertUserRelationToData(iBaseEntity, user);
            storeData(relation);
        }
    }

    @Override
    public <T extends IBaseEntity, E extends IUser> void insertUserRelationToData(T t, E user) throws DataFormatException {
        Class<? extends EntityConnectedToUser> connection = baseEntityMappedToEntity.get(t.getClass());
        List<EntityConnectedToUser> listOfConnections = getDataConnectedToUsersList(t.getClass());
        Constructor<? extends EntityConnectedToUser> data;
        try {
            data = connection.getDeclaredConstructor(String.class, String.class);
            listOfConnections.add(data.newInstance(t.getID(), user.getName()));
            storeData(connection);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            try {ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_FATAL, e);
            } catch (IOException ex) { ex.printStackTrace();}
        }
    }

    @Contract(pure = true)
    @Override
    public void insertNewUser(RawUser rawUser)throws UnableToRegisterUser{
        ValidationResult result = Validation.ofNewUser(rawUser);
        if(!result.IS_VALID)throw new UnableToRegisterUser(result);
    }

    @Override
    public <T extends IBaseEntity> void mutateData(T thatBaseEntity) throws DataFormatException {
        List<IBaseEntity> entityList = getList(thatBaseEntity.getClass());
        entityList.removeIf((thisBaseEntity) -> thisBaseEntity.getID().equals(thatBaseEntity.getID()) || thisBaseEntity.equals(thatBaseEntity));
        entityList.add(thatBaseEntity);
        storeData(thatBaseEntity.getClass());
    }


    // --------------------------------------------------//
    //                5.Public Data Removal              //
    // --------------------------------------------------//
    @Override
    public <T extends IBaseEntity> void deleteData(T thatBaseEntity, ProtoUser user) throws IllegalDataAccess, DataFormatException {
        if(!AccessValidate.ThatUserCanModifyBaseEntity(thatBaseEntity, user))throw new IllegalDataAccess();
        List<IBaseEntity> list = getList(thatBaseEntity.getClass());
        list.removeIf((thisBaseEntity) -> thisBaseEntity.getID().equals(thatBaseEntity.getID()) || thisBaseEntity.equals(thatBaseEntity));
        deleteAllEntityConnectedToUserData(thatBaseEntity.getID(), thatBaseEntity.getClass());
        storeData(thatBaseEntity.getClass());
    }

    @Override
    public <T extends IBaseEntity, E extends IUser> void deleteUserConnectionToData(T thatO, E user) throws DataFormatException {
        List<EntityConnectedToUser> list = getDataConnectedToUsersList(thatO.getClass());
        if(list == null)throw new DataFormatException();
        list.removeIf((thisO) -> thisO.getID().equals(thatO.getID()) && thisO.getUSERNAME().equals(user.getName()));
        storeData(baseEntityMappedToEntity.get(thatO.getClass()));
    }


    // --------------------------------------------------//
    //                6.Public Query Data                //
    // --------------------------------------------------//
    @Override
    @SuppressWarnings("unchecked")//The only possible lists that it returns are the ones already defined
    public <T extends IBaseEntity> List<T> queryAllDataOfGivenType(Class<T> aClass) {
        return new ArrayList<>((List<T>) objectMappedToList.get(aClass));
    }

    @Override
    @SuppressWarnings("unchecked")//only one possible return type here, as this is mapped from the start
    public <T extends IBaseEntity, E extends IUser> List<T> queryAllEntityConnectedToUserData(Class<T> aClass, E user) {
        List<EntityConnectedToUser> dataConnectedToUsers = getDataConnectedToUsersList(aClass);
        if(dataConnectedToUsers == null)return new ArrayList<>();

        List<T> result = new ArrayList<>();
        List<IBaseEntity> baseEntityList = getList(aClass);
        String userName = user.getName();

        outer:for(IBaseEntity baseEntity : baseEntityList){
            String baseID = baseEntity.getID();
            for(EntityConnectedToUser data : dataConnectedToUsers)
                if (userName.equals(data.getUSERNAME()) && baseID.equals(data.getID())) {
                    result.add((T) baseEntity);
                    continue outer;
                }
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")//only one possible return type here, as this is mapped from the start
    public <T extends IBaseEntity> T queryDataWithID(String ID, Class<T> aClass) {
        List<IBaseEntity> list = getList(aClass);
        for (IBaseEntity entity : list)if (entity.getID().equals(ID)) return (T) entity;
        return null;
    }

    @NotNull
    @Override
    public ProtoUser queryUserDetailsWith(@NotNull ILoginInformation loginInformation) throws InvalidLoginInformation {
        String userID = loginInformation.getUserID();
        String passHash = loginInformation.getPassHash();

        ProtoUser protoUser = ProtoUser.getUser(userID);
        if(protoUser == null || !protoUser.getName().equals(userID) || !protoUser.getPass().equals(passHash)) throw new InvalidLoginInformation();
        return protoUser;
    }

    /**
     * This method interacts with an database to check if the address exists.
     * Temp solution is for it to return true.
     * @param streetAddress String
     * @return boolean
     */
    @Contract(pure = true)
    @Override
    public boolean queryAddress(@NotNull String streetAddress) {return false;}

    @Contract(pure = true)
    @Override
    public boolean queryEmailExists(String email) {return false;}
}