package no.hiof.set.gruppe.core;

import no.hiof.set.gruppe.model.user.IUser;
import java.util.List;

public interface IRepository {
    <T extends IBaseEntity> List<T> queryAllDataOfGivenType(Class<T> aClass);
    <T extends IBaseEntity, E extends IUser> List<T> queryAllEntityConnectedToUserData(Class<T> tClass, E user);
    <T extends IBaseEntity> T queryDataWithID(String ID, Class<T> tClass);
    <T extends IBaseEntity> void insertData(T t);
    <T extends IBaseEntity> void deleteData(T t);
    <T extends IBaseEntity> void mutateData(T t);
}