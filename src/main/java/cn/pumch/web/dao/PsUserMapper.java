package cn.pumch.web.dao;

import cn.pumch.core.generic.GenericDao;
import cn.pumch.web.model.PsUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface PsUserMapper extends GenericDao<PsUser, Long> {

    int deleteByPrimaryKey(Long id);

    int insert(PsUser record);

    int insertSelective(PsUser record);

    PsUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PsUser record);

    int updateByPrimaryKey(PsUser record);

    /**
     * 通过登录名查询用户
     * @param loginName
     * @return
     */
    PsUser selectByUsername(@Param("loginName") String loginName);

    /**
     * 根据指定的页码和页长，进行条件查询
     * @param nickName 显示姓名
     * @param sex 性别
     * @param idNo 身份证号
     * @param state 用户状态
     * @param startTime 登录时间区间开始
     * @param endTime 登录时间区间结束
     * @param start 开始位置
     * @param count 数量
     * @return 用户列表
     */
    List<PsUser> selectByConditionsInPage(@Param("nickName")String nickName,
                                          @Param("sex") String sex,
                                          @Param("idNo") String idNo,
                                          @Param("uState") String state,
                                          @Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime,
                                          @Param("roleName") String roleName,
                                          @Param("start") int start,
                                          @Param("count") int count);

    /**
     * 根据条件查询记录的数量
     * @param nickName
     * @param sex
     * @param idNo
     * @param state
     * @param startTime
     * @param endTime
     * @return
     */
    int selectCountByConditions(@Param("nickName")String nickName,
                                @Param("sex") String sex,
                                @Param("idNo") String idNo,
                                @Param("uState") String state,
                                @Param("roleName") String roleName,
                                @Param("startTime") Date startTime,
                                @Param("endTime") Date endTime);

}