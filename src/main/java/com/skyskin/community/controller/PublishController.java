package com.skyskin.community.controller;

import com.skyskin.community.cache.TagsCache;
import com.skyskin.community.dto.QuestionDTO;
import com.skyskin.community.dto.TagsDTO;
import com.skyskin.community.mapper.QuestionMapper;
import com.skyskin.community.model.Question;
import com.skyskin.community.model.User;
import com.skyskin.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rock
 * @createDate 2019/09/07 20:09
 * @see com.skyskin.community.controller
 */
@Controller
public class PublishController {



    @Autowired
    private QuestionService questionService;



    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tagsDTOS",TagsCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value ="description",required = false) String description,
            @RequestParam(value ="tag",required = false) String tag,
            @RequestParam(value ="id",required = false) Long id,
            HttpServletRequest request,
            Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || "".equals(title)) {
            model.addAttribute("error", "问题标题不能为空");
            return "publish";
        }
        if (description == null || "".equals(description)) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || "".equals(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        User user= (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        //进行对于标签的判断
        //1.得到所有填所的标签
        String[] tags = tag.split(",");
        //2.得到预定义的所有的标签
        List<TagsDTO> tagsDTOS = TagsCache.get();
        //3.设置缓存

        String string = TagsCache.tagsToString();
        //4.进行循环比较是否存在改标签
        for (int i = 0; i < tags.length; i++) {
            if (string.indexOf(tags[i])==-1) {
                model.addAttribute("error", "含有非法标签:"+tags[i]);
                return "publish";
            }
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tagsDTOS",TagsCache.get());
        return "publish";
    }
}
