package com.fc.fcserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fc.fcserver.dao.BlogMapper;
import com.fc.fcserver.dao.BlogOtherMapper;
import com.fc.fcserver.entity.V2BlogSt;
import com.fc.fcserver.service.BlogOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogOtherServiceImpl implements BlogOtherService {

    @Autowired
    BlogOtherMapper bm;

    //获取全部blog_key
    @Override
    public List<V2BlogSt> getAllBlogKeys(String user) {
        return bm.getAllBlogKeys(user);
    }

    //增加同层Doc
    @Override
    public boolean addNewBrotherDoc(String currentKey, String user) {
        //格式化keys
        int[] keys = getKeys(currentKey);
        //获取深度1/2/3/4
        int depth = getDepth(keys);
        //获取当前深度前的key
        StringBuilder frontKeys = new StringBuilder();
        for (int i = 0; i < depth-1; i++) {
            frontKeys.append(keys[i]).append("-");
        }
        //获取表中所有同父项key
        List<String> fatherKeys = bm.getFatherKeys(frontKeys.toString(), user);
        //获取当前深度的最大值
        int maxNum = 0;
        for (String fatherKey : fatherKeys) {
            int[] tmpKeys = getKeys(fatherKey);
            if (tmpKeys[depth - 1] > maxNum) {
                maxNum = tmpKeys[depth - 1];
            }
        }
        //生成新key
        StringBuilder newKey = new StringBuilder(frontKeys.toString() + (maxNum + 1));
        for (int i = 0; i < 4-depth; i++) {
            newKey.append("-0");
        }
        System.out.println(newKey);
        //增加doc
        return bm.insertNewDoc(newKey.toString(), user);
    }

    //增加子Doc
    @Override
    public boolean addNewChildDoc(String currentKey, String user) {
        //格式化keys
        int[] keys = getKeys(currentKey);
        //获取深度1/2/3/4
        int depth = getDepth(keys);
        if(depth < 4){
            //获取当前深度的key
            StringBuilder frontKeys = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                frontKeys.append(keys[i]).append("-");
            }
            //获取表中所有同父项key
            List<String> fatherKeys = bm.getFatherKeys(frontKeys.toString(), user);
            //获取当前深度的最大值
            int maxNum = 0;
            for (String fatherKey : fatherKeys) {
                int[] tmpKeys = getKeys(fatherKey);
                if (tmpKeys[depth] > maxNum) {
                    maxNum = tmpKeys[depth];
                }
            }
            //生成新key
            StringBuilder newKey = new StringBuilder(frontKeys.toString() + (maxNum + 1));
            for (int i = 0; i < 4-depth-1; i++) {
                newKey.append("-0");
            }
            System.out.println(newKey);
            return bm.insertNewDoc(newKey.toString(), user);
        }
        return false;
    }

    //更改标题
    @Override
    public boolean resetName(String currentKey, String newName, String user) {
        return bm.resetName(currentKey, newName, user);
    }

    @Override
    public boolean moveUp(String currentKey, String user) {
        return false;
    }

    @Override
    public boolean moveDown(String currentKey, String user) {
        return false;
    }

    //删除文档
    @Override
    public boolean deleteDoc(String currentKey, String user) {
        return bm.deleteDoc(currentKey, user);
    }

    //获取文档
    @Override
    public V2BlogSt getDocDetail(String currentKey, String user) {
        return bm.getDocDetail(currentKey, user);
    }

    //更新文档
    @Override
    public boolean updateDocDetail(JSONObject e) {
        return bm.updateDocDetail(e.get("currentKey").toString(), e.get("newContent").toString(), e.get("user").toString());
    }

    //查询是否有用户
    @Override
    public boolean getIfUser(String user) {
        return bm.getIfUser(user);
    }

    //处理字符串
    private int[] getKeys(String key){
        String[] stringArray = key.split("-");

        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }
        return intArray;
    }

    //获取当前深度
    private int getDepth(int[] keys){
        int depth = 4;
        for (int i = 0; i < 4; i++) {
            if(keys[i] == 0){
                depth = i;
                break;
            }
        }
        return depth;
    }

    @Override
    public String getKeyByAccountAndPassword(String account) {
        // TODO Auto-generated method stub
        return bm.getKeyByAccountAndPassword(account);
        // throw new UnsupportedOperationException("Unimplemented method 'getKeyByAccountAndPassword'");
    }

    @Override
    public boolean checkIfPassword(String account) {
        // TODO Auto-generated method stub

        String rightPassword = bm.getPassword(account);
        if (rightPassword == null || rightPassword.isEmpty()) {
            return false;
        }else{
            return true;
        }
        // throw new UnsupportedOperationException("Unimplemented method 'checkIfPassword'");
    }

    @Override
    public boolean checkPassword(String account, String password) {
        // TODO Auto-generated method stub

        String rightPassword = bm.getPassword(account);
        if (rightPassword.equals(password)) {
            return true;
        }
        return false;
        // throw new UnsupportedOperationException("Unimplemented method 'checkPassword'");
    }

    @Override
    public boolean changePassword(String account, String password) {
        // TODO Auto-generated method stub
        return bm.changePassword(account, password);
        // throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    @Override
    public boolean changeKeyByAccount(String account, String key) {
        // TODO Auto-generated method stub
        return bm.changeKeyByAccount(account, key);
        // throw new UnsupportedOperationException("Unimplemented method 'changeKeyByAccount'");
    }
}
