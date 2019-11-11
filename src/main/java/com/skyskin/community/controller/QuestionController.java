package com.skyskin.community.controller;

import com.skyskin.community.dto.CommentCreateDTO;
import com.skyskin.community.dto.CommentDTO;
import com.skyskin.community.dto.QuestionDTO;
import com.skyskin.community.service.CommentService;
import com.skyskin.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Rock
 * @createDate 2019/09/11 21:10
 * @see com.skyskin.community.controller
 */
//用来显示详细的question
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);

        //查询改问题下面的评论
        List<CommentDTO> commentDTOList =commentService.listByQuestionId(id);
        //进行阅读量的增加
        questionService.incView(id);
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("commentDTOList", commentDTOList);
        return "question";
    }


}
