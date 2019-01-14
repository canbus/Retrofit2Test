package com.bao.retrofit2demo;

import com.bao.retrofit2demo.entry.GanResultEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Retrofix+Rxjava:map,flatMap及filter的使用
 * 请求网址:https://gank.io/api/data/Android/10/1
 *         observeOn(AndroidSchedulers.mainThread())：订阅者的回调在主线程
 *         subscribeOn(Schedulers.io())：订阅发生在io线程
 *         map:一般我们不会关心error字段，我们关心的只是results，所以在这里做了一个映射让用户接收的是List<GankResultBean.ResultsBean>而不是包含有error的GankResultBean
 *         flatMap:让结果一条一条的发射出去，而不是一个集合
 *         filter：只接收Type为Android的数据
 */
public class Demo9 {
    private interface GankService{
        @GET("data/Android/10/{page}")
        Observable<GanResultEntry> getAndroidData(@Path("page")int page);
    }
    public static final void main(String args[]){
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GankService gankService = retrofit.create(GankService.class);
        gankService.getAndroidData(1)
                //.subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GanResultEntry, List<GanResultEntry.ResultEntry>>() {
                    @Override
                    public List<GanResultEntry.ResultEntry> call(GanResultEntry ganResultEntry) {
                        return ganResultEntry.getResults();
                    }})
                .flatMap(new Func1<List<GanResultEntry.ResultEntry>, Observable<GanResultEntry.ResultEntry>>() {
                    @Override
                    public Observable<GanResultEntry.ResultEntry> call(List<GanResultEntry.ResultEntry> resultEntries) {
                        return Observable.from(resultEntries);
                    }})
                .filter(new Func1<GanResultEntry.ResultEntry, Boolean>() {
                    @Override
                    public Boolean call(GanResultEntry.ResultEntry resultEntry) {
                        return "Android".equals(resultEntry.getType());
                    }})
                .subscribe(new Subscriber<GanResultEntry.ResultEntry>() {
                    @Override
                    public void onNext(GanResultEntry.ResultEntry resultEntry) {
                        System.out.println("onNext");
                        System.out.println(resultEntry.getDesc());
                    }
                    public void onCompleted() {System.out.println("onCompleted");}
                    public void onError(Throwable e) { System.out.println(e.toString());}
                });



    }

}
