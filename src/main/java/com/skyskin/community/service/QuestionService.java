package com.skyskin.community.service;

import com.skyskin.community.dto.PageInfoDTO;
import com.skyskin.community.dto.QuestionDTO;
import com.skyskin.community.exception.CustomizeErrorCodeImpl;
import com.skyskin.community.exception.CustomizeException;
import com.skyskin.community.mapper.QuestionExtMapper;
import com.skyskin.community.mapper.QuestionMapper;
import com.skyskin.community.mapper.UserMapper;
import com.skyskin.community.model.Question;
import com.skyskin.community.model.QuestionExample;
import com.skyskin.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    private QuestionExtMapper questionExtMapper;

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
        QuestionExample questionExample = new QuestionExample();
        //设置排序的条件
        questionExample.setOrderByClause("gmt_modified DESC");
        List<Question> list = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));
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
            user.setId(0L);
            user.setAccountId("");
        }
        return user;
    }

    public PageInfoDTO getQuestionList(Long userId, Integer page, Integer size) {
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


    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null) {
            throw new CustomizeException(CustomizeErrorCodeImpl.RUSULT_NOT_FOUND);
        }
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
            question.setCommentCount(0);
            question.setViewCount(0);
            //使用 insertSelective 防止阅读数、评论数 等值为空时 报错
            questionMapper.insertSelective(question);

        } else {
            //如果有ID则进行  修改
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int i = questionMapper.updateByExampleSelective(question, questionExample);
            if (i!=1) {
                throw new CustomizeException(CustomizeErrorCodeImpl.RUSULT_NOT_FOUND);
            }
        }

    }

    /**
     * 用于更新阅读数
     */
    public void incView(Long id) {
        //存在阅读数bug的代码
//        Question question = questionMapper.selectByPrimaryKey(id);
//        Question updateQuestion = new Question();
//        updateQuestion.setViewCount(question.getViewCount()+1);
//        QuestionExample questionExample = new QuestionExample();
//        questionExample.createCriteria()
//                .andIdEqualTo(id);
//        questionMapper.updateByExampleSelective(updateQuestion,questionExample);
        Question record = new Question();
        record.setViewCount(1);
        record.setId(id);
        questionExtMapper.incView(record);
    }
}
