package com.fc.fcserver.service;

import com.alibaba.fastjson.JSONObject;
import com.fc.fcserver.entity.V2BlogSt;

import java.util.List;

public interface BlogService {
    List<V2BlogSt> getAllBlogKeys();

    boolean addNewBrotherDoc(String currentKey);

    boolean addNewChildDoc(String currentKey);

    boolean resetName(String currentKey, String newName);

    boolean moveUp(String currentKey);

    boolean moveDown(String currentKey);

    boolean deleteDoc(String currentKey);

    V2BlogSt getDocDetail(String currentKey);

    boolean updateDocDetail(JSONObject e);
}
