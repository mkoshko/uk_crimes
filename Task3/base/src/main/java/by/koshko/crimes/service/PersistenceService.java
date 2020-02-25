package by.koshko.crimes.service;

public interface PersistenceService<T> {

    void save(T model);
}
