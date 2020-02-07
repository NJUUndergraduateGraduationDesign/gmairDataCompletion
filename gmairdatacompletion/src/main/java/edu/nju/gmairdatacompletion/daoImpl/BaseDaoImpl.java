package edu.nju.gmairdatacompletion.daoImpl;

import edu.nju.gmairdatacompletion.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BaseDaoImpl implements BaseDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
    }

    @Override
    public Serializable add(Object obj) {
        Serializable res = null;
        Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            res = session.save(obj);
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public <T> boolean delete(Class<T> entityClass, Serializable id) {
        boolean res = false;
        Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            Object obj = session.get(entityClass, id);
            session.delete(obj);
            transaction.commit();
            res = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(Object obj) {
        boolean res = false;
        Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.update(obj);
            transaction.commit();
            res = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public <T> T searchById(Class<T> entityClass, Serializable id) {
        T res = null;
        Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            res = session.get(entityClass, id);
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public <T> List<T> searchByHql(String hql, Serializable... args) {
        ArrayList<T> res = null;
        Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            int i = 1;
            for (Serializable arg : args) {
                query.setParameter(i, arg);
                i++;
            }
            List queryRes = query.list();
            if(queryRes != null && queryRes.size() > 0) {
                res = (ArrayList<T>) queryRes;
            }
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean executeHql(String hql, Serializable... args) {
        boolean res = false;
        Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            int i = 1;
            for (Serializable arg : args) {
                query.setParameter(i, arg);
                i++;
            }
            if(query.executeUpdate() >= 0) {
                res = true;
            }
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
