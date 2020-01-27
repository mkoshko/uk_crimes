package by.koshko.crimes.service;

public interface RequestDataMapper<T> {

    T map(String parameters) throws MappingException;
}
