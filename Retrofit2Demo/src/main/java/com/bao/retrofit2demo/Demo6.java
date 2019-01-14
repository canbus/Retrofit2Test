package com.bao.retrofit2demo;

import com.bao.retrofit2demo.entry.Blog;
import com.bao.retrofit2demo.entry.Result;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

//演示如使创建一个Blog
public class Demo6 {
    private interface BlogServer{
        //@Body注解的的Blog将会被Gson转换成RequestBody发送到服务器。
        @POST("blog")
        Call<Result<Blog>> createBlog(@Body Blog blog);
    }
    public final static void main(String args[]){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BlogServer blogServer = retrofit.create(BlogServer.class);
        Blog blog = new Blog();
        blog.setTitle("Blog创建测试");
        blog.setAuthor("作者");
        blog.setTitle("标题");
        blog.setContent("Blog内容");
        blogServer.createBlog(blog).enqueue(new Callback<Result<Blog>>() {
            @Override
            public void onResponse(Call<Result<Blog>> call, Response<Result<Blog>> response) {
                if(response.code() == 200){
                    System.out.println(new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Result<Blog>> call, Throwable t) {

            }
        });
    }
}
