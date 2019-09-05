package com.skyskin.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Rock
 * @createDate 2019/09/06 1:08
 * @see com.skyskin.community.controller
 */
@Controller
public class GreetingController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}