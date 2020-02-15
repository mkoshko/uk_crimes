package by.koshko.crimes.dao;

import java.util.List;

public interface QueryDao<T> {

    List<T> get(String sql);
}
