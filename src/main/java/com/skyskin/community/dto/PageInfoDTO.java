package com.skyskin.community.dto;

import com.skyskin.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rock
 * @createDate 2019/09/10 16:35
 * @see com.skyskin.community.dto
 */


/* 封装分页需要返回的数据结构*/

@Data
public class PageInfoDTO {
    private List<QuestionDTO> questionDTOS;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages=new ArrayList<>();

    public PageInfoDTO(Integer totalCount, Integer page, Integer size) {
        //定义总页数
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //当page小于1时，让它为1
        if (page<1){
            page=1;
        }
        //当page大于最大页数时，让它为1最大页数

        if (page>totalPage){
            page=totalPage;
        }
        this.page=page;
        //计算pages
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            int begin = page - i;
            int end=page+i;
            if (begin>0) {
                pages.add(0,begin);
            }
            if(end<=totalPage){
                pages.add(end);
            }
        }
        //如果当前页为1,不显示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //如果到达最后一页，不显示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }
}
