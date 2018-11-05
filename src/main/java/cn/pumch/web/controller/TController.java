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
 *  教师页面专属分发器
 */
@Controller
@RequestMapping("/t")
public class TController {

    @Autowired
    private SignInService signInService;

    @RequestMapping(value = "/mySignIn", method = RequestMethod.GET)
    public String mySignIn() {
        return "mySignIn";
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
            List<SignIn> dataList = signInService.getMySignInInPage(page, pageSize, psUser.getId(), courseName, sName);
            int totalRecord = signInService.getMySignInCount(psUser.getId(), courseName, sName);

            queryParam.put("data", JSONArray.fromObject(dataList, CommonUtils.getJsonConfig()));
            queryParam.put("totalRecord", totalRecord);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return queryParam;
    }

    private final static Logger logger = LoggerFactory.getLogger(TController.class);
}
