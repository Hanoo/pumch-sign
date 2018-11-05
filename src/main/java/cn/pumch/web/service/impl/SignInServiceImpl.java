package cn.pumch.web.service.impl;

import cn.pumch.web.dao.SignInMapper;
import cn.pumch.web.model.SignIn;
import cn.pumch.web.service.SignInService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private SignInMapper signInMapper;

    @Override
    public List<SignIn> getMySignInInPage(int page, int pageSize, Long tId,
                                          String courseName, String sName) {
        int start;
        if(page<1) {
            page = 1;
        }
        if(pageSize<10) {
            pageSize = 10;
        }
        start = (page -1) * pageSize;

        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }

        return signInMapper.selectByTIdInPage(start, pageSize, tId, courseName, sName);
    }

    @Override
    public int getMySignInCount(Long tId, String courseName, String sName) {
        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }

        return signInMapper.selectCountByTId(tId, courseName, sName);
    }
}
