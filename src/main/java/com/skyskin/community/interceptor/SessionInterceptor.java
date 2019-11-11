package com.skyskin.community.interceptor;

import com.skyskin.community.mapper.UserMapper;
import com.skyskin.community.model.User;
import com.skyskin.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Rock
 * @createDate 2019/09/11 19:31
 * @see com.skyskin.community.interceptor
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    //在执行实际处理程序之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            //遍历cookie得到名称为token的cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    if (token!=null) {
//                        System.out.println("token:"+token);

                        UserExample userExample = new UserExample();
                        userExample.createCriteria()
                                .andTokenEqualTo(token);
                        List<User> users = userMapper.selectByExample(userExample);
                        if (users.size()!=0) {
                            User user = users.get(0);
                            if (user != null) {
                                request.getSession().setAttribute("user", user);
//                                System.out.println("set Session");
                            }
                        }
                        break;
                    }
                }
            }
        }
        return true;
    }

    //在处理程序执行后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //在完成请求之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
