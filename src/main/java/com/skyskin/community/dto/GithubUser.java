package com.skyskin.community.dto;

import lombok.Data;

/**
 * @author Rock
 * @createDate 2019/09/06 15:34
 * @see com.skyskin.community.dto
 */
/*
{
  "login": "SkySkin",
  "id": 47810174,
  "node_id": "MDQ6VXNlcjQ3ODEwMTc0",
  "avatar_url": "https://avatars3.githubusercontent.com/u/47810174?v=4",
  "gravatar_id": "",
  "url": "https://api.github.com/users/SkySkin",
  "html_url": "https://github.com/SkySkin",
  "followers_url": "https://api.github.com/users/SkySkin/followers",
  "following_url": "https://api.github.com/users/SkySkin/following{/other_user}",
  "gists_url": "https://api.github.com/users/SkySkin/gists{/gist_id}",
  "starred_url": "https://api.github.com/users/SkySkin/starred{/owner}{/repo}",
  "subscriptions_url": "https://api.github.com/users/SkySkin/subscriptions",
  "organizations_url": "https://api.github.com/users/SkySkin/orgs",
  "repos_url": "https://api.github.com/users/SkySkin/repos",
  "events_url": "https://api.github.com/users/SkySkin/events{/privacy}",
  "received_events_url": "https://api.github.com/users/SkySkin/received_events",
  "type": "User",
  "site_admin": false,
  "name": "SkySkin",
  "company": "叮当猫教育",
  "blog": "",
  "location": null,
  "email": null,
  "hireable": null,
  "bio": "学习使用",
  "public_repos": 5,
  "public_gists": 0,
  "followers": 0,
  "following": 0,
  "created_at": "2019-02-20T07:43:04Z",
  "updated_at": "2019-09-07T11:56:02Z"
}


 */

@Data
public class GithubUser {
    private String name;
    private Long id;
    //描述
    private String bio;
    //头像
    private String avatarUrl;

}
