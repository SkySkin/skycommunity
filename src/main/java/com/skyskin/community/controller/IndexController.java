package com.skyskin.community.controller;

import com.skyskin.community.dto.PageInfoDTO;
import com.skyskin.community.model.User;
import com.skyskin.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rock
 * @createDate 2019/09/06 1:08
 * @see com.skyskin.community.controller
 */
@Controller
public class IndexController {



    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "limit",defaultValue = "5")Integer size) {
        User user=null;
        //得到cookie

        PageInfoDTO pageInfoDTO=questionService.getQuestionList(page,size);
        model.addAttribute("pageInfoDTO",pageInfoDTO);
        return "index";
    }

}