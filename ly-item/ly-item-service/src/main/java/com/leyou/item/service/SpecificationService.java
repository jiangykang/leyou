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

    public List<SpecParam> querySpecParams(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        param.setGeneric(generic);
        return this.specParamMapper.select(param);
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

    public int addSpecGroups(SpecGroup specGroup) {
        int res = this.specGroupMapper.insertSelective(specGroup);
        return res;
    }

    public int deleteSpecGroups(Long id) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        int res = this.specGroupMapper.deleteByPrimaryKey(specGroup);
        return res;
    }

    public int updateSpecGroups(SpecGroup specGroup) {
        int res = this.specGroupMapper.updateByPrimaryKeySelective(specGroup);
        return res;
    }
}
