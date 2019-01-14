package com.bao.retrofit2demo;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
//Header和Headers 示例
public class Demo3 {
    private interface BlogServer{
        //演示 @Headers 和 @Header
        @GET("headers?showAll=true")
        @Headers({"CustomHeader1:customHeadValue1","CustomHeader2:customHeadValue2"})
        Call<ResponseBody> setHeader(@Header("CustomHeader3")String customHeaderValue3);
    }
    public final static void main(String args[]) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/").build();
        BlogServer blogServer = retrofit.create(BlogServer.class);
        Response<ResponseBody> response = blogServer.setHeader("headerValue3").execute();
        PrintResponse.printBody(response);
    }

}
