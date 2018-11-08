package cn.pumch.web.controller;

import cn.pumch.web.enums.UserType;
import cn.pumch.web.model.Course;
import cn.pumch.web.model.PsUser;
import cn.pumch.web.model.Role;
import cn.pumch.web.service.PsUserService;
import cn.pumch.web.service.CourseService;
import cn.pumch.web.service.RoleService;
import cn.pumch.web.util.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private RoleService roleService;

    private final static Logger logger = LoggerFactory.getLogger(MTController.class);

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newUser(HttpServletRequest request) {
        List<Role> roleList = roleService.selectList();
        request.setAttribute("roleList", JSONArray.fromObject(roleList));
        return "newUser";
    }

    @RequestMapping(value = "/crusr", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject createUser(@RequestBody JSONObject queryParam){
        String loginName = queryParam.getString("loginName");
        String nickName = queryParam.getString("nickName");
//        String sex = queryParam.getString("sex");
        String idNo = queryParam.getString("idNo");
        Long roleId = queryParam.getLong("roleId");

        PsUser user = new PsUser();
        user.setIdNo(idNo);
        user.setNickName(nickName);
        user.setLoginName(loginName);
//        user.setSex(sex);

        JSONObject result = new JSONObject();
        if(userService.createUser(user, roleId)) {
            logger.info("用户"+nickName+"创建成功！");
            result.put("result", "success");
        } else {
            logger.warn("用户创建失败！");
            result.put("result", "failed");
        }
        return result;
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

        jsonObject.put("data", JSONArray.fromObject(dataList, CommonUtils.getJsonConfig()));

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
