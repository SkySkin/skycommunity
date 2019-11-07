package com.skyskin.community.controller;

import com.skyskin.community.dto.CommentDTO;
import com.skyskin.community.dto.ResultDTO;
import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import com.skyskin.community.mapper.CommentMapper;
import com.skyskin.community.model.Comment;
import com.skyskin.community.model.User;
import com.skyskin.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
     * @param commentDTO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object insertComment(@RequestBody CommentDTO commentDTO,
                                HttpServletRequest request){
        //判断当前用户是否登陆
        User user = (User) request.getSession().getAttribute("user");
        if (user==null) {
            return ResultDTO.errorOf(CustomizeErrorCodeImpl.NTO_LOGIN_ERROR);

        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1);
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
