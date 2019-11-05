package com.skyskin.community.controller;

import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * 该类的作用为捕获不能处理的异常
 * 参考的是springboot的官方文档，以及
 * org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
 * @author Rock
 * @createDate 2019/11/05 16:39
 * @see com.skyskin.community.controller
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Model model) {
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()) {
            //是4xx则是客户端错误
            model.addAttribute("message", CustomizeErrorCodeImpl.QUESTION_NOT_FOUND.getMessage());
        }
        if (status.is5xxServerError()) {
            //是5xx则是服务端错误
            model.addAttribute("message", CustomizeErrorCodeImpl.IS5XX_ERROR.getMessage());

        }
//        response.setStatus(status.value());
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
