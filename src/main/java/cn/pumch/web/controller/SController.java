package cn.pumch.web.controller;

import cn.pumch.web.model.PsUser;
import cn.pumch.web.model.SignIn;
import cn.pumch.web.service.SignInService;
import cn.pumch.web.util.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 学生页面专属分发器
 */
@Controller
@RequestMapping("/s")
public class SController {

    @Autowired
    private SignInService signInService;

    @RequestMapping(value = "/mySignList", method = RequestMethod.GET)
    public String mySignListPage() {
        return "sMySignInList";
    }

    @RequestMapping(value = "/signList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject signList(@RequestBody JSONObject queryParam, HttpSession session) {
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
            List<SignIn> dataList = signInService.getSSignInListInPage(page, pageSize, psUser.getId(), courseName, sName);
            int totalRecord = signInService.getSSignInCount(psUser.getId(), courseName, sName);

            queryParam.put("data", JSONArray.fromObject(dataList, CommonUtils.getJsonConfig()));
            queryParam.put("totalRecord", totalRecord);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.debug("查询签到列表成功！");
        return queryParam;
    }

    @RequestMapping(value = "/signIn/{courseId}")
    @ResponseBody
    public String doSignIn(@PathVariable String courseId, HttpSession session) {
        PsUser user = (PsUser) session.getAttribute("userInfo");
        logger.info("学生" + user.getNickName() + "签到成功！");
        return "success";
    }

    private final static Logger logger = LoggerFactory.getLogger(SController.class);
}
