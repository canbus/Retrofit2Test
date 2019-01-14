package com.bao.retrofit2demo;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class PrintResponse {
    public static void printBody(Response<ResponseBody> response) throws Exception{
        if(response.code() == 200){
            StringBuilder sb = new StringBuilder();
            sb.append(response.message()).append("\r\n")
                    .append(response.body().string());
            System.out.println(sb.toString());
        }else{
            System.out.println(response.code() + "/" + response.message());
        }
    }
}
