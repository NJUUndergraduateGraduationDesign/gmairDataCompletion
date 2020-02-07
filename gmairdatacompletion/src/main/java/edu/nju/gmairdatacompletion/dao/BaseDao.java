package edu.nju.gmairdatacompletion.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {

    Serializable add(Object obj);

    <T> boolean delete(Class<T> entityClass, Serializable id);

    boolean update(Object obj);

    <T> T searchById(Class<T> entityClass, Serializable id);

    <T> List<T> searchByHql(String hql, Serializable... args);

    boolean executeHql(String hql, Serializable... args);
}
