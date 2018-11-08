package cn.pumch.web.dao;

import java.util.List;

import cn.pumch.web.model.RoleExample;
import org.apache.ibatis.annotations.Param;
import cn.pumch.core.generic.GenericDao;
import cn.pumch.web.model.Role;

public interface RoleMapper extends GenericDao<Role, Long> {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);

    /**
     * 用户关联角色
     * @param userId
     * @param roleId
     * @return
     */
    int roleAssociated(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 通过角色名查询角色ID
     * @param roleName
     * @return
     */
    Long selectRoleIdByName(@Param("roleName") String roleName);

    /**
     * 查询所有记录
     * @return
     */
    List<Role> selectAll();

}