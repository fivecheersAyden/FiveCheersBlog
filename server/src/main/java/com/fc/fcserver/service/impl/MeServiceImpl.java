package com.fc.fcserver.service.impl;

import com.fc.fcserver.dao.MeMapper;
import com.fc.fcserver.dto.MeList;
import com.fc.fcserver.entity.V2MeSt;
import com.fc.fcserver.service.MeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeServiceImpl implements MeService {
    @Autowired
    private MeMapper mm;

    @Override
    public MeList getMyList() {
        List<V2MeSt> v2MeStList = mm.selectAllFromV2Me();
        MeList meList = new MeList();
        meList.setContact_wechat(getV2MeStListValueByType(v2MeStList, "contact_wechat"));
        meList.setContact_qq(getV2MeStListValueByType(v2MeStList, "contact_qq"));
        meList.setContact_github(getV2MeStListValueByType(v2MeStList, "contact_github"));
        meList.setName(getV2MeStListValueByType(v2MeStList, "name"));
        meList.setIntro_1(getV2MeStListValueByType(v2MeStList, "intro_1"));
        meList.setIntro_2(getV2MeStListValueByType(v2MeStList, "intro_2"));
        meList.setIntro_3(getV2MeStListValueByType(v2MeStList, "intro_3"));
        meList.setIntro_4(getV2MeStListValueByType(v2MeStList, "intro_4"));
        meList.setIntro_5(getV2MeStListValueByType(v2MeStList, "intro_5"));
        meList.setIntro_6(getV2MeStListValueByType(v2MeStList, "intro_6"));
        meList.setIntro_7(getV2MeStListValueByType(v2MeStList, "intro_7"));
        meList.setPhoto(getV2MeStListValueByType(v2MeStList, "photo"));
        return meList;
    }

    @Override
    public boolean changeKey(String type, String value) {
        return mm.changeKey(type, value);
    }

    private String getV2MeStListValueByType(List<V2MeSt> v2MeStList ,String type){
        Optional<V2MeSt> result = v2MeStList.stream()
                .filter(obj -> obj.getType().equals(type))
                .findFirst();
        return result.map(V2MeSt::getValue).orElse(null);
    }

}

