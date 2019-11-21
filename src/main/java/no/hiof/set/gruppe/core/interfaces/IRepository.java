package no.hiof.set.gruppe.core.interfaces;

import no.hiof.set.gruppe.core.infrastructure.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.infrastructure.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.infrastructure.exceptions.InvalidLoginInformation;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;

import java.util.List;

public interface IRepository {
    ProtoUser queryUserDetailsWith(ILoginInformation loginInformation) throws InvalidLoginInformation;

    <T extends IBaseEntity, E extends IUser> void deleteUserRelationToData(T t, E user) throws DataFormatException;
    <T extends IBaseEntity> List<T> queryAllDataOfGivenType(Class<T> aClass);

    <T extends IBaseEntity, E extends IUser> List<T> queryAllEntityConnectedToUserData(Class<T> tClass, E user);
    <T extends IBaseEntity> T queryDataWithID(String ID, Class<T> tClass);

    <T extends IBaseEntity, E extends IUser> void insertData(T iBaseEntity, E user) throws IllegalDataAccess, DataFormatException;
    <T extends IBaseEntity, E extends IUser> void insertUserRelationToData(T t, E user) throws DataFormatException;

    <T extends IBaseEntity> void mutateData(T t) throws DataFormatException;
    <T extends IBaseEntity> void deleteData(T t, ProtoUser user) throws IllegalDataAccess, DataFormatException;


}