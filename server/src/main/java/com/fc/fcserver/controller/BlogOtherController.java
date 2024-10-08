package com.fc.fcserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.fc.fcserver.entity.V2BlogSt;
import com.fc.fcserver.service.BlogOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogother")
public class BlogOtherController {

    @Autowired
    BlogOtherService bs;

    /**
     * 查询用户是否存在
     */
    @GetMapping("/getIfUser")
    public boolean getIfUser(@RequestParam("user") String user){
        return bs.getIfUser(user);
    }

    /**
     * 获取全部Blog（不包含内容）
     * @return
     */
    @GetMapping("/getAllBlogKeys")
    public List<V2BlogSt> getAllBlogKeys(@RequestParam("user") String user){
        return bs.getAllBlogKeys(user);
    }

    /**
     * 增加一个同层文件
     * @param currentKey
     * @return
     */
    @PostMapping("/addNewBrotherDoc")
    public boolean addNewBrotherDoc(@RequestParam("currentKey") String currentKey, @RequestParam("user") String user){
        return bs.addNewBrotherDoc(currentKey, user);
    }

    /**
     * 增加一个子文件
     * @param currentKey
     * @return
     */
    @PostMapping("/addNewChildDoc")
    public boolean addNewChildDoc(@RequestParam("currentKey") String currentKey, @RequestParam("user") String user){
        return bs.addNewChildDoc(currentKey, user);
    }

    /**
     * 重命名
     * @param currentKey
     * @return
     */
    @PostMapping("/resetName")
    public boolean resetName(@RequestParam("currentKey")  String currentKey, @RequestParam("newName") String newName, @RequestParam("user") String user){
        return bs.resetName(currentKey, newName, user);
    }

    /**
     * 上移该文档
     * @param currentKey
     * @return
     */
    @GetMapping("/moveUp")
    public boolean moveUp(@RequestParam("currentKey") String currentKey, @RequestParam("user") String user){
        return bs.moveUp(currentKey, user);
    }

    /**
     * 下移该文档
     * @param currentKey
     * @return
     */
    @GetMapping("/moveDown")
    public boolean moveDown(@RequestParam("currentKey") String currentKey, @RequestParam("user") String user){
        return bs.moveDown(currentKey, user);
    }

    /**
     * 删除该文档
     * @param currentKey
     * @return
     */
    @GetMapping("/deleteDoc")
    public boolean deleteDoc(@RequestParam("currentKey") String currentKey, @RequestParam("user") String user){
        return bs.deleteDoc(currentKey, user);
    }

    /**
     * 获取该文档全部信息
     */
    @PostMapping("/getDocDetail")
    public V2BlogSt getDocDetail(@RequestParam("currentKey") String currentKey, @RequestParam("user") String user){
        return bs.getDocDetail(currentKey, user);
    }

    /**
     * 更新该文档全部信息
     */
    @PostMapping("/updateDocDetail")
    public boolean updateDocDetail(@RequestBody JSONObject e){
        return bs.updateDocDetail(e);
    }

    /**
    * 检查帐户有无密码
    */ 
    @GetMapping("/checkIfPassword")
    public boolean checkIfPassword(@RequestParam("account") String account){
        return bs.checkIfPassword(account);
    }


    /**
    * 检查帐户密码
    */ 
    @GetMapping("/checkPassword")
    public boolean checkPassword(@RequestParam("account") String account, @RequestParam("password") String password){
        return bs.checkPassword(account, password);
    }

    /**
    * 更改帐户密码
    */ 
    @GetMapping("/changePassword")
    public boolean changePassword(@RequestParam("account") String account, @RequestParam("password") String password){
        return bs.changePassword(account, password);
    }

   /**
    * 根据帐户返回key
    */ 
    @GetMapping("/getKeyByAccountAndPassword")
    public String getKeyByAccountAndPassword(@RequestParam("account") String account){
        return bs.getKeyByAccountAndPassword(account);
    }

    /**
     * 根据用户修改key
     */
    @PostMapping("/changeKeyByAccount")
    public boolean changeKeyByAccount(@RequestParam("account") String account, @RequestParam("key") String key){
        return bs.changeKeyByAccount(account, key);
    }

}
