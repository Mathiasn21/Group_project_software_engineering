package no.hiof.set.gruppe.core.interfaces;

import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.exceptions.InvalidLoginInformation;
import no.hiof.set.gruppe.core.exceptions.UnableToRegisterUser;
import no.hiof.set.gruppe.model.user.ProtoUser;
import no.hiof.set.gruppe.model.user.RawUser;

import java.util.List;

public interface IRepository {
    ProtoUser queryUserDetailsWith(ILoginInformation loginInformation) throws InvalidLoginInformation;
    boolean queryAddress(String streetAddress);
    boolean queryEmailExists(String email);
    void insertNewUser(RawUser rawUser)throws UnableToRegisterUser;

    <T extends IBaseEntity, E extends IUser> void deleteUserConnectionToData(T t, E user) throws DataFormatException;
    <T extends IBaseEntity> List<T> queryAllDataOfGivenType(Class<T> aClass);

    <T extends IBaseEntity, E extends IUser> List<T> queryAllEntityConnectedToUserData(Class<T> tClass, E user);
    <T extends IBaseEntity> T queryDataWithID(String ID, Class<T> tClass);

    <T extends IBaseEntity, E extends IUser> void insertData(T iBaseEntity, E user) throws IllegalDataAccess, DataFormatException;

    <T extends IBaseEntity, E extends IUser> void insertUserRelationToData(T t, E user) throws DataFormatException;

    <T extends IBaseEntity> void mutateData(T t) throws DataFormatException;
    <T extends IBaseEntity> void deleteData(T t, ProtoUser user) throws IllegalDataAccess, DataFormatException;


}