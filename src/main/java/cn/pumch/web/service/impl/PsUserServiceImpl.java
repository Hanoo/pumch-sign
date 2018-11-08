package cn.pumch.web.service.impl;

import cn.pumch.core.generic.GenericServiceImpl;
import cn.pumch.web.dao.PsUserMapper;
import cn.pumch.web.enums.UserType;
import cn.pumch.web.model.PsUser;
import cn.pumch.core.generic.GenericDao;
import cn.pumch.web.model.Role;
import cn.pumch.web.service.PsUserService;
import cn.pumch.web.service.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户Service实现类
 *
 * @author StarZou
 * @since 2014年7月5日 上午11:54:24
 */
@Service
public class PsUserServiceImpl extends GenericServiceImpl<PsUser, Long> implements PsUserService {

    @Resource
    private PsUserMapper mapper;

    @Resource
    private RoleService roleService;

    @Value("${user.password.default}")
    private String defaultPassword;

    @Override
    public PsUser getUserByLoginName(String username) {
        if(null == username) {
            return null;
        }
        return mapper.selectByUsername(username);
    }

    @Override
    public boolean verifyByCredit(String loginName, String passWord) {
        if(null == loginName || null == passWord) {
            return false;
        } else {
            return passWord.equals(this.getUserByLoginName(loginName).getPassword());
        }
    }

    @Override
    public boolean doReg(PsUser record) {
        if(null!=record) {
            int result = mapper.insert(record);
            return result>0;
        }
        return false;
    }

    @Override
    public boolean updatePassword(String loginName, String oPwd, String nPwd) {
        PsUser psUser = mapper.selectByUsername(loginName);
        if(null!=psUser && oPwd.equals(psUser.getPassword())) {
            psUser.setPassword(nPwd);
            int result = mapper.updateByPrimaryKey(psUser);
            return result>0;
        }
        return false;
    }

    @Override
    public List<PsUser> findByConditionsInPage(int page, int pageSize, String nickName,
                                               String sex, String idNo, String state, UserType userType, Date startTime, Date endTime) {
        int start;
        if(page<1) {
            page = 1;
        }
        if(pageSize<10) {
            pageSize = 10;
        }
        start = (page -1) * pageSize;

        return mapper.selectByConditionsInPage(nickName, sex, idNo, state, startTime, endTime, userType.getRole_name(), start, pageSize);
    }

    @Override
    public int findByConditionsQuantity(String loginName, String sex,
                                        String idNo, String state, UserType userType, Date startTime, Date endTime) {
        return mapper.selectCountByConditions(loginName, sex, idNo, state, userType.getRole_name(), startTime, endTime);
    }

    @Override
    public List<PsUser> findStudentsInPage(int page, int pageSize, String loginName, String sex, String idNo, String state, Date startTime, Date endTime) {
        return this.findByConditionsInPage(page, pageSize, loginName, sex, idNo, state, UserType.S, startTime, endTime);
    }

    @Override
    public int findStudentsCount(String loginName, String sex, String idNo, String state, Date startTime, Date endTime) {
        return this.findByConditionsQuantity(loginName, sex, idNo, state, UserType.S, startTime, endTime);
    }

    @Override
    public List<PsUser> findTeachersInPage(int page, int pageSize, String loginName, String sex, String idNo, String state, Date startTime, Date endTime) {
        return this.findByConditionsInPage(page, pageSize, loginName, sex, idNo, state, UserType.T, startTime, endTime);
    }

    @Override
    public int findTeachersCount(String loginName, String sex, String idNo, String state, Date startTime, Date endTime) {
        return this.findByConditionsQuantity(loginName, sex, idNo, state, UserType.T, startTime, endTime);
    }

    @Override
    public int updatePsUser(PsUser psUser) {
        return mapper.updateByPrimaryKey(psUser);
    }


    @Override
    public boolean resetPwd(String userId) {
        PsUser psUser = new PsUser();
        psUser.setId(Long.valueOf(userId));
        String defaultPwd = String.valueOf(defaultPassword.hashCode());
        psUser.setPassword(defaultPwd);

        return mapper.updateByPrimaryKeySelective(psUser)>0;
    }

    @Override
    @Transactional
    public boolean createUser(PsUser user, Long roleId) {
        user.setPassword(String.valueOf(defaultPassword.hashCode()));
        user.setCreateTime(new Date());
        user.setuState("1");
        int result1 = mapper.insertSelective(user);
        boolean result2 = roleService.userRoleAssociated(user.getId(), roleId);
        return result1>0 && result2;
    }

    @Override
    public GenericDao<PsUser, Long> getDao() {
        return mapper;
    }
}
