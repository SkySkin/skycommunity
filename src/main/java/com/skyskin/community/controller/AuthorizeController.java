package com.skyskin.community.controller;

import com.skyskin.community.dto.Access_tokenDTO;
import com.skyskin.community.dto.GithubUser;
import com.skyskin.community.model.User;
import com.skyskin.community.provider.GithubProvider;
import com.skyskin.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @author Rock
 * @createDate 2019/09/06 14:43
 * @see com.skyskin.community.controller
 */
@Controller
/*授权控制器*/
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @Autowired
    private UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           @RequestParam(name = "uri", required = false) String uri,
                           HttpServletRequest request,
                           HttpServletResponse response) {
//        System.out.println("uri:"+uri);
//        System.out.println("state:"+state);
//        System.out.println("code:"+code);
        Access_tokenDTO access_tokenDTO = new Access_tokenDTO();
        access_tokenDTO.setCode(code);
        access_tokenDTO.setRedirect_uri(redirect_uri);
        access_tokenDTO.setClient_id(client_id);
        access_tokenDTO.setClient_secret(client_secret);
        access_tokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(access_tokenDTO);
        GithubUser getGithubUser = githubProvider.getUser(accessToken);
        if (getGithubUser != null && getGithubUser.getId() != null) {
            //登陆成功，写cookie和session
            User user = new User();
            if (StringUtils.isBlank(getGithubUser.getName())) {
                user.setName(getGithubUser.getLogin());
            } else {
                user.setName(getGithubUser.getName());

            }
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(getGithubUser.getId()));
            user.setBio(getGithubUser.getBio());
            user.setAvatarUrl(getGithubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            Cookie loginCookie = new Cookie("token", token);
            loginCookie.setMaxAge(14 * 24 * 60 * 60);
            response.addCookie(loginCookie);
            //判断是否有请求跳转的参数
            if (uri != null && !StringUtils.isBlank(uri)) {
                return "redirect:" + uri + "";

            }
            return "redirect:/";
        } else {
            //登陆失败，重新登陆
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         String code) {
        request.getSession().removeAttribute("user");
        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        response.addCookie(token);
//        if (code!=null)
//            return
        return "redirect:/";

    }

    /**
     * 用于进行内部的直接登陆
     */
    @GetMapping("/admin/{value}")
    public String logInside(@PathVariable(name = "value")String value,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            Model model){

      if (value.equals("981484060rootlogin")){
            //得到所有的用户
            List<User> users=userService.getAllUser();
            model.addAttribute("users",users);
            request.getSession().setAttribute("admin",true);
            return "adminLoginPage";
        }

        //得到是否有系统管理登陆的状态
        boolean attribute = (boolean) request.getSession().getAttribute("admin");
        if (attribute){
            //如果有状态，则进行用户的查询和token的记录

            User user =userService.selectUserById(Long.parseLong(value));
                if (user!=null) {
                    //得到的用户不为空则进行cookie写入
                    String token = user.getToken();
                    Cookie loginCookie = new Cookie("token", token);
                    loginCookie.setMaxAge(14 * 24 * 60 * 60);
                    response.addCookie(loginCookie);
                    request.getSession().setAttribute("user", user);
                }else {
                    return "redirect:/admin/adminLoginPage";
                }

        }
        return "redirect:/";


    }


}
