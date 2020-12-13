package org.elwazy.twitter_clone.dao;

import java.util.List;

public interface GenericCrud<T> {
    List<T> getAll();
    T getById(int id);
    int insert(T t);
    int update(T t);
    int delete(int id);
}
