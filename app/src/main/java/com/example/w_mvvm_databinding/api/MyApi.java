package com.example.w_mvvm_databinding.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.w_mvvm_databinding.api.request.ApiRequest;
import com.example.w_mvvm_databinding.api.request.LoginRequest;
import com.example.w_mvvm_databinding.api.request.RegisterRequest;
import com.example.w_mvvm_databinding.api.result.ApiResponse;
import com.example.w_mvvm_databinding.api.result.ApiResult;
import com.example.w_mvvm_databinding.api.result.LoginResult;
import com.example.w_mvvm_databinding.api.result.RegisterResult;
import com.example.w_mvvm_databinding.commom.GsonUtlis;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyApi {
    private static MyApi instance;
    private static OkHttpClient okHttpClient;
    private static String TAG = "hank";
    private static String MSG = "MyApi=>";
    private static final String URL ="http://10.0.8.78:8080/MyJaveEE/WexLu";


    //1.getInstance 物件實體化
    public static MyApi getInstance() {
        if (instance == null) instance = new MyApi();
        Log.v(TAG,"getInstance:" + instance.toString());
        return instance;
    }

    //2.被建構式時okHttpClient物件實體建立
    public  MyApi() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
//                    .sslSocketFactory(getSSLSocketFactory())
                    .build();
            Log.v(TAG, MSG + "MyApi():" + okHttpClient.toString());
        }
    }



    //5.Login
    public MutableLiveData<ApiResponse<LoginResult>> loginApi(String phone, String password, String cart_id) {
        ApiRequest request = new LoginRequest(phone, password, cart_id);
        MutableLiveData<ApiResponse<LoginResult>> response = new MutableLiveData<>();
        Log.v(TAG, "loginApi:" + "request:" + request.toString() + "response:" + response.toString());
        Callback callback =new ApiResultCallBack<LoginResult>(response,LoginResult.class);
        doFormBody(request,callback);
        return response;
    }



    public MutableLiveData<ApiResponse<RegisterResult>> RegisterApi (String name, String account, String password){
        ApiRequest apiRequest = new RegisterRequest(name,account,password,0);
        MutableLiveData<ApiResponse<RegisterResult>> response = new MutableLiveData<>();
        Log.v(TAG, "loginApi:" + "request:" + apiRequest.toString() + "response:" + response.toString());
        Callback callback = new ApiResultCallBack<>(response,RegisterResult.class);
        doPostJson(apiRequest,callback);
        return response;
    }

    /*4.doFormBody,將Bean轉成key,value送到body走Post傳送
    1.@param ApiRequest apiRequest => 1.要送出的requestBean
    2.@param Callback apiResponseCallBack => 2.自己定義的apiResponseCallBack(1.MutanData的Reponse資料,2.要respone轉成bean的類別)
    */
    public  void doFormBody(ApiRequest apiRequest,Callback apiResponseCallBack) {
        Log.v(TAG,"doFormBody:" +"ApiRequest:" +apiRequest.toString());
        //A.將Bean轉成Map
        Map<String, String> map = javaBeanToMap(apiRequest);

        //B.將Map尋訪將資料用key:value給formBodyBuilder.add加進去
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                Log.v(TAG, "key:" + key + "/value:" + value);
                formBodyBuilder.add(key, value);
            }
        }

        //C.Request設定
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder()
                .url(URL)
                .post(formBody)
                .build();

        //D.enqueue.自己寫的CallBack
        okHttpClient.newCall(request).enqueue(apiResponseCallBack);
    }





    //6.需要實作CallBack讓回來的response自己定義處理
    public abstract class MyCallBack implements Callback{ //A.實作Callback介面

        //B.實作CallBack方法,當連線失敗時
        @Override
        public void onFailure(Call call, IOException e) {

        }

        //B.實作CallBack方法,當連線成功時,將body跟,call傳遞出去
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            //D.將需要的call,response.body()將參數用變形方法傳遞
            String body = response.body().string();
            Log.v(TAG,"MyCallBack: => onResponse" + "body:" + body);
               int code = response.code();

            //C.如果連線成功將call,body交給自己寫的變形方法onResult讓call跟body給後代來玩
            if (response.isSuccessful()) {
                onResult(call, body);

            }else {//連線失敗的話
                onError(call,response.code(),response.message(),body);
            }

        }


        /*C.abstract方法要玩的是Call跟Response,所以寫這個抽象方法拿到call,response,讓後代去實作
        1.@param:Call call => 1.將call給後代去玩
        2.@param:String responseBody => 2.將responseBody給後代去玩
        * */
       public abstract void onResult(Call call, String responseBody);

       /*E.abstract方法當失敗時Code,msg,body給後代實做
       * @param:Call call => 1.call物件
       * @param:code => 2.伺服器狀態Code
       * @param:msg => 3.伺服器訊息
       * @param:body => 4.response.body
       * */
       public abstract void onError(Call call, int code, String msg, String body);
    }

    //7.繼承自己寫的MyCallBack,可以玩 onResult(Call call, String responseBody等,就可以玩
    public class ApiResultCallBack<T extends ApiResult> extends MyCallBack {
        MutableLiveData<ApiResponse<T>> liveData; //MutableLiveData,要玩PostValue
        Class<T> clazz; //有繼承ApiResult的Bean


        /*A.建構式即可玩到LiveData<ApiResponse<T>>,跟有繼承ApiResult的Bean
         * 1.@param:MutableLiveData<ApiResponse<T>>liveData => 1.要response回來的MutableLiveData,主要設定postValue給ApiResponse
         * 2.@param:Class<T> clazz => 2.要用JsonFrom轉型回來存放的類別
         * */
        public ApiResultCallBack(MutableLiveData<ApiResponse<T>> liveData, Class<T> clazz) {
            this.liveData = liveData;
            this.clazz = clazz;
        }


        /*B.玩call,跟responseBody,JsonFrom拿到的bean存到<T> result,Data用postValue將result送到ApirResponse建構式時拿到bean
         * */
        @Override
        public void onResult(Call call, String responseBody) {
            Gson gson = new Gson();
            Log.v(TAG, "ApiResultCallBack => onResult(call,responseBody):" + "responseBody:" + responseBody);
            //C.將回傳來的responseBody,轉成bean
            T result = gson.fromJson(responseBody, clazz);

            //E.
            onResult(result);

            //D.將result用liveData走背景設定值,建構式實可以拿回值
            liveData.postValue(new ApiResponse<T>(result));

        }

        //C.當連線失敗時將code,msg,body,用liveData的psotValue將值傳response去建構式
        @Override
        public void onError(Call call, int code, String msg, String body) {
            liveData.postValue(new ApiResponse<T>(code, msg, body));
        }

        public void onResult(T result) {
        }
    }


    /*3.javabean轉成Map
     *@param =>Object javabean:預轉成Map的javabean
     *@return =>Map<String, String>:回傳JsonMapo格式
     * */
    public Map<String, String> javaBeanToMap(Object javabean) {
        Gson gson = new Gson();

        //將傳進來的bean轉成Json
        String json = gson.toJson(javabean);//toJson(Object src):將物件轉成字串Json(要被轉成Json的物件)(回傳String)
        Log.v(TAG, "gson.toJson:" + json); //{"cart_id":"aaa","}

        //將Json格式Sting資料,轉成Map<String, String>
        //fromJson(String json, Type typeOfT):從Json字串解析回傳到資料結構上(1.要轉型的Json字串,2.要轉型的資料結構類型)(回傳<T> T)
        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType()); //getType():取得底層資料結構的種類(回傳Type)

        Log.v(TAG, "map:" + map.toString());
        return map;
    }


    public void doPostJson(ApiRequest apiRequest, Callback apiResponseCallBack) {
        Log.v(TAG, "doPostJson:" + "ApiRequest:" + apiRequest.toString());

        //A.將Bean轉成Map
        Map<String, String> map = javaBeanToMap(apiRequest);

        //B.將Map轉成Json
        String json = GsonUtlis.getInstance().toJson(map);
        Log.v(TAG,"doPostJson()=>" +"json:" +json);

        //設定contentType
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(mediaType, json);

        Request request = new Request.Builder()
                .url("http://10.0.8.78:8080/MyJaveEE/WexLu")
                .post(requestBody)
                .build();

        //D.enqueue.自己寫的CallBack
        okHttpClient.newCall(request).enqueue(apiResponseCallBack);
    }

}
