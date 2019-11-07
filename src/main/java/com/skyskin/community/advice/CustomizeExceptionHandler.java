package com.skyskin.community.advice;

import com.skyskin.community.dto.ResultDTO;
import com.skyskin.community.exception.CustomizeErrorCode;
import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import com.skyskin.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rock
 * @createDate 2019/11/05 14:53
 * @see com.skyskin.community.advice
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handler(HttpServletRequest request, Throwable e, Model model) {
        e.printStackTrace();
//        HttpStatus status = getStatus(request);
        //===修改码mark=1000 对于返回的信息格式进行逻辑处理【text/html,application/json  】===
        //得到数据的格式
        String contentType = request.getContentType();

        //对格式进行判断

        if ("application/json".equalsIgnoreCase(contentType)) {
            //返回json
            if (e instanceof CustomizeException) {
                //得到的是可识别的错误时，讲该错误的信息的进行传递
                return ResultDTO.errorOf((CustomizeErrorCode) e);
            } else {
                //当服务不能识别的错误出现时，所传的信息
                return ResultDTO.errorOf(CustomizeErrorCodeImpl.IS5XX_ERROR);
//                model.addAttribute("message", CustomizeErrorCodeImpl.IS5XX_ERROR.getMessage());
            }
            //mark=1000 end
        } else {

            if (e instanceof CustomizeException) {
                //得到的是可识别的错误时，讲该错误的信息的进行传递
                model.addAttribute("message", e.getMessage());
            } else {
                //当服务不能识别的错误出现时，所传的信息
                model.addAttribute("message", CustomizeErrorCodeImpl.IS5XX_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}


//获取状态码。
//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }

