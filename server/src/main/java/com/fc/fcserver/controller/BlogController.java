package com.fc.fcserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.fc.fcserver.entity.V2BlogSt;
import com.fc.fcserver.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService bs;

    /**
     * 获取全部Blog（不包含内容）
     * @return
     */
    @GetMapping("/getAllBlogKeys")
    public List<V2BlogSt> getAllBlogKeys(){
        return bs.getAllBlogKeys();
    }

    /**
     * 增加一个同层文件
     * @param currentKey
     * @return
     */
    @PostMapping("/addNewBrotherDoc")
    public boolean addNewBrotherDoc(@RequestParam("currentKey") String currentKey){
        return bs.addNewBrotherDoc(currentKey);
    }

    /**
     * 增加一个子文件
     * @param currentKey
     * @return
     */
    @PostMapping("/addNewChildDoc")
    public boolean addNewChildDoc(@RequestParam("currentKey") String currentKey){
        return bs.addNewChildDoc(currentKey);
    }

    /**
     * 重命名
     * @param currentKey
     * @return
     */
    @PostMapping("/resetName")
    public boolean resetName(@RequestParam("currentKey")  String currentKey, @RequestParam("newName") String newName){
        return bs.resetName(currentKey, newName);
    }

    /**
     * 上移该文档
     * @param currentKey
     * @return
     */
    @GetMapping("/moveUp")
    public boolean moveUp(@RequestParam("currentKey") String currentKey){
        return bs.moveUp(currentKey);
    }

    /**
     * 下移该文档
     * @param currentKey
     * @return
     */
    @GetMapping("/moveDown")
    public boolean moveDown(@RequestParam("currentKey") String currentKey){
        return bs.moveDown(currentKey);
    }

    /**
     * 删除该文档
     * @param currentKey
     * @return
     */
    @GetMapping("/deleteDoc")
    public boolean deleteDoc(@RequestParam("currentKey") String currentKey){
        return bs.deleteDoc(currentKey);
    }

    /**
     * 获取该文档全部信息
     */
    @PostMapping("/getDocDetail")
    public V2BlogSt getDocDetail(@RequestParam("currentKey") String currentKey){
        return bs.getDocDetail(currentKey);
    }

    /**
     * 更新该文档全部信息
     */
    @PostMapping("/updateDocDetail")
    public boolean updateDocDetail(@RequestBody JSONObject e){
        return bs.updateDocDetail(e);
    }
}
