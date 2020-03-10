package edu.nju.dao;

import edu.nju.dao.impl.BaseDaoImpl;
import edu.nju.model.statistic.Category;
import edu.nju.model.statistic.CategoryNumber;

import java.util.List;

/**
 * @author ：CK
 * @date ：Created in 2020/3/10 10:46
 * @description：
 */
public interface CategoryDao extends BaseDao<Category> {

    public Category getCategoryByUid(String uid);

    public List<CategoryNumber> getAllCategoryEffect(String area);

    public List<CategoryNumber> getAllCategoryEffectProvince(String area);

    public List<CategoryNumber> getAllCategoryEnvironment(String area);

    public List<CategoryNumber> getAllCategoryEnvironmentProvince(String area);
}
