package com.fc.fcserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fc.fcserver.dao.BlogMapper;
import com.fc.fcserver.entity.V2BlogSt;
import com.fc.fcserver.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper bm;

    //获取全部blog_key
    @Override
    public List<V2BlogSt> getAllBlogKeys() {
        return bm.getAllBlogKeys();
    }

    //增加同层Doc
    @Override
    public boolean addNewBrotherDoc(String currentKey) {
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
        List<String> fatherKeys = bm.getFatherKeys(frontKeys.toString());
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
        return bm.insertNewDoc(newKey.toString());
    }

    //增加子Doc
    @Override
    public boolean addNewChildDoc(String currentKey) {
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
            List<String> fatherKeys = bm.getFatherKeys(frontKeys.toString());
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
            return bm.insertNewDoc(newKey.toString());
        }
        return false;
    }

    //更改标题
    @Override
    public boolean resetName(String currentKey, String newName) {
        return bm.resetName(currentKey, newName);
    }

    @Override
    public boolean moveUp(String currentKey) {
        return false;
    }

    @Override
    public boolean moveDown(String currentKey) {
        return false;
    }

    //删除文档
    @Override
    public boolean deleteDoc(String currentKey) {
        return bm.deleteDoc(currentKey);
    }

    //获取文档
    @Override
    public V2BlogSt getDocDetail(String currentKey) {
        return bm.getDocDetail(currentKey);
    }

    //更新文档
    @Override
    public boolean updateDocDetail(JSONObject e) {
        return bm.updateDocDetail(e.get("currentKey").toString(), e.get("newContent").toString());
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
}
