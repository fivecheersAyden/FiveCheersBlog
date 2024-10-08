package com.fc.fcserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fc.fcserver.dao.BlogOtherMapper;

@SpringBootTest
class FcserverApplicationTests {

    @Autowired
    BlogOtherMapper bm;

    @Test
    void contextLoads() {
        System.out.println(bm.getKeyByAccountAndPassword("时啟轩"));
        System.out.println(bm.getPassword("时啟轩"));
        // System.out.println(bm.changePassword("时啟轩", "Deadpool1234-"));

    }

}
