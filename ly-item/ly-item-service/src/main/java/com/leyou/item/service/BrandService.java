package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPageAndSort(
            Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("name", "%" + key + "%")
                    .orEqualTo("letter", key.toUpperCase());
        }
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        // 返回结果
        return new PageResult<>(pageInfo.getTotal(), pageInfo);
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增品牌信息
        this.brandMapper.insertSelective(brand);
        //新增品牌和分类中间表
        for (Long cid : cids
             ) {
            this.brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    public void updateBrand(Brand brand, List<Long> cids) {
        //更新品牌信息
        this.brandMapper.updateByPrimaryKeySelective(brand);
        //更新品牌和分类中间表
        //1.删除原来的品牌和分类中间表
        this.brandMapper.deleteCategoryBrand(brand.getId());
        //2.新增品牌和分类中间表数据
        for (Long cid : cids
        ) {
            this.brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    public int deleteBrand(Brand brand) {

        int var1 = this.brandMapper.deleteCategoryBrand(brand.getId());
        int var2 = this.brandMapper.deleteByPrimaryKey(brand);
        if (var1 > 0 && var2 > 0) {
            return 1;
        }
        return 0;
    }

    public List<Brand> queryBrandByCategory(Long cid) {
        return this.brandMapper.queryByCategoryId(cid);
    }
}
