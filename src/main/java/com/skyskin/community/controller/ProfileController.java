package com.skyskin.community.controller;

import com.skyskin.community.dto.PageInfoDTO;
import com.skyskin.community.model.User;
import com.skyskin.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rock
 * @createDate 2019/09/11 15:32
 * @see com.skyskin.community.controller
 */
@Controller
public class ProfileController {



    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action")String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size) {
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }

            //如果是跳转到显示我的问题
        if ("questions".equals(action)) {
            PageInfoDTO pageInfoDTO=questionService.getQuestionList(user.getId(),page,size);
            model.addAttribute("pageInfoDTO",pageInfoDTO);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("repies".equals(action)) {
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }
}
