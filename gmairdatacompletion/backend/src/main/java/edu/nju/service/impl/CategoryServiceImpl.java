package edu.nju.service.impl;

import edu.nju.model.statistic.Category;
import edu.nju.model.statistic.CategoryNumber;
import edu.nju.service.CategoryDataService;
import edu.nju.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：CK
 * @date ：Created in 2020/3/10 0:25
 * @description：
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryDataService categoryDataService;

    @Override
    public Category getCategoryByUid(String uid) {
        return categoryDataService.getCategoryByUid(uid);
    }

    @Override
    public List<CategoryNumber> getAllCategoryEffect(String area) {
        return categoryDataService.getAllCategoryEffect(area);
    }

    @Override
    public List<CategoryNumber> getAllCategoryEnvironment(String area) {
        return categoryDataService.getAllCategoryEnvironment(area);
    }
}
