package cn.pumch.web.controller;

import cn.pumch.web.model.Course;
import cn.pumch.web.model.PsUser;
import cn.pumch.web.model.SignIn;
import cn.pumch.web.service.CourseService;
import cn.pumch.web.service.SignInService;
import cn.pumch.web.util.CommonUtils;
import cn.pumch.web.util.QRCodeUtil;
import io.netty.handler.codec.http.HttpResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *  教师页面专属分发器
 */
@Controller
@RequestMapping("/t")
public class TController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/mySignIn", method = RequestMethod.GET)
    public String mySignIn() {
        return "tMySignInList";
    }

    @RequestMapping(value = "/signInList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject signInList(@RequestBody JSONObject queryParam, HttpSession session) {
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

        String courseName = null;
        String sName = null;
        Date startTime = null;
        Date endTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss");
        PsUser psUser = (PsUser) session.getAttribute("userInfo");

        if(StringUtils.isNotEmpty(queryParam.getString("courseName"))) {
            courseName = queryParam.getString("courseName");
        }
        if (StringUtils.isNotEmpty(queryParam.getString("sName"))) {
            sName = queryParam.getString("sName");
        }
        try {
            if (StringUtils.isNotEmpty(queryParam.getString("startTime"))) {
                startTime = sdf.parse(queryParam.getString("startTime"));
            }
            if (StringUtils.isNotEmpty(queryParam.getString("endTime"))) {
                endTime = sdf.parse(queryParam.getString("endTime"));
            }
            List<SignIn> dataList = signInService.getTSignInListInPage(page, pageSize, psUser.getId(), courseName, sName);
            int totalRecord = signInService.getTSignInCount(psUser.getId(), courseName, sName);

            queryParam.put("data", JSONArray.fromObject(dataList, CommonUtils.getJsonConfig()));
            queryParam.put("totalRecord", totalRecord);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.debug("查询签到列表成功！");
        return queryParam;
    }

    @RequestMapping(value = "/qrCode/{courseId}", method = RequestMethod.GET)
    public void qrCode(@PathVariable String courseId, HttpServletRequest request, HttpServletResponse response) {
        String serverPath = request.getScheme() +"://" + request.getServerName() + ":" + request.getServerPort();
        String url = serverPath + "/s/signIn/" + courseId;
        try {
            QRCodeUtil.generateQRCode(url,500, 500, "jpg", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/courseList4T", method = RequestMethod.GET)
    public String courseList4T() {
        return "courseList4T";
    }

    @RequestMapping(value = "/courseList4T", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject courseList4T(@RequestBody JSONObject queryParam, HttpSession session) {
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
        PsUser psUser = (PsUser) session.getAttribute("userInfo");

        List<Course> dataList = courseService.getCourseByTIdInPage(page, pageSize, psUser.getId());
        int totalRecord = courseService.getCourseCountByTId(psUser.getId());
        queryParam.put("data", JSONArray.fromObject(dataList, CommonUtils.getJsonConfig()));
        queryParam.put("totalRecord", totalRecord);

        return queryParam;
    }

    private final static Logger logger = LoggerFactory.getLogger(TController.class);
}
