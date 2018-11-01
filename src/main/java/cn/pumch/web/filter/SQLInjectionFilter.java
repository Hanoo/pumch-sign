package cn.pumch.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by a on 2017/8/10.
 */
public class SQLInjectionFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(SQLInjectionFilter.class);
//    private String regularExpression;

    public SQLInjectionFilter() {

    }

    public void init(FilterConfig filterConfig) throws ServletException {
//        regularExpression = filterConfig.getInitParameter("regularExpression");
    }

    /*
     * 如果需要输入“'”、“;”、“--”这些字符 可以考虑将这些字符转义为html转义字符 “'”转义字符为：&#39; “;”转义字符为：&#59;
     * “--”转义字符为：&#45;&#45;
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse)response;
        String requestUrl = req.getRequestURL().toString();
        String contextPath = req.getContextPath();
        requestUrl = requestUrl.substring(requestUrl.indexOf(contextPath)
                + contextPath.length());// 获取剥离contextPath的url路径
        Map parametersMap = request.getParameterMap();
        Iterator it = parametersMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String[] value = (String[]) entry.getValue();
            for (int i = 0; i < value.length; i++) {
                if (null != value[i] &&  sqlValidate(value[i])) {
                    logger.debug("疑似SQL注入攻击！账号：" + ((HttpServletRequest) request).getSession().getAttribute("userInfo")
                            + ";执行Action："
                            + requestUrl
                            + ";参数名称："
                            + entry.getKey().toString()
                            + ";录入信息："
                            + value[i]);
                    // 操作时有SQL注入嫌疑，跳转到错误页面。

                    if (requestUrl.indexOf("/oper/login") == -1 ) {
                        request.setAttribute("error", "输入有非法字符！");
//                       RequestDispatcher rd = req.getRequestDispatcher("404");
//                        rd.forward(req, response);
                        res.sendRedirect(req.getContextPath()+"/page/error_Character");
                        return;
                    } else {// 登陆时有SQL注入嫌疑，记录登陆日志
                        String message = "请输入正确的数据！";
                        request.setAttribute("Message", message);
                        res.sendRedirect(req.getContextPath()+"/page/error_Character");
                        return;
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
    public void destroy() {

    }
    //校验关键字
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "insert|delete|update|<!--";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }
        return false;
    }
}
