package com.skyskin.community.controller;

import com.skyskin.community.dto.Access_tokenDTO;
import com.skyskin.community.dto.GithubUser;
import com.skyskin.community.mapper.UserMapper;
import com.skyskin.community.model.User;
import com.skyskin.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request){
        Access_tokenDTO access_tokenDTO = new Access_tokenDTO();
        access_tokenDTO.setCode(code);
        access_tokenDTO.setRedirect_uri(redirect_uri);
        access_tokenDTO.setClient_id(client_id);
        access_tokenDTO.setClient_secret(client_secret);
        access_tokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(access_tokenDTO);
        GithubUser getGithubUser = githubProvider.getUser(accessToken);
        if (getGithubUser!=null) {
            //登陆成功，写cookie和session
            User user= new User();
            user.setName(getGithubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(getGithubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user",getGithubUser);
            return "redirect:/";
        }else {
            //登陆失败，重新登陆
            return "redirect:/";
        }
    }
}
