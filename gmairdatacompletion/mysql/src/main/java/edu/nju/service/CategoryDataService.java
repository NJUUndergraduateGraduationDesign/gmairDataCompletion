package edu.nju.service;

import edu.nju.model.statistic.Category;
import edu.nju.model.statistic.CategoryNumber;

import java.util.List;

/**
 * @author ：CK
 * @date ：Created in 2020/3/10 10:48
 * @description：
 */
public interface CategoryDataService {

    public Category getCategoryByUid(String uid);

    public List<CategoryNumber> getAllCategoryEffect(String area);

    public List<CategoryNumber> getAllCategoryEnvironment(String area);

}
