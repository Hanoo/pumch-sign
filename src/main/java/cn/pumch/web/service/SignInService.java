package cn.pumch.web.service;


import cn.pumch.web.model.SignIn;

import java.util.List;

public interface SignInService {
    /**
     * 查询我的签到列表
     * @param page
     * @param pageSize
     * @param tId
     * @param courseName
     * @param sName
     * @return
     */
    List<SignIn> getMySignInInPage(int page, int pageSize, Long tId, String courseName, String sName);

    /**
     * 查询我的签到数量
     * @param tId
     * @param courseName
     * @param sName
     * @return
     */
    int getMySignInCount(Long tId, String courseName, String sName);
}
