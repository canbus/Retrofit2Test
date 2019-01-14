package com.bao.retrofit2demo;

import java.io.BufferedReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

/**
 * Retrofit 练习，原文:https://www.jianshu.com/p/308f3c54abdd
 */

public class Demo1 {
    private interface BlogServer{
        /**
         * 基本使用:http://localhost:4567/blog/2
         * @GET("/blog/{id}")
         * @HTTP(method = "GET",path="blog/{id}",hasBody = false) //method区分大小写
         */
        //@GET("/blog/{id}")
        @HTTP(method = "GET",path="blog/{id}",hasBody = false)
        Call<ResponseBody> getBlog(@Path("id")int id);
    }
    public final static  void main(String args[]) throws Exception{
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/").build();
        BlogServer blog = retrofit.create(BlogServer.class);
        Response<ResponseBody> response= blog.getBlog(2).execute();
        PrintResponse.printBody(response);
    }

}
