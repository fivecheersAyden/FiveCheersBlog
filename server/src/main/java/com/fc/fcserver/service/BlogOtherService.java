package com.fc.fcserver.service;

import com.alibaba.fastjson.JSONObject;
import com.fc.fcserver.entity.V2BlogSt;

import java.util.List;

public interface BlogOtherService {
    List<V2BlogSt> getAllBlogKeys(String user);

    boolean addNewBrotherDoc(String currentKey, String user);

    boolean addNewChildDoc(String currentKey, String user);

    boolean resetName(String currentKey, String newName, String user);

    boolean moveUp(String currentKey, String user);

    boolean moveDown(String currentKey, String user);

    boolean deleteDoc(String currentKey, String user);

    V2BlogSt getDocDetail(String currentKey, String user);

    boolean updateDocDetail(JSONObject e);

    boolean getIfUser(String user);

    String getKeyByAccountAndPassword(String account);

    boolean checkIfPassword(String account);

    boolean checkPassword(String account, String password);

    boolean changePassword(String account, String password);

    boolean changeKeyByAccount(String account, String key);
}
