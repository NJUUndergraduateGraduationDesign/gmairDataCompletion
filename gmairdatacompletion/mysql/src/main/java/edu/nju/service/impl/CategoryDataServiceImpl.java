package edu.nju.service.impl;

import edu.nju.dao.CategoryDao;
import edu.nju.model.statistic.Category;
import edu.nju.model.statistic.CategoryNumber;
import edu.nju.service.CategoryDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author ：CK
 * @date ：Created in 2020/3/10 10:49
 * @description：
 */
@Service
@Transactional
public class CategoryDataServiceImpl implements CategoryDataService {

    @Resource
    CategoryDao categoryDao;

    @Override
    public Category getCategoryByUid(String uid) {
        return categoryDao.getCategoryByUid(uid);
    }

    @Override
    public List<CategoryNumber> getAllCategoryEffect(String area) {
        if(area.equals("china")){
            return categoryDao.getAllCategoryEffect(area);
        }
        else {
            return categoryDao.getAllCategoryEffectProvince(area);
        }
    }

    @Override
    public List<CategoryNumber> getAllCategoryEnvironment(String area) {
        if(area.equals("china")){
            return categoryDao.getAllCategoryEnvironment(area);
        }
        else {
            return categoryDao.getAllCategoryEnvironmentProvince(area);
        }
    }

}
