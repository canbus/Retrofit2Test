package com.bao.retrofit2demo;

import com.bao.retrofit2demo.entry.Blog;
import com.bao.retrofit2demo.entry.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

//gson Converter 反序化,泛型的使用
public class Demo5 {
    //http://localhost:4567/blog/2
    private interface BlogServer{
        @GET("blog/{id}")
        Call<Result<Blog>> getBlog(@Path("id") int id);
    }

    public final static void main(String args[]) throws Exception{
        final Gson gson = new GsonBuilder().setDateFormat("yy/MM/dd HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        BlogServer blogServer = retrofit.create(BlogServer.class);
        blogServer.getBlog(2).enqueue(new Callback<Result<Blog>>() {
            @Override
            public void onResponse(Call<Result<Blog>> call, Response<Result<Blog>> response) {
                if(response.code() == 200){
                    System.out.println(gson.toJson(response));
                    System.out.println(gson.toJson(response.body().getData()));
                }
            }

            @Override
            public void onFailure(Call<Result<Blog>> call, Throwable t) {}
        });
    }
}
