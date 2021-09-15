package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroups(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.querySpecGroups(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> querySpecParam(
            @RequestParam(value = "gid", required = false) Long gid
    ) {
        List<SpecParam> list =
                this.specificationService.querySpecParams(gid);
        if (list == null || list.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/param")
    public ResponseEntity<Integer> addSpecParam(@RequestBody SpecParam specParam) {

        int res = this.specificationService.addSpecParam(specParam);
        if (res == 1)
            return ResponseEntity.ok(res);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping(value = "/param/{id}")
    public ResponseEntity<Integer> deleteSpecParam(@PathVariable Long id) {
        int res = this.specificationService.deleteSpecParam(id);
        if (res == 1)
            return ResponseEntity.ok(res);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping(value = "/param")
    public ResponseEntity<Integer> updateSpecParam(@RequestBody SpecParam specParam) {
        int res = this.specificationService.updateSpecParam(specParam);
        if (res == 1)
            return ResponseEntity.ok(res);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
