package com.example.w_mvvm_databinding.commom;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.w_mvvm_databinding.R;

public  class CheckUtils {
    private static CheckUtils instance;
    private Context context;
    private String TAG = "hank";
    public static  CheckUtils getInstance(Context context){
        if(instance == null) instance = new CheckUtils(context);
        return instance;
    }

    public CheckUtils(Context context){
        this.context = context;
    }

    /*檢查帳號密碼是否有輸入,且帳號不得大於10碼,且帳密不能小於4碼
    * 1.@param:String phone => 使用者手機
    * 2.@param:String pwd => 使用者密碼
    * */
    public boolean isOK(String phone, String pwd) {
        boolean isOk = true;
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd)) {
            //帳號跟密碼部為空

            //如果密碼大於10碼
            if (phone.length() > 15) {
                Toast.makeText(context, context.getString(R.string.s_to_many_word), Toast.LENGTH_SHORT).show();
                isOk = false;
                Log.v(TAG, "帳號大於15碼:" + "isOk:" + isOk);
            }

            //如果密碼跟帳號小於4碼
            if (phone.length() < 4 || pwd.length() < 4) {
                Toast.makeText(context, context.getString(R.string.s_too_few_word), Toast.LENGTH_SHORT).show();
                isOk = false;
                Log.v(TAG, "密碼或帳號小於4碼:" + "isOk:" + isOk);
            }


        } else {
            //帳號或密碼為空
            Toast.makeText(context, context.getString(R.string.s_conFrimInput), Toast.LENGTH_SHORT).show();
            isOk = false;
            Log.v(TAG, "帳號或密碼為空:" + "isOk:" + isOk);
        }

        Log.v(TAG, "isOk:" + isOk);

        return isOk;
    }


    public boolean isEnterThree(String str1, String str2, String str3) {
        boolean isEnter = false;
        if (str1 != null && str2 != null && str3 != null) {
            //有輸入
            if (TextUtils.isEmpty(str1.trim()) || TextUtils.isEmpty(str2.trim()) || TextUtils.isEmpty(str3.trim())) {
                //有少輸入
                Toast.makeText(context, "欄位不能為空", Toast.LENGTH_SHORT).show();
            } else {
                //輸入都有填
                isEnter = true;
            }

        } else {

            Toast.makeText(context, "欄位不能為空", Toast.LENGTH_SHORT).show();
        }
        return isEnter;
    }
}
