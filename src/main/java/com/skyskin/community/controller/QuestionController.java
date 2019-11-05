package com.skyskin.community.controller;

import com.skyskin.community.dto.QuestionDTO;
import com.skyskin.community.mapper.QuestionMapper;
import com.skyskin.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        //进行阅读量的增加
        questionService.incView(id);
        model.addAttribute("questionDTO", questionDTO);
        return "question";
    }


}
