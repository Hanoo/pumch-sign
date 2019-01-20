package cn.pumch.web.dao;

import cn.pumch.web.model.SignIn;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SignInMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SignIn record);

    int insertSelective(SignIn record);

    SignIn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SignIn record);

    int updateByPrimaryKey(SignIn record);

    SignIn selectFullSignIn(Long signInId);

    /**
     * 根据条件进行查询指定教师课程的签到情况
     * @param start
     * @param count
     * @param tId
     * @param courseName
     * @param sName
     * @return
     */
    List<SignIn> selectByTIdInPage(@Param("start") int start,
                                   @Param("count") int count,
                                   @Param("tId") Long tId,
                                   @Param("courseName") String courseName,
                                   @Param("sName") String sName);

    /**
     * 根据条件进行查询指定教师课程的签到数量
     * @param tId
     * @param courseName
     * @param sName
     * @return
     */
    int selectCountByTId(@Param("tId") Long tId,
                         @Param("courseName") String courseName,
                         @Param("sName") String sName);

    /**
     * 根据条件进行查询指定学生课程的签到情况
     * @param start
     * @param count
     * @param signerId
     * @param courseName
     * @param sName
     * @return
     */
    List<SignIn> selectBySIdInPage(@Param("start") int start,
                                   @Param("count") int count,
                                   @Param("signerId") Long signerId,
                                   @Param("courseName") String courseName,
                                   @Param("sName") String sName);

    /**
     * 根据条件进行查询指定学生的签到数量
     * @param signerId
     * @param courseName
     * @param sName
     * @return
     */
    int selectCountBySId(@Param("signerId") Long signerId,
                         @Param("courseName") String courseName,
                         @Param("sName") String sName);

    /**
     * 根据时间区间查询签到
     * @param startTim 开始时间
     * @param endTime 结束时间
     * @param signerId 签到人ID
     * @return
     */
    List<SignIn> selectByDate(@Param("signerId") Long signerId,
                              @Param("courseId") Long courseId,
                              @Param("startTime") Date startTim,
                              @Param("endTime") Date endTime);

    /**
     *
     * @param start
     * @param pageSize
     * @param courseName
     * @param sName
     * @param startTime
     * @param endTime
     * @return
     */
    List<SignIn> selectByConditionsInPage(@Param("start") int start,
                                          @Param("count") int pageSize,
                                          @Param("courseName") String courseName,
                                          @Param("sName")String sName,
                                          @Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime);

    /**
     *
     * @param courseName
     * @param sName
     * @param startTime
     * @param endTime
     * @return
     */
    int selectCountByConditions(@Param("courseName") String courseName,
                                @Param("sName")String sName,
                                @Param("startTime") Date startTime,
                                @Param("endTime") Date endTime);
}