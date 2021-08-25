package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public int addCategory(Category category) {
        //如果在叶子节点添加新节点，原叶子节点变为父节点，需要更改是否父节点标志
        Category parentNode = categoryMapper.selectByPrimaryKey(category.getParentId());
        if (parentNode.getIsParent()==false) {
            parentNode.setIsParent(true);
            categoryMapper.updateByPrimaryKey(parentNode);
        }
        return categoryMapper.insert(category);
    }

    @Transactional
    public int updateById(Category category) {

        int i = categoryMapper.updateByPrimaryKeySelective(category);
        return i;
    }

    public int deleteById(Category category) {

        //todo 查询父节点-是否父节点状态
        int i = categoryMapper.deleteByPrimaryKey(category);
        return i;
    }
}
