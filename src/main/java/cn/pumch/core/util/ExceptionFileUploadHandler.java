package cn.pumch.core.util;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义文件上传大小异常处理器类
 * Created by a on 2017/7/17.
 */
public class ExceptionFileUploadHandler implements HandlerExceptionResolver {
    /**
     * 处理上传文件大小超过限制抛出的异常
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest req,
                                         HttpServletResponse res, Object ob, Exception ex) {
        ModelAndView mv=new ModelAndView();
        //判断异常类型，来跳转不同页面
        if (ex instanceof MaxUploadSizeExceededException){
            //指定错误信息
            req.setAttribute("errormessage", "上传文件过大");
            try{
                //设置跳转视图
                req.getRequestDispatcher("enterpriseEx")
                        .forward(req, res);
            }
            catch (Exception e){
                   req.setAttribute("errormessage","文件上传失败，请刷新页面");
            }

            return mv;
        }
        //其他异常
        return null;
    }

}
