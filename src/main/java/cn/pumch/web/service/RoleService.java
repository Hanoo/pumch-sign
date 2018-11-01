package cn.pumch.web.service;

import java.util.List;

import cn.pumch.core.generic.GenericService;
import cn.pumch.web.model.Role;

/**
 * 角色 业务接口
 * 
 * @author StarZou
 * @since 2014年6月10日 下午4:15:01
 **/
public interface RoleService extends GenericService<Role, Long> {
    /**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);

    /**
     * 通过操作者ID 查询其拥有的角色
     * @param userId
     * @return
     */
    List<Role> selectRolesByOperId(Long userId);
}
