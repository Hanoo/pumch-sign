package cn.pumch.web.controller;

import cn.pumch.core.util.JsonDateValueProcessor;
import cn.pumch.web.enums.UserType;
import cn.pumch.web.model.Course;
import cn.pumch.web.model.PsUser;
import cn.pumch.web.service.PsUserService;
import cn.pumch.web.service.CourseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 管理教师专属分发器
 */
@Controller
@RequestMapping(value = "/mt")
public class MTController {

    @Autowired
    private PsUserService userService;

    @Autowired
    private CourseService courseService;

    private final static Logger logger = LoggerFactory.getLogger(MTController.class);
    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        PsUser userInfo = (PsUser) session.getAttribute("userInfo");
        session.removeAttribute("userInfo");
        // 登出操作
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        logger.info("用户"+userInfo.getLoginName()+"登出成功！");
        return "login";
    }

    @RequestMapping(value = "/crusr", method = RequestMethod.POST)
    public String createUser(){
        return "";
    }

    @RequestMapping(value = "/sList", method = RequestMethod.GET)
    public String studentsListPage() {
        return "sList";
    }


    @RequestMapping(value = "/tList", method = RequestMethod.GET)
    public String teacherListPage() {
        return "tList";
    }

    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject userListQuery(@RequestBody JSONObject queryParam) {
        int page = 1;
        int pageSize = 10;
        Object nickNameInP = queryParam.get("nickName");
        String nickName = null;
        Object startTime = queryParam.get("startTime");
        Object endTime = queryParam.get("endTime");
        String state = queryParam.getString("state");
        String userType = queryParam.getString("userType");

        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(null != queryParam.get("currentPageIndex")) {
            int pageIndex = Integer.valueOf(queryParam.get("currentPageIndex").toString());
            if(pageIndex>1) {
                page = pageIndex;
            }
        }

        if(null != queryParam.get("pageSize")) {
            int countInP = Integer.valueOf(queryParam.get("pageSize").toString());
            if(countInP<1) {
                pageSize = 0;
            } else {
                pageSize = countInP;
            }
        }

        JSONObject jsonObject = new JSONObject();

        try {
            if(null!=nickNameInP && !StringUtils.isEmpty(nickNameInP.toString())) {
                nickName = nickNameInP.toString();
            }
            if(null!=startTime && !StringUtils.isEmpty(startTime.toString())) {
                startDate = sdf.parse(startTime.toString());
            }
            if(null!=endTime && !StringUtils.isEmpty(endTime.toString())) {
                endDate = sdf.parse(endTime.toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            jsonObject.put("error", "Invalid Args");
            return jsonObject;
        }
        List<PsUser> dataList;
        int totalRecord;
        if(UserType.S.getRole_name().equals(userType)) {
            dataList = userService.findStudentsInPage(page, pageSize, nickName, null, null, state, startDate, endDate);
            totalRecord = userService.findStudentsCount(nickName, null, null, state, startDate, endDate);
        } else {
            dataList = userService.findTeachersInPage(page, pageSize, nickName, null, null, state, startDate, endDate);
            totalRecord = userService.findTeachersCount(nickName, null, null, state, startDate, endDate);
        }
        jsonObject.put("totalRecord", totalRecord);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm"));
        PropertyFilter filter = new PropertyFilter() {
            public boolean apply(Object object, String fieldName, Object fieldValue) {
                if(fieldValue instanceof List){
                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>) fieldValue;
                    if (list.size()==0) {
                        return true;
                    }
                }
                //过滤条件：值为null时过滤
                return null == fieldValue;
            }
        };
        jsonConfig.setJsonPropertyFilter(filter);

        jsonObject.put("data", JSONArray.fromObject(dataList, jsonConfig));

        return jsonObject;
    }

    @RequestMapping(value = "/courseList", method = RequestMethod.GET)
    public String courseListPage() {
        return "courseList";
    }

    @RequestMapping(value = "/courseList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject courseList(@RequestBody JSONObject queryParam) {
        int page = 1;
        int pageSize = 10;
        if(null != queryParam.get("currentPageIndex")) {
            int pageIndex = Integer.valueOf(queryParam.get("currentPageIndex").toString());
            if(pageIndex>1) {
                page = pageIndex;
            }
        }

        if(null != queryParam.get("pageSize")) {
            int countInP = Integer.valueOf(queryParam.get("pageSize").toString());
            if(countInP<1) {
                pageSize = 0;
            } else {
                pageSize = countInP;
            }
        }

        String courseName = queryParam.getString("courseName");
        if(StringUtils.isEmpty(courseName)) {
            courseName = null;
        }

        List<Course> dataList = courseService.getCoursesByConditionsInPage(page, pageSize, courseName);
        int totalRecord = courseService.getCoursesCountByCondition(courseName);
        queryParam.put("totalRecord", totalRecord);
        queryParam.put("data", JSONArray.fromObject(dataList));

        return queryParam;
    }
}
