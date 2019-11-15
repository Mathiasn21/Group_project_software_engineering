package no.hiof.set.gruppe.core;

public interface ICommandRepository {
    <T extends Repository> void insertData(T t);
}
