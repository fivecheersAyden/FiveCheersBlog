package com.fc.fcserver.service.impl;

import com.fc.fcserver.dao.AmideadMapper;
import com.fc.fcserver.entity.V2AmideadSt;
import com.fc.fcserver.service.AmideadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmideadServiceImpl implements AmideadService {

    @Autowired
    AmideadMapper am;

    @Override
    public V2AmideadSt getAmidead() {
        return am.getAmidead();
    }

    @Override
    public boolean updateTime() {
        return am.updateTime();
    }

    @Override
    public boolean updateGiftStill() {
        return am.updateGiftStill();
    }
}
