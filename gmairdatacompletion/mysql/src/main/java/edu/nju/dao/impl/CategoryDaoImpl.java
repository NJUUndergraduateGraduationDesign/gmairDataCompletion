package edu.nju.dao.impl;

import edu.nju.dao.CategoryDao;
import edu.nju.model.Location;
import edu.nju.model.statistic.Category;
import edu.nju.model.statistic.CategoryNumber;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：CK
 * @date ：Created in 2020/3/10 10:47
 * @description：
 */
@Repository
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {

    @Override
    public Category getCategoryByUid(String uid) {
        String hql = "SELECT * FROM " + clazz.getName()
                + " WHERE uid= ?0";
        Object obj = getUniqueColumnByHQL(hql, uid);
        return obj == null ? null : (Category) obj;
    }

    @Override
    public List<CategoryNumber> getAllCategoryEffect(String area) {
        String hql = "select new edu.nju.model.statistic.CategoryNumber(effect,count(*)) " +
                "from Category group by effect";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<CategoryNumber> getAllCategoryEffectProvince(String area) {
        String hql = "select new edu.nju.model.statistic.CategoryNumber(c.effect,count(*)) " +
                "from Category c,User u,Location l where c.uid=u.uid and l.cityId=u.cityId and l.provinceName=?1 group by c.effect";
        Query query = getSession().createQuery(hql);
        query.setParameter(1, area);
        return query.list();
    }

    @Override
    public List<CategoryNumber> getAllCategoryEnvironment(String area) {
        String hql = "select new edu.nju.model.statistic.CategoryNumber(environment,count(*)) " +
                "from Category group by environment";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<CategoryNumber> getAllCategoryEnvironmentProvince(String area) {
        String hql = "select new edu.nju.model.statistic.CategoryNumber(c.environment,count(*)) " +
                "from Category c,User u,Location l where c.uid=u.uid and l.cityId=u.cityId and l.provinceName=?1 group by c.environment";
        Query query = getSession().createQuery(hql);
        query.setParameter(1,area);
        return query.list();
    }
}
