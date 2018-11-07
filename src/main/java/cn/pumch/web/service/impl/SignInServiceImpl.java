package cn.pumch.web.service.impl;

import cn.pumch.web.dao.SignInMapper;
import cn.pumch.web.model.SignIn;
import cn.pumch.web.service.SignInService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private SignInMapper signInMapper;

    @Override
    public List<SignIn> getTSignInListInPage(int page, int pageSize, Long tId,
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
    public int getTSignInCount(Long tId, String courseName, String sName) {
        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }

        return signInMapper.selectCountByTId(tId, courseName, sName);
    }

    @Override
    public List<SignIn> getSSignInListInPage(int page, int pageSize, Long signerId, String courseName, String sName) {
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
        return signInMapper.selectBySIdInPage(start, pageSize, signerId, courseName, sName);
    }

    @Override
    public int getSSignInCount(Long signerId, String courseName, String sName) {
        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }
        return signInMapper.selectCountBySId(signerId, courseName, sName);
    }

    @Override
    public boolean doSignIn(Long signerId, Long courseId) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 6);
        Date startTime = today.getTime();
        today.set(Calendar.HOUR_OF_DAY,21);
        Date endTime = today.getTime();
        List<SignIn> signIns = this.getSignInListByDate(signerId, courseId, startTime, endTime);
        if(signIns.size()>0) {//已经签到成功直接返回成功
            return true;
        } else {
            SignIn signIn = new SignIn();
            signIn.setSignerId(signerId);
            signIn.setCourseId(courseId);
            signIn.setSignInTime(new Date());

            return signInMapper.insertSelective(signIn)>0;
        }
    }

    @Override
    public List<SignIn> getSignInListByDate(Long signerId, Long courseId, Date startTime, Date endTime) {
        if(null==startTime || null==endTime) {
            return null;
        }
        return signInMapper.selectByDate(signerId, courseId, startTime, endTime);
    }
}
