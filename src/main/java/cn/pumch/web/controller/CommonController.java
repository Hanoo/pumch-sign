package cn.pumch.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.pumch.web.model.PsUser;
import cn.pumch.web.service.PsUserService;
import com.mysql.jdbc.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 通用控制器
 **/
@Controller
public class CommonController {

    @Resource
    private PsUserService userService;

    @RequestMapping(value = {"/","/login","/web/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 用户登录
     *
     * @param request
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/web/login", method = RequestMethod.POST)
    public JSONObject doLogin(HttpServletRequest request, @RequestBody JSONObject json) {

        String loginName = json.getString("loginName");
        String password = json.getString("password");
        String jump = "s";

        try {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                // 身份验证
                subject.login(new UsernamePasswordToken(loginName, password));
                if(subject.hasRole("mt")) {
                    jump = "/mt/sList";
                } else if (subject.hasRole("t")) {
                    jump = "t";
                }
                // 验证成功在Session中保存用户信息
                PsUser authUserInfo = userService.getUserByLoginName(loginName);
                request.getSession().setAttribute("userInfo", authUserInfo);
                authUserInfo.setLoginTime(new Date());
                userService.update(authUserInfo);
            } // 已经登录直接跳转
        } catch (AuthenticationException e) {
            // 身份验证失败
            logger.error("用户验证失败，用户名：" + loginName, e);
            json.put("result", "error");
            json.put("resultInfo", "用户名或密码错误！");
            return json;
        }

        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        String urlBeforeRedirect = null;
        if (null != savedRequest) {
            urlBeforeRedirect = WebUtils.getSavedRequest(request).getRequestUrl();
        }
        if (!StringUtils.isNullOrEmpty(urlBeforeRedirect) && !urlBeforeRedirect.endsWith("logout")) {
            urlBeforeRedirect = urlBeforeRedirect.replace(request.getContextPath(), "");
            if (urlBeforeRedirect.length() > 2) {
                json.put("result", "success");
                json.put("resultInfo", urlBeforeRedirect);
                return json;
            }
        }

        json.put("resultInfo", jump);
        json.put("result", "success");

        logger.info(loginName+" 登录成功。");
        return json;
    }

    private final static Logger logger = LoggerFactory.getLogger(CommonController.class);
}
