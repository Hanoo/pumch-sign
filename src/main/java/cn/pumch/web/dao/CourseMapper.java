package cn.pumch.web.dao;

import cn.pumch.web.model.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    /**
     * 通过课程名称进行条件查询
     * @param courseName
     * @param start
     * @param count
     * @return
     */
    List<Course> selectByConditionsInPage(@Param("courseName") String courseName,
                                          @Param("start") int start,
                                          @Param("count") int count);

    /**
     * 查询课程数量
     * @param courseName
     * @return
     */
    int selectCountByCondition(@Param("courseName") String courseName);

    /**
     * 查询指定教师的课程
     * @param tId
     * @param start
     * @param count
     * @return
     */
    List<Course> selectByTIdInPage(@Param("tId") Long tId,
                                   @Param("start") int start,
                                   @Param("count") int count);

    /**
     * 查询指定教师课程的数量
     * @param tId
     * @return
     */
    int selectCountByTId(@Param("tId") Long tId);
}