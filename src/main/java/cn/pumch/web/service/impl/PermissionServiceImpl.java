package cn.pumch.web.service.impl;

import java.util.List;
import javax.annotation.Resource;

import cn.pumch.core.generic.GenericServiceImpl;
import org.springframework.stereotype.Service;
import cn.pumch.core.generic.GenericDao;
import cn.pumch.web.dao.PermissionMapper;
import cn.pumch.web.model.Permission;
import cn.pumch.web.service.PermissionService;

/**
 * 权限Service实现类
 *
 * @author StarZou
 * @since 2014年6月10日 下午12:05:03
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public GenericDao<Permission, Long> getDao() {
        return permissionMapper;
    }

    @Override
    public List<Permission> selectPermissionsByRoleId(Long roleId) {
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }
}
