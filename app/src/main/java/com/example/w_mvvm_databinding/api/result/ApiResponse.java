package com.example.w_mvvm_databinding.api.result;

import android.util.Log;

import com.example.w_mvvm_databinding.api.request.ApiRequest;

//https://github.com/zhou-you/RxEasyHttp/tree/master/app/src
//https://github.com/zhou-you/RxEasyHttp
//https://codertw.com/android-%E9%96%8B%E7%99%BC/24722/
public class ApiResponse <T extends ApiResult> {
    private T result; //bean資料內容
    private Throwable exception; //拋出例外
    private Error error; //連線失敗時的物件

    public ApiResponse(){

    }

    //4.在連線失敗時建構式取得Http訊息
    public ApiResponse(int code, String msg, String responseBody){
         this.error = new Error(code,msg,responseBody);
    }

    //3.在postValue中建構時拿會自己的bean資料內容
    public ApiResponse(T result){
        this.result = result;
        Log.v("hank","ApiResponse(T result)建構式" + result.toString());
    }

    //5.讓外面得人可以玩到Error物件
    public Error getError() {
        return error;
    }

    //1.取得<T extends ApiResult>物件
    public T getResult() {
        return result;
    }

    //2.
    public Throwable getException(){
        return  exception;
    }


    //4.寫一個Error類別裡面有取得Http錯誤訊息的三種方法
    public static class Error {
        private int httpCode;
        private String httpMsg;
        private String httpBody;

        public Error(int httpCode, String httpMsg, String httpBody) {
            this.httpCode = httpCode;
            this.httpMsg = httpMsg;
            this.httpBody = httpBody;
        }

        public int getHttpCode() {
            return httpCode;
        }

        public String getHttpMsg() {
            return httpMsg;
        }

        public String getHttpBody() {
            return httpBody;
        }
    }
}

