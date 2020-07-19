package com.zhangbao.eblog.common.exception;

import cn.hutool.json.JSONUtil;
import com.zhangbao.eblog.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sunny
 * @create 2020-06-2020/6/16-21:21
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws IOException {

        log.error("------------------>捕捉到全局异常", e);

        //ajax请求，弹窗显示未登录
        String header = req.getHeader("X-Requested-With");
        if(header!=null && "XMLHttpRequest".equals(header)){
            log.error("not authenticated - ajax请求未登录");
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().print(JSONUtil.toJsonStr(Result.fail("请先登录")));
            return null;
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", e.getMessage());

        if(e instanceof NullPointerException) {

        }

        // web处理
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = HwException.class)
    @ResponseBody
    public Result jsonErrorHandler(HttpServletRequest req, HwException e) {
        return Result.fail(e.getMessage(), "some error data");
    }

}
