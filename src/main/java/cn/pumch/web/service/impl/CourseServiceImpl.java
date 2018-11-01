package cn.pumch.web.service.impl;

import cn.pumch.web.dao.CourseMapper;
import cn.pumch.web.model.Course;
import cn.pumch.web.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> getCoursesByConditionsInPage(int page, int pageSize, String courseName) {
        int start;
        if(page<1) {
            page = 1;
        }
        if(pageSize<10) {
            pageSize = 10;
        }
        start = (page -1) * pageSize;
        return courseMapper.selectByConditionsInPage(courseName, start, pageSize);
    }

    @Override
    public int getCoursesCountByCondition(String courseName) {
        if(!StringUtils.isEmpty(courseName)) {
            return 0;
        }
        return courseMapper.selectCountByCondition(courseName);
    }
}
