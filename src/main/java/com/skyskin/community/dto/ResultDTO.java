package com.skyskin.community.dto;

import com.skyskin.community.enums.CommentEnum;
import com.skyskin.community.exception.CustomizeErrorCode;
import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import com.skyskin.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Rock
 * @createDate 2019/11/05 22:05
 * @see com.skyskin.community.dto
 * 该类用来定义返回的json响应数据格式
 */
@Data
public class ResultDTO {
    //返回给前端的状态码
    private Integer code;
    //提升信息
    private String message;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }


    /**
     * 评论错误时操作的方法
     * @param
     * @return
     */
    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    /**
     * 评论成功返回的操作
     */
    public  static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        CommentEnum success = CommentEnum.SUCCESS;
        resultDTO.setCode(success.getType());
        resultDTO.setMessage(success.getMessage());

        return  resultDTO;
    }

    /**
     * 异常处理时，返回json 格式数据
     * @param e
     * @return
     */
    public static ResultDTO errorOf(CustomizeException e)
    {
        return errorOf(e.getCode(),e.getMessage());
    }
}
