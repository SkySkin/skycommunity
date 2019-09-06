package com.skyskin.community.provider;

import com.alibaba.fastjson.JSON;
import com.skyskin.community.dto.Access_tokenDTO;
import com.skyskin.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Rock
 * @createDate 2019/09/06 14:55
 * @see com.skyskin.community.provider
 */
@Component
public class GithubProvider {
    public String  getAccessToken(Access_tokenDTO access_tokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(access_tokenDTO) );
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
