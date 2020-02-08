package edu.nju.gmairdatacompletion.daoImpl;

import edu.nju.gmairdatacompletion.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BaseDaoImpl implements BaseDao {

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public Serializable add(Object obj) {
        Serializable res = null;
        try (Session session = getSession()) {
            res = session.save(obj);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public <T> boolean delete(Class<T> entityClass, Serializable id) {
        boolean res = false;
        try (Session session = getSession()) {
            Object obj = session.get(entityClass, id);
            session.delete(obj);
            res = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(Object obj) {
        boolean res = false;
        try (Session session = getSession()) {
            session.update(obj);
            res = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public <T> T searchById(Class<T> entityClass, Serializable id) {
        T res = null;
        try (Session session = getSession()) {
            res = session.get(entityClass, id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public <T> List<T> searchByHql(String hql, Serializable... args) {
        ArrayList<T> res = null;
        try (Session session = getSession()) {
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
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean executeHql(String hql, Serializable... args) {
        boolean res = false;
        try (Session session = getSession()) {
            Query query = session.createQuery(hql);
            int i = 1;
            for (Serializable arg : args) {
                query.setParameter(i, arg);
                i++;
            }
            if(query.executeUpdate() >= 0) {
                res = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
