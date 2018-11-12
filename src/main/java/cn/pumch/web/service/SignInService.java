package cn.pumch.web.service;


import cn.pumch.web.model.SignIn;

import java.util.Date;
import java.util.List;

public interface SignInService {
    /**
     * 根据教师ID查询任课课程签到列表
     * @param page
     * @param pageSize
     * @param tId
     * @param courseName
     * @param sName
     * @return
     */
    List<SignIn> getTSignInListInPage(int page, int pageSize, Long tId, String courseName, String sName);

    /**
     * 根据教师ID查询任课课程签到数量
     * @param tId
     * @param courseName
     * @param sName
     * @return
     */
    int getTSignInCount(Long tId, String courseName, String sName);

    /**
     * 根据学生ID查询任课课程签到列表
     * @param page
     * @param pageSize
     * @param signerId
     * @param courseName
     * @param sName
     * @return
     */
    List<SignIn> getSSignInListInPage(int page, int pageSize, Long signerId, String courseName, String sName);

    /**
     * 根据学生ID查询任课课程签到数量
     * @param signerId
     * @param courseName
     * @param sName
     * @return
     */
    int getSSignInCount(Long signerId, String courseName, String sName);

    /**
     * 学生签到
     * @param signerId
     * @param courseId
     * @return
     */
    boolean doSignIn(Long signerId, Long courseId);

    /**
     * 根据时间区间查询指定学生、指定课程的签到情况
     * @param signerId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SignIn> getSignInListByDate(Long signerId, Long courseId, Date startTime, Date endTime);

    /**
     * 为签到的课程打分
     * @param signInId 签到表主键
     * @param score 分数
     * @return
     */
    boolean doScore(Long signInId, int score);
}
