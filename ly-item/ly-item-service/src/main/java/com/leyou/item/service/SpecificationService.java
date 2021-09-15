package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> querySpecGroups(Long cid) {

        SpecGroup t = new SpecGroup();
        t.setCid(cid);
        return this.specGroupMapper.select(t);}

    public List<SpecParam> querySpecParams(Long gid) {
        SpecParam t = new SpecParam();
        t.setGroupId(gid);
        return this.specParamMapper.select(t);
    }

    public int addSpecParam(SpecParam specParam) {
        int res = this.specParamMapper.insertSelective(specParam);
        return res;
    }

    public int deleteSpecParam(Long id) {
        SpecParam specParam = new SpecParam();
        specParam.setId(id);
        int res = this.specParamMapper.deleteByPrimaryKey(specParam);
        return res;
    }

    public int updateSpecParam(SpecParam specParam) {
        int res = this.specParamMapper.updateByPrimaryKeySelective(specParam);
        return res;
    }
}
