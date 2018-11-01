package cn.pumch.web.dao;

import cn.pumch.web.model.CourseScore;

public interface CourseScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CourseScore record);

    int insertSelective(CourseScore record);

    CourseScore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CourseScore record);

    int updateByPrimaryKey(CourseScore record);
}