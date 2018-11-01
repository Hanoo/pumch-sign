package cn.pumch.web.service;

import cn.pumch.web.model.Course;

import java.util.List;

public interface CourseService {

    /**
     * 查询课程记录
     * @param page
     * @param pageSize
     * @param courseName
     * @return
     */
    List<Course> getCoursesByConditionsInPage(int page, int pageSize, String courseName);

    /**
     * 查询课程数量
     * @param courseName
     * @return
     */
    int getCoursesCountByCondition(String courseName);
}
