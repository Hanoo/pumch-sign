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
        if(null!=courseName) {
            courseName = "%" + courseName + "%";
        }
        return courseMapper.selectByConditionsInPage(courseName, start, pageSize);
    }

    @Override
    public int getCoursesCountByCondition(String courseName) {
        if("".equals(courseName)) {
            return 0;
        }
        if(null!=courseName) {
            courseName = "%" + courseName + "%";
        }
        return courseMapper.selectCountByCondition(courseName);
    }

    @Override
    public List<Course> getCourseByTIdInPage(int page, int pageSize, Long tId) {
        if(null==tId) {
            return null;
        }

        int start;
        if(page<1) {
            page = 1;
        }
        if(pageSize<10) {
            pageSize = 10;
        }
        start = (page -1) * pageSize;
        return courseMapper.selectByTIdInPage(tId, start, pageSize);
    }

    @Override
    public int getCourseCountByTId(Long tId) {
        if(null==tId) {
            return 0;
        }
        return courseMapper.selectCountByTId(tId);
    }

    @Override
    public String createCourse(String courseName, Long tId) {
        int courseCount = courseMapper.selectCourseByName(courseName);
        if(courseCount>0) {
            return "duplicateName";
        } else {
            Course course = new Course();
            course.setCourseName(courseName);
            course.settId(tId);

            int result = courseMapper.insert(course);
            if(result>0) {
                return "success";
            } else {
                return "failed";
            }
        }
    }
}
