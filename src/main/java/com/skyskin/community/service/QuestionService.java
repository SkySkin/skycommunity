package com.skyskin.community.service;

import com.skyskin.community.dto.PageInfoDTO;
import com.skyskin.community.dto.QuestionDTO;
import com.skyskin.community.mapper.QuestionMapper;
import com.skyskin.community.mapper.UserMapper;
import com.skyskin.community.model.Question;
import com.skyskin.community.model.QuestionExample;
import com.skyskin.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rock
 * @createDate 2019/09/09 16:48
 * @see com.skyskin.community.service
 */
//服务，组装
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PageInfoDTO getQuestionList(Integer page, Integer size) {
        //得到条目总数
        QuestionExample example = new QuestionExample();
        Integer totalCount = (int) questionMapper.countByExample(example);
        //得到实例，以及设置page数据的值
        PageInfoDTO pageInfoDTO = new PageInfoDTO(totalCount, page, size);
        //公式: ((page-1)*5),5
        Integer offset = (pageInfoDTO.getPage() - 1) * size;
        List<Question> list = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        ArrayList<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : list) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            if (user == null) {
                user=EmptyUserGetVal(user);
            }
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question, questionDTO);
            questionDTOS.add(questionDTO);
        }
        pageInfoDTO.setQuestionDTOS(questionDTOS);

        return pageInfoDTO;
    }

    private User EmptyUserGetVal(User user) {
        if (user == null) {
            user = new User();
            user.setAvatarUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2506043363,1520693567&fm=26&gp=0.jpg");
            user.setBio("");
            user.setName("用户不存在");
            user.setToken("");
            user.setId(0);
            user.setAccountId("");
        }
        return user;
    }

    public PageInfoDTO getQuestionList(Integer userId, Integer page, Integer size) {
        //得到条目总数
//        Integer totalCount = questionMapper.countByCreator(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        //得到实例，以及设置page数据的值
        PageInfoDTO pageInfoDTO = new PageInfoDTO(totalCount, page, size);
        //公式: ((page-1)*5),5
        Integer offset = (pageInfoDTO.getPage() - 1) * size;
//        QuestionExample questionExample1 = new QuestionExample();
//        questionExample1.createCriteria().andCreatorEqualTo(userId);
        List<Question> list = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));

        ArrayList<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : list) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            if(user==null){
                user=EmptyUserGetVal(user);
            }
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageInfoDTO.setQuestionDTOS(questionDTOS);

        return pageInfoDTO;


    }


    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        if(user==null){
            user=EmptyUserGetVal(user);
        }
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //如果没有id,则是进行 新增
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);

        } else {
            //如果有ID则进行  修改
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andCreatorEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question, questionExample);
        }

    }
}
