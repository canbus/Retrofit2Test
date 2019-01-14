package com.bao.retrofit2demo;

import com.bao.retrofit2demo.entry.Blog;
import com.bao.retrofit2demo.entry.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

//和RxJava结合
public class Demo7 {
    private interface BlogServer{
        @GET("blog/{id}") //http://localhost:4567/blog/2
        //Call<Result<Blog>> getBlogs(@Path("id")int id);
        Observable<Result<Blog>> getBlog(@Path("id")int id);

        @GET("blog")//http://localhost:4567/blog?page=2
        Observable<Result<List<Blog>>> getBlogs(@Query("page")int page);
    }
    public static final void main(String args[]) throws Exception{
        Gson gson = new GsonBuilder().setDateFormat("yy/MM/dd HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        BlogServer server = retrofit.create(BlogServer.class);
        server.getBlog(1)
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<Result<Blog>>() {
                    public void onCompleted() {System.out.println("onCompleted"); }
                    public void onError(Throwable e) { }
                    public void onNext(Result<Blog> blogResult) {
                        System.out.println(new Gson().toJson(blogResult.getData())); //调用gson.toJson会出错
                    }
                });

        server.getBlogs(2)
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<Result<List<Blog>>>() {
                    public void onCompleted() {System.out.println("onCompleted"); }
                    public void onError(Throwable e) { }
                    public void onNext(Result<List<Blog>> listResult) {
                        System.out.println(new Gson().toJson(listResult.getData()));
                    }
                });

        new Thread(new Runnable() { //运行一次线程可以让上面的toJson正常运行，原因未知
            @Override
            public void run() {
                new Gson().toJson(new Object());
            }
        }).start();
    }
}
