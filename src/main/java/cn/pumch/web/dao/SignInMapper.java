package cn.pumch.web.dao;

import cn.pumch.web.model.SignIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignInMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SignIn record);

    int insertSelective(SignIn record);

    SignIn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SignIn record);

    int updateByPrimaryKey(SignIn record);

    List<SignIn> selectByTIdInPage(@Param("start") int start,
                                   @Param("count") int count,
                                   @Param("tId") Long tId,
                                   @Param("courseName") String courseName,
                                   @Param("sName") String sName);

    int selectCountByTId(@Param("tId") Long tId,
                         @Param("courseName") String courseName,
                         @Param("sName") String sName);
}