package com.skyskin.community.dto;

/**
 * @author Rock
 * @createDate 2019/09/06 15:34
 * @see com.skyskin.community.dto
 */
public class GithubUser {
    private String name;
    private Long id;
    //描述
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
