package com.bao.retrofit2demo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

//Field、FieldMap、Part和PartMap
public class Demo2 {
    private interface BlogService{
         /**
         * {@link FormUrlEncoded} 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
         * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
         */
         @POST("/form") //中文乱码,在build.gradle中添加compileJava.options.encoding = "UTF-8"
         @FormUrlEncoded
         @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
         Call<ResponseBody> formUrlEncoded1(@Field("username")String username,@Field("age")int age);

        /**
         * Map的Key作为表单的键
         */
        @POST("/form")
        @FormUrlEncoded
        Call<ResponseBody> formUrlEncoded2(@FieldMap Map<String,Object> map);

        /**
         * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
         * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
         */
        @POST("/form")
        @Multipart
        Call<ResponseBody> fileUpload1(@Part("name")RequestBody name, @Part("kind") RequestBody kind, @Part MultipartBody.Part file);

        /**
         * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
         * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
         * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
         */
        @POST("/form")
        @Multipart
        Call<ResponseBody> fileUpload2(@PartMap Map<String,RequestBody> args,@Part MultipartBody.Part file);

        @POST("/form")
        @Multipart
        Call<ResponseBody> fileUpload2(@PartMap Map<String,RequestBody> args);
    }
    public static final void main(String args[]) throws Exception
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .build();
        BlogService blog = retrofit.create(BlogService.class);

        // @FormUrlEncoded 和 @Field的使用
        Response<ResponseBody> response = blog.formUrlEncoded1("中文", 30).execute();
        PrintResponse.printBody(response);

        // @FormUrlEncoded 和 @Fieldmap的使用
        Map<String,Object> map = new HashMap<>();
        map.put("username","中文");
        map.put("age",40);
        Response<ResponseBody> response1 = blog.formUrlEncoded2(map).execute();
        PrintResponse.printBody(response1);

        //  @Multipart 和 @Part的使用  模拟文件上传
        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType,"文件名");
        RequestBody kind = RequestBody.create(textType,"视频");
        RequestBody file = RequestBody.create(MediaType.parse("application/octed-stream"),"这里是xx文件的内容");
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file","test.txt",file);
//        Response<ResponseBody> fileUpload1 = blog.fileUpload1(name, kind, filePart).execute();
//        PrintResponse.printBody(fileUpload1);

        Map<String,RequestBody> fileUpload2Args = new HashMap<>();
        fileUpload2Args.put("name",name);
        fileUpload2Args.put("kind",kind);
        Response<ResponseBody> fileUpload2 = blog.fileUpload2(fileUpload2Args,filePart).execute();//单独处理文件
        PrintResponse.printBody(fileUpload2);
    }
}
