package by.koshko.crimes.service;

import java.util.List;
import java.util.stream.Stream;

public interface RequestDataMapper<T> {

    List<T> map(Stream<String> parameters);
}
