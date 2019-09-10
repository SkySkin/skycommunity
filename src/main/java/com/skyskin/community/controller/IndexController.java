package com.skyskin.community.controller;

import com.skyskin.community.dto.QuestionDTO;
import com.skyskin.community.mapper.UserMapper;
import com.skyskin.community.model.User;
import com.skyskin.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Rock
 * @createDate 2019/09/06 1:08
 * @see com.skyskin.community.controller
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,Model model) {
        //得到cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            //遍历cookie得到名称为token的cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }

        }
        List<QuestionDTO> questionList=questionService.getQuestionList();
        model.addAttribute("question",questionList);
        return "index";
    }

}