package no.hiof.set.gruppe.core;

import no.hiof.set.gruppe.IBaseEntity;
import java.util.List;

public interface IRepository<T extends IBaseEntity> {
    List<T> queryAllDataOfGivenType(Class<T> tClass);
    void queryDataWithID(String str, Class<T> tClass);
    void insertData(T t);
    void deleteData(T t);
    void mutateData(T t);
}
