package com.skyskin.community.dto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import	java.util.Date;

/**
 * @author Rock
 * @createDate 2019/09/06 14:57
 * @see com.skyskin.community.dto
 */
@Data
public class Access_tokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
