package cn.pumch.web.service;

import cn.pumch.core.generic.GenericService;
import cn.pumch.web.enums.UserType;
import cn.pumch.web.model.PsUser;

import java.util.Date;
import java.util.List;

public interface PsUserService extends GenericService<PsUser, Long> {

    /**
     * 根据用户登录ID查询用户
     * 
     * @param loginName
     * @return
     */
    PsUser getUserByLoginName(String loginName);

    /**
     * 通过用户名和密码查找用户
     *
     * @param loginName 用户名
     * @param passWord  密码
     * @return 当前认证是否合法
     */
    boolean verifyByCredit(String loginName, String passWord);

    /**
     * 新用户注册
     * @param record 实体
     * @return
     */
    boolean doReg(PsUser record);

    /**
     * 重置密码
     * @param loginName 登录名
     * @param oPwd 原来的密码
     * @param nPwd 新密码
     * @return
     */
    boolean updatePassword(String loginName, String oPwd, String nPwd);

    /**
     * 根据条件获取用户列表
     */
    List<PsUser> findByConditionsInPage(int page, int pageSize, String loginName, String sex,
                                        String idNo, String state, UserType userType, Date startTime, Date endTime);

    /**
     * 获取用户总数
     * @return
     */
    int findByConditionsQuantity(String loginName, String sex,
                                 String idNo, String state ,UserType userType, Date startTime, Date endTime);

    /**
     * 查询学生列表
     * @param page
     * @param pageSize
     * @param loginName
     * @param sex
     * @param idNo
     * @param state
     * @param startTime
     * @param endTime
     * @return
     */
    List<PsUser> findStudentsInPage(int page, int pageSize, String loginName, String sex,
                                    String idNo, String state, Date startTime, Date endTime);

    /**
     * 查询学生数量
     * @param loginName
     * @param sex
     * @param idNo
     * @param state
     * @param startTime
     * @param endTime
     * @return
     */
    int findStudentsCount(String loginName, String sex,
                                    String idNo, String state, Date startTime, Date endTime);

    /**
     * 查询教师列表
     * @param page
     * @param pageSize
     * @param loginName
     * @param sex
     * @param idNo
     * @param state
     * @param startTime
     * @param endTime
     * @return
     */
    List<PsUser> findTeachersInPage(int page, int pageSize, String loginName, String sex,
                                    String idNo, String state, Date startTime, Date endTime);

    /**
     * 查询教师数量
     * @param loginName
     * @param sex
     * @param idNo
     * @param state
     * @param startTime
     * @param endTime
     * @return
     */
    int findTeachersCount(String loginName, String sex,
                          String idNo, String state, Date startTime, Date endTime);

    /**
     * 更新用户信息
     */
    int updatePsUser(PsUser psUser);

    /**
     * 重置用户密码
     */
    boolean resetPwd(String userId);

    /**
     * 创建用户并关联角色
     * @param user
     * @param roleId
     * @return
     */
    boolean createUser(PsUser user, Long roleId);
}
