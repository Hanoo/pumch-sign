package cn.pumch.web.service.impl;

import cn.pumch.core.generic.GenericServiceImpl;
import cn.pumch.web.dao.PsUserMapper;
import cn.pumch.web.model.PsUser;
import cn.pumch.core.generic.GenericDao;
import cn.pumch.web.service.PsUserService;
import org.springframework.stereotype.Service;

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
                                               String sex, String idNo, String state, Date startTime, Date endTime) {
        int start;
        if(page<1) {
            page = 1;
        }
        if(pageSize<10) {
            pageSize = 10;
        }
        start = (page -1) * pageSize;

        return mapper.selectByConditionsInPage(nickName, sex, idNo, state, startTime, endTime, start, pageSize);
    }

    @Override
    public int findByConditionsQuantity(String loginName, String sex,
                                        String idNo, String state, Date startTime, Date endTime) {
        return mapper.selectCountByConditions(loginName, sex, idNo, state, startTime, endTime);
    }

    @Override
    public int updatePsUser(PsUser psUser) {
        return mapper.updateByPrimaryKey(psUser);
    }


    @Override
    public boolean resetPwd(String userId) {
        PsUser psUser = new PsUser();
        psUser.setId(Long.valueOf(userId));
        String defaultPwd = String.valueOf("pumch2018".hashCode());
        psUser.setPassword(defaultPwd);

        return mapper.updateByPrimaryKeySelective(psUser)>0;
    }

    @Override
    public GenericDao<PsUser, Long> getDao() {
        return mapper;
    }
}
