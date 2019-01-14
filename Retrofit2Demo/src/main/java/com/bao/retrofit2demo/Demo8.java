package com.bao.retrofit2demo;

import com.bao.retrofit2demo.entry.MovieEntry;
import com.bao.retrofit2demo.entry.RepoEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
//
public class Demo8 {
    //https://api.douban.com/v2/movie/top250?start=0&count=2
    private interface MoviceService{
        @GET("v2/movie/top250")
        Observable<MovieEntry> getTop250(@Query("start")int start, @Query("count")int count);
    }

    //https://api.github.com/users/canbus/repos
    private interface GitHubService{
        @GET("users/{user}/repos")
        Observable<List<RepoEntry>> getRepos(@Path("user")String user);
    }

    public static final void main(String args[]) {
//        getTop250();
        getRepos();
    }
    private static void getRepos()
    {   Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);
        gitHubService.getRepos("canbus")
                .subscribe(new Subscriber<List<RepoEntry>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<RepoEntry> repoEntries) {
                        System.out.println(new Gson().toJson(repoEntries));
                    }
                });
    }
    private static void getTop250(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                //.client(httpClient) //增加调试输出
                .baseUrl("https://api.douban.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        final MoviceService moviceService = retrofit.create(MoviceService.class);
        moviceService.getTop250(0,2)
                //.observeOn(Schedulers.io())
                .subscribe(new Subscriber<MovieEntry>() {
                    public void onCompleted() {  }
                    public void onError(Throwable e) { System.out.println(e.toString());}
                    public void onNext(MovieEntry movieEntry) {
                        System.out.println(new Gson().toJson(movieEntry));
                    }
                });
    }

}
