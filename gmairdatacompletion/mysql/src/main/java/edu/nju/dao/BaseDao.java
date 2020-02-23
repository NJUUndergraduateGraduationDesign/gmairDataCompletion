package edu.nju.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2019/9/30 13:40
 * @description：common dao interface
 */

public interface BaseDao<T> {
    Serializable add(T t);

    void delete(T t);

    void deleteById(Serializable id);

    T get(Serializable id);

    void update(T t);

    List<T> getAll();

    T getUniqueResultByHQL(String hql, Object... values);

    List<T> getListByHQL(String hql, Object... values);
}