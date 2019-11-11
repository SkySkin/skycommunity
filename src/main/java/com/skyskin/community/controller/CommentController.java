package com.skyskin.community.controller;

import com.skyskin.community.dto.CommentCreateDTO;
import com.skyskin.community.dto.ResultDTO;
import com.skyskin.community.dto.UserDTO;
import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import com.skyskin.community.mapper.CommentMapper;
import com.skyskin.community.model.Comment;
import com.skyskin.community.model.User;
import com.skyskin.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rock
 * @createDate 2019/11/05 21:25
 * @see com.skyskin.community.controller
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    /**
     * @ResponseBody 返回的数据直接变为json
     * @RequestBody 传入的数据序列化为实例DTO
     * @param commentCreateDTO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object insertComment(@RequestBody CommentCreateDTO commentCreateDTO,
                                HttpServletRequest request){
        //判断当前用户是否登陆
        User user = (User) request.getSession().getAttribute("user");
        if (user==null) {

            return ResultDTO.errorOf(CustomizeErrorCodeImpl.NTO_LOGIN_ERROR);

        }
        //判断页面传的值和内容是否为空
        if ( commentCreateDTO==null || StringUtils.isBlank(commentCreateDTO.getContent())) {

            return ResultDTO.errorOf(CustomizeErrorCodeImpl.COMMENT_CONTENT_ISEMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }


    /**
     * @ResponseBody 返回的数据直接变为json
     * @RequestBody 传入的数据序列化为实例DTO
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addCommentInHtml",method = RequestMethod.POST)
    public Object addCommentInHtml(HttpServletRequest request){
        //判断当前用户是否登陆
        User user = (User) request.getSession().getAttribute("user");
        if (user==null) {

            return ResultDTO.errorOf(CustomizeErrorCodeImpl.NTO_LOGIN_ERROR);

        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }

}
