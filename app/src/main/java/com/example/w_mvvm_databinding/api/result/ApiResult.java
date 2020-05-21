package com.example.w_mvvm_databinding.api.result;
//1.因為response回來,固定有回傳code,跟message,因此在這裡先設定是否成功,跟取得message
public abstract class ApiResult {
    private static final int STATE_SUCCESS = 200;
    private int code; //伺服器返回的狀態碼
    private String message; //伺服器返回的成功/失敗資訊

    //判斷是否成功
    public boolean isOk(){
        if(code == STATE_SUCCESS);
        return true;
    }

    //伺服器返回的成功/失敗資訊
    public String getMessage(){
        return  message;
    }

    public int getCode() {
        return code;
    }
}
