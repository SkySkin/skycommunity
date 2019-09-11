package com.skyskin.community.service;

import com.skyskin.community.dto.PageInfoDTO;
import com.skyskin.community.dto.QuestionDTO;
import com.skyskin.community.mapper.QuestionMapper;
import com.skyskin.community.mapper.UserMapper;
import com.skyskin.community.model.Question;
import com.skyskin.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        Integer totalCount = questionMapper.count();
        //得到实例，以及设置page数据的值
        PageInfoDTO pageInfoDTO = new PageInfoDTO(totalCount,page,size);
        //公式: ((page-1)*5),5
        Integer offset=(pageInfoDTO.getPage()-1)*size;
        List<Question> list = questionMapper.getList(offset,size);

        ArrayList<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
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
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageInfoDTO.setQuestionDTOS(questionDTOS);

        return pageInfoDTO;


    }
}
