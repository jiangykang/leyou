package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryListByParent(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return this.categoryMapper.select(category);
    }

    @Transactional
    public Long addCategory(Category category) {
        category.setId(null);
        //如果在叶子节点添加新节点，原叶子节点变为父节点，需要更改是否父节点标志
        Category parentNode = categoryMapper.selectByPrimaryKey(category.getParentId());
        if (parentNode.getIsParent()==false) {
            parentNode.setIsParent(true);
            categoryMapper.updateByPrimaryKey(parentNode);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        category.setCreateStamp(new java.sql.Timestamp(System.currentTimeMillis()));
        //插入新节点
        int i = categoryMapper.insert(category);
        List<Category> list = categoryMapper.select(category);
        if (i == 1 && list.size() != 0){
            return list.get(0).getId();
        }
        else
            return new Long(-1);
    }

    @Transactional
    public int updateById(Category category) {

        int i = categoryMapper.updateByPrimaryKeySelective(category);
        return i;
    }

    public int deleteById(Category category) {

        Category sub = new Category();
        sub.setParentId(category.getParentId());
        List<Category> list = categoryMapper.select(sub);
        if (list.size() <= 1) {
            //父节点更新是否父节点标志
            Category parent = new Category();
            parent.setId(category.getParentId());
            parent.setIsParent(false);
            categoryMapper.updateByPrimaryKeySelective(parent);
        }
        int i = categoryMapper.deleteByPrimaryKey(category);
        return i;
    }
}
