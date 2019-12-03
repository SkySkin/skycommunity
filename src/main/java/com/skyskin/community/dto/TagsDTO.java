package com.skyskin.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Rock
 * @createDate 2019/11/15 22:02
 * @see com.skyskin.community.dto
 */
@Data
public class TagsDTO {
    //类别名
    private String categoryName;
    //该类别下的tags
    private List<String> tags;
}
