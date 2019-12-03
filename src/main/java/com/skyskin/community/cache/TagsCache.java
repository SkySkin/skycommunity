package com.skyskin.community.cache;

import com.skyskin.community.dto.TagsDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rock
 * @createDate 2019/11/15 22:01
 * @see com.skyskin.community.cache
 */
public class TagsCache {
    static String[] CategoryNames = {"开发语言,developmentlang", "平台框架,platformFrame", "服务器,server", "数据库,database", "开发工具,developmentTools", "其他,others"};
    static String[][] alltags = {
            {
                    "javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "c#", "swift", "sass", "bash", "ruby", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"
            },
            {
                    "ruby-on-rails", "laravel", "spring", "express", "django", "flask yii", "tornado", "koa", "struts", "springboot", "springcloud", "springData", "spring(all)", "mybatis", "ROM", "javaWeb"
            },
            {
                    "linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存", "tomcat", "负载均衡", "unix", "hadoop", "windows-server"
            },
            {
                    "mysql", "redis", "mongodb", "sql", "oracle", "nosql", "memcached", "sqlserver", "postgresql", "sqlite"
            },
            {
                    "git", "github", "visual-studio-code", "vim", "sublime-text", "xcode", "intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom", "emacs", "textmate hg",
            },
            {
                    "广告", "求职", "招聘","开心"
            }
    };

    /**
     * 得到所以分类的所以标签
     *
     * @return
     */
    public static List<TagsDTO> get() {

        List<TagsDTO> tagsDTOS = new ArrayList<>();
        for (int i = 0; i < CategoryNames.length; i++) {
            TagsDTO tagsDTO = new TagsDTO();
            tagsDTO.setCategoryName(CategoryNames[i]);
            ArrayList<String> tags = new ArrayList<>();
            for (int i1 = 0; i1 < alltags[i].length; i1++) {
                tags.add(alltags[i][i1]);
            }
            tagsDTO.setTags(tags);
            tagsDTOS.add(tagsDTO);
        }
        return tagsDTOS;
    }

    public static String tagsToString() {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < alltags.length; i++) {
            String[] alltag = alltags[i];
            String collect = Arrays.stream(alltag).collect(Collectors.joining(","));
            if (i == 0) {
                stringBuffer.append(collect);
            } else {
                stringBuffer.append("," + collect);
            }
        }
        return stringBuffer.toString();

    }
}
