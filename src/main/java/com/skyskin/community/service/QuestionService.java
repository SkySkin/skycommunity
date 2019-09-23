package com.skyskin.community.service;

import com.skyskin.community.dto.PageInfoDTO;
import com.skyskin.community.dto.QuestionDTO;
import com.skyskin.community.mapper.QuestionMapper;
import com.skyskin.community.mapper.UserMapper;
import com.skyskin.community.model.Question;
import com.skyskin.community.model.QuestionExample;
import com.skyskin.community.model.User;
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
        Integer totalCount = (int)questionMapper.countByExample(example);
        //得到实例，以及设置page数据的值
        PageInfoDTO pageInfoDTO = new PageInfoDTO(totalCount,page,size);
        //公式: ((page-1)*5),5
        Integer offset=(pageInfoDTO.getPage()-1)*size;
        List<Question> list = questionMapper.getList(offset,size);

        ArrayList<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : list) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageInfoDTO.setQuestionDTOS(questionDTOS);

        return pageInfoDTO;
    }

    public PageInfoDTO getQuestionList(Integer id, Integer page, Integer size) {
        //得到条目总数
        Integer totalCount = questionMapper.countByCreator(id);
        //得到实例，以及设置page数据的值
        PageInfoDTO pageInfoDTO = new PageInfoDTO(totalCount,page,size);
        //公式: ((page-1)*5),5
        Integer offset=(pageInfoDTO.getPage()-1)*size;
        List<Question> list = questionMapper.getListByCreator(id,offset,size);

        ArrayList<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : list) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageInfoDTO.setQuestionDTOS(questionDTOS);

        return pageInfoDTO;


    }


    public QuestionDTO getById(Integer id) {
      Question question=  questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null) {
            //如果没有id,则是进行 新增
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);

        }else {
            //如果有ID则进行  修改
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }

    }
}
