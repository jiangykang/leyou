package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /*
    根据父节点查询商品类目
     */
    @GetMapping(value = "list")
    public ResponseEntity<List<Category>> queryByParentId(@RequestParam(value = "pid", defaultValue = "0") Long pid) {

        List<Category> list = this.categoryService.queryListByParent(pid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);

    }

    /*
    插入新的商品类目
     */
    @PostMapping(value = "add")
    public ResponseEntity<Long> addCategory(@RequestBody Category category) {

        Long res = categoryService.addCategory(category);
        if (res == new Long(-1)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(res);
    }

    /*
    编辑商品类目
     */
    @PostMapping(value = "update")
    public ResponseEntity<Integer> updateCategory(@RequestBody Category category) {

        int res = categoryService.updateById(category);
        if (res == 1) {
            return ResponseEntity.ok(res);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
    删除商品类目
     */
    @PostMapping(value = "delete")
    public ResponseEntity<Integer> deleteCategory(@RequestBody Category category) {

        int res = categoryService.deleteById(category);
        if (res == 1) {
            return ResponseEntity.ok(res);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     *根据品牌id查询商品分类
     * @author jiangyongkang
     * @date 2021/9/10
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Category>>
     */
    @RequestMapping(value = "bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid) {
        List<Category> list = this.categoryService.queryByBrandId(bid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
