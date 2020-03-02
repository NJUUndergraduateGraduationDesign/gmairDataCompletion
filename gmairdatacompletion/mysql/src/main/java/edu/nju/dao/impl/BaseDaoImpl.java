package edu.nju.dao.impl;

import edu.nju.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2019/9/30 13:39
 * @description： implementation of baseDao
 */

@Repository
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    @PersistenceContext
    private EntityManager entityManager;

    protected Class<T> clazz;

    /*
     * 用反射获取具体类型
     */
    public BaseDaoImpl() {
        if (this.getClass().getGenericSuperclass() instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            clazz = (Class<T>) type.getActualTypeArguments()[0];
        }
    }

    /*
    sessionFactory.getCurrentSession():
        创建的session会绑定到当前线程；session会在事务提交或回滚后自动关闭；
    sessionFactory.openSession():
        每次调用会生成一个新的session；session在事务提交或回滚后需要手动关闭；
    entityManager:
        entityManager是JPA的api，而sessionFactory是hibernate的；两者是接口和实现的关系；
        entityManager.unwrap(Session.class)相当于获取JPA底层的hibernate实现；
        entityManager通过@PersistenceContext由Spring统一管理后不需要手动关闭；
        默认为“Entity Manager per transaction”模式。
     */
    protected final Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    /*
    处理瞬时态，刚创建还没有被初始化，而且不处于session中；
     */
    @Override
    public Serializable add(T t) {
        return getSession().save(t);
    }

    /*
    处理持久态或游离态对象，即已经被持久化，处不处于session中均可；
     */
    @Override
    public void delete(T t) {
        getSession().delete(t);
    }

    /*
    通过主键删除
     */
    @Override
    public void deleteById(Serializable id) {
        Session session = getSession();
        Object obj = session.get(clazz, id);
        session.delete(obj);
    }

    /*
    通过主键获取，非懒加载
     */
    @Override
    public T get(Serializable id) {
        return getSession().get(clazz, id);
    }

    /*
    处理游离态(已经被持久化但不在session)
    如果持久态想要更新直接set后提交事务就行
     */
    @Override
    public void update(T t) {
        getSession().update(t);
    }

    /*
    获得所有对象
     */
    @Override
    public List<T> getAll() {
        String hql = "from " + clazz.getName();
        return getSession().createQuery(hql).list();
    }

    /*
    单个查询，注意hql使用类的属性名而不是表的字段名
     */
    @Override
    public T getUniqueResultByHQL(String hql, Object... values) {
        Query query = getSession().createQuery(hql);
        if (values != null) {
            int count = values.length;
            for (int i = 0; i < count; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    /*
    列表查询
     */
    @Override
    public List<T> getListByHQL(String hql, Object... values) {
        Query query = getSession().createQuery(hql);
        if (values != null) {
            int count = values.length;
            for (int i = 0; i < count; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    @Override
    public List<T> getLimitResultByHQL(String hql, int offset, int pageSize, Object... values) {
        Query query = getSession().createQuery(hql);
        if (values != null) {
            int count = values.length;
            for (int i = 0; i < count; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        return query.list();
    }
}