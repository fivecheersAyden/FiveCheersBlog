package com.fc.fcserver.service;

import com.fc.fcserver.dto.MeList;

public interface MeService {
    MeList getMyList();

    boolean changeKey(String type, String value);
}
