package com.skyskin.community.service;

import com.skyskin.community.mapper.UserMapper;
import com.skyskin.community.model.User;
import com.skyskin.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rock
 * @createDate 2019/09/12 13:55
 * @see com.skyskin.community.service
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user){
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        if (users.size()==0){
            //如果查询到的dbUser为空，则插入用户
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertSelective(user);
        }else {
            //如果能查询到dbUser,则修改时间和部分信息
            user.setGmtModified(System.currentTimeMillis());
            UserExample example1 = new UserExample();
            example1.createCriteria().andIdEqualTo(users.get(0).getId());
            userMapper.updateByExampleSelective(user, example1);
        }

    }

    /**
     * 得到所有的用户，该方法只在测试阶段使用
     * @return
     */
    public List<User> getAllUser() {
        List<User> users = userMapper.selectByExample(new UserExample());
        return users;
    }


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User selectUserById(Long id) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size()!=0) {
            return users.get(0);
        }
        return null;

    }
}
