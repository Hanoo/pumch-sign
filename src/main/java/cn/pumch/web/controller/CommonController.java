package cn.pumch.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.*;

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
                // 验证成功在Session中保存用户信息
                PsUser authUserInfo = userService.getUserByLoginName(loginName);
                request.getSession().setAttribute("userInfo", authUserInfo);
                authUserInfo.setLoginTime(new Date());
                userService.update(authUserInfo);
            } // 已经登录直接跳转
            if(subject.hasRole("mt")) {
                jump = "/mt/sList";
            } else if (subject.hasRole("t")) {
                jump = "/t/mySignIn";
            } else if (subject.hasRole("s")) {
                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                String urlBeforeRedirect = null;
                if (null != savedRequest) {
                    urlBeforeRedirect = WebUtils.getSavedRequest(request).getRequestUrl();
                }
                if (!StringUtils.isNullOrEmpty(urlBeforeRedirect) && !urlBeforeRedirect.endsWith("logout")) {
                    urlBeforeRedirect = urlBeforeRedirect.replace(request.getContextPath(), "");
                    if (urlBeforeRedirect.length() > 2) {
                        jump = urlBeforeRedirect;
                    } else {
                        jump = "/s/mySignList";
                    }
                } else {
                    jump = "/s/mySignList";
                }
            }
        } catch (AuthenticationException e) {
            // 身份验证失败
            logger.error("用户验证失败，用户名：" + loginName, e);
            json.put("result", "error");
            json.put("resultInfo", "用户名或密码错误！");
            return json;
        }

        json.put("resultInfo", jump);
        json.put("result", "success");

        logger.info(loginName+" 登录成功。");
        return json;
    }

    @RequestMapping(value = "/web/error/{eCode}")
    public String error(@PathVariable String eCode, HttpServletRequest request) {
        request.setAttribute("eCode", eCode);
        return "error";
    }

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

    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    public String updatePassword() {
        return "updatePassword";
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updatePassword(@RequestBody JSONObject queryParam, HttpSession session) {
        PsUser user = (PsUser) session.getAttribute("userInfo");
        String loginName = user.getLoginName();
        String oPassword = queryParam.getString("oPassword");
        String nPassword = queryParam.getString("nPassword");

        if (userService.updatePassword(loginName, oPassword, nPassword)) {
            queryParam.put("result", "success");
        } else {
            queryParam.put("result", "failed");
        }
        return queryParam;
    }

    private final static Logger logger = LoggerFactory.getLogger(CommonController.class);
}
