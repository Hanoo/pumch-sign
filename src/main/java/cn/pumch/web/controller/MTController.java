package cn.pumch.web.controller;

import cn.pumch.core.util.ZipUtils;
import cn.pumch.web.enums.CourseType;
import cn.pumch.web.enums.UserType;
import cn.pumch.web.model.Course;
import cn.pumch.web.model.PsUser;
import cn.pumch.web.model.Role;
import cn.pumch.web.model.SignIn;
import cn.pumch.web.redis.JedisWrap;
import cn.pumch.web.service.PsUserService;
import cn.pumch.web.service.CourseService;
import cn.pumch.web.service.RoleService;
import cn.pumch.web.service.SignInService;
import cn.pumch.web.util.CommonUtils;
import cn.pumch.web.util.MSExcelReader;
import cn.pumch.web.util.QRCodeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @Autowired
    private SignInService signInService;

    @Autowired
    private JedisWrap jedisService;

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
        if(userService.createUser(user, roleId)>0l) {
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
        String state = "1";
        if(null!=queryParam.get("state")) {
            state = queryParam.getString("state");
        }
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
        queryParam.put("data", JSONArray.fromObject(dataList, CommonUtils.getJsonConfig()));

        return queryParam;
    }

    @RequestMapping(value = "/qrCode/{courseId}", method = RequestMethod.GET)
    public void qrCode(@PathVariable String courseId, HttpServletRequest request, HttpServletResponse response) {
        String serverPath = request.getScheme() +"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String url = serverPath + "s/signIn/" + courseId;
        try {
            QRCodeUtil.generateQRCode(url,500, 500, "jpg", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/genQrCode/{courseId}", method = RequestMethod.GET)
    public void genQrCode(@PathVariable Long courseId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Course course = courseService.getCourseById(courseId);
        if(null==course) {
            logger.warn("不合法的课程ID！");
            return;
        } else {
            String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String url = serverPath + "/s/signIn/" + courseId;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateSuffix = sdf.format(new Date());

            String basePath = "/var/pumchSign/qrCode/";
            String fileName = URLEncoder.encode(course.getCourseName(), "UTF-8");
            File directory = new File(basePath + courseId + "_" + dateSuffix + "/");
            if (!directory.exists()) {
                directory.mkdir();
            }

            long startTime = System.currentTimeMillis();
            String[] uuids = new String[50];
            for (int i = 0; i < 50; i++) {
                String uuid = CommonUtils.getUUId();
                String qrCode = directory.getPath() + "/" + dateSuffix + "-" + (i + 1) + ".jpg";
                QRCodeUtil.generateQRCode(url + "/" + dateSuffix + "/" + uuid, 200, 200, "jpg", qrCode);
                uuids[i] = uuid;
            }
            String redisKey = "signIn:" + dateSuffix;
            jedisService.addSetEle(redisKey, uuids);
            jedisService.setExpire(redisKey, 3600 * 24);
            long endTime = System.currentTimeMillis();
            logger.info("生成二维码消耗时间：" + (endTime - startTime) / 1000);

            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + "_" + dateSuffix + ".zip");
            response.setContentType("application/octet-stream;charset=UTF-8");
            ZipUtils.toZip(directory.getPath(), response.getOutputStream(), true);
        }
    }

    @RequestMapping(value = "/newCourse", method = RequestMethod.GET)
    public String newCourse(HttpServletRequest request) {
        List dataList = userService.findTeachersInPage(0, 50, null, null, null, "1", null, null);
        request.setAttribute("data", JSONArray.fromObject(dataList));
        return "newCourse";
    }

    @RequestMapping(value = "/newCourse", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject newCourse(@RequestBody JSONObject queryParam) {
        Long tId = queryParam.getLong("tId");
        String courseName = queryParam.getString("courseName");
        String courseType = queryParam.getString("courseType");

        String result = courseService.createCourse(courseName, courseType, tId);
        queryParam.put("result", result);
        return queryParam;
    }

    @RequestMapping(value = "/excelImport", method = RequestMethod.POST)
    public String excelImport(HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        InputStream in = null;
        List<List<Object>> listob = null;
        MultipartFile file = multipartRequest.getFile("upfile");

        if(file.isEmpty()){
            throw new Exception("文件不存在！");
        }
        in = file.getInputStream();
        listob = MSExcelReader.getBankListByExcel(in, file.getOriginalFilename());
        in.close();

        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> row = listob.get(i);
            String tName = (String) row.get(1);
            Long tUserId = userService.createTUserWithNickName(tName);

            String cName = (String) row.get(2);
            String displayWord = (String) row.get(3);
            String courseType;
            if(CourseType.REQUIRED.getDisplayWord().equals(displayWord)) {
                courseType = CourseType.REQUIRED.getCourseCode();
            } else {
                courseType = CourseType.OPTIONAL.getCourseCode();
            }

            String result = courseService.createCourse(cName, courseType, tUserId);
            if("success".equals(result) || "duplicateName".equals(result)) {
                logger.info("课程：" + cName + "创建成功，" + "任课教师为："+ tName);
            }
        }

        return "tList";
    }

    @RequestMapping(value = "/ajaxImport", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxImport(HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("upfile");

        if(file.isEmpty()){
            throw new Exception("文件体不存在！");
        }
        InputStream in = file.getInputStream();
        List<List<Object>> rowList = MSExcelReader.getBankListByExcel(in, file.getOriginalFilename());
        in.close();

        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < rowList.size(); i++) {
            List<Object> row = rowList.get(i);
            String tName = (String) row.get(1);
            Long tUserId = userService.createTUserWithNickName(tName);

            String cName = (String) row.get(2);
            String displayWord = (String) row.get(3);
            String courseType;
            if(CourseType.REQUIRED.getDisplayWord().equals(displayWord)) {
                courseType = CourseType.REQUIRED.getCourseCode();
            } else {
                courseType = CourseType.OPTIONAL.getCourseCode();
            }

            String result = courseService.createCourse(cName, courseType, tUserId);
            if("success".equals(result) || "duplicateName".equals(result)) {
                logger.info("课程：" + cName + "创建成功，" + "任课教师为："+ tName);
            } else {
                logger.warn("课程：" + cName + "创建失败！");
            }
        }

        return "success";
    }

    @RequestMapping(value = "/signInList", method = RequestMethod.GET)
    public String signInList() {
        return "signInList";
    }

    @RequestMapping(value = "/signList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject signList(@RequestBody JSONObject queryParam) {
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

        Date startTime = null;
        Date endTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss");

        String courseName = null;
        if(StringUtils.isNotEmpty(queryParam.getString("courseName"))) {
            courseName = queryParam.getString("courseName");
        }

        String sName = null;
        if(StringUtils.isNotEmpty(queryParam.getString("sName"))) {
            sName = queryParam.getString("sName");
        }

        try {
            if (StringUtils.isNotEmpty(queryParam.getString("startTime"))) {
                startTime = sdf.parse(queryParam.getString("startTime"));
            }
            if (StringUtils.isNotEmpty(queryParam.getString("endTime"))) {
                endTime = sdf.parse(queryParam.getString("endTime"));
            }
            List<SignIn> dataList = signInService.getSignInListInPage(page, pageSize, courseName, sName, startTime, endTime);
            int totalRecord = signInService.getSignInCount(courseName, sName, startTime, endTime);

            queryParam.put("data", JSONArray.fromObject(dataList, CommonUtils.getJsonConfig()));
            queryParam.put("totalRecord", totalRecord);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.debug("查询签到列表成功！");
        return queryParam;
    }

    @RequestMapping(value = "/exportSignInList", method = RequestMethod.GET)
    public void exportSignInList(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ServletOutputStream output = response.getOutputStream();
        String courseName = request.getParameter("courseName");
        if(StringUtils.isEmpty(courseName)) {
            response.setContentType("text/html;charset=utf-8");
            output.write("未指定导出课程，导出失败！".getBytes("UTF-8"));
            return;
        }

        Date startTime;
        Date endTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss");

        try {
            if (StringUtils.isNotEmpty(request.getParameter("startTime"))) {
                startTime = sdf.parse(request.getParameter("startTime"));
            } else {
                response.setContentType("text/html;charset=utf-8");
                output.write("未指定导出开始时间！".getBytes("UTF-8"));
                return;
            }
            if (StringUtils.isNotEmpty(request.getParameter("endTime"))) {
                endTime = sdf.parse(request.getParameter("endTime"));
            } else {
                // 不指定结束时间就查当天的
                Calendar instance = Calendar.getInstance();
                instance.setTime(startTime);
                instance.set(Calendar.HOUR_OF_DAY, 23);
                instance.set(Calendar.MINUTE, 59);
                instance.set(Calendar.SECOND, 59);
                endTime = instance.getTime();
            }
        } catch (ParseException e) {
            response.setContentType("text/html;charset=utf-8");
            output.write("时间格式化错误导致导出失败！".getBytes("UTF-8"));
            return;
        }

        HSSFWorkbook workbook = signInService.buildWorkbook(courseName, startTime, endTime);
        response.setContentType("application/binary;charset=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode("调查问卷导出"+sdf.format(startTime)
                        + "-" + sdf.format(endTime)+".xls", "UTF-8"));
        workbook.write(output);
        output.flush();
        output.close();    }
}
