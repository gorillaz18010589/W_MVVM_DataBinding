package com.example.w_mvvm_databinding.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.w_mvvm_databinding.R;
import com.example.w_mvvm_databinding.api.MyOkHttpApi;
import com.example.w_mvvm_databinding.commom.CheckUtils;
import com.example.w_mvvm_databinding.databinding.FragmentRegisterBinding;
import com.example.w_mvvm_databinding.viewmodels.RegisterViewModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;
    private FragmentRegisterBinding fragmentRegisterBinding;
    private String url ="http://10.0.8.78:8080/MyJaveEE/T3_text_DB_void";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        fragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        fragmentRegisterBinding.setViewModel(registerViewModel);
        fragmentRegisterBinding.setHandler(this);

        View view = fragmentRegisterBinding.getRoot();

        return view;
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnRegister:
                Log.v("hank","btnRegister()");
                register();
                break;
        }
    }

    private void register() {
        String name = registerViewModel.name.get();
        String account = registerViewModel.account.get();
        String password = registerViewModel.password.get();
        if(CheckUtils.getInstance(getContext()).isEnterThree(name,account,password)){
//            regsiterApi(name,account,password);
            registerJsonApi(name,account,password);
        }
    }


    public void regsiterApi(String name ,String account , String password){


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("account",account);
        map.put("password",password);
//        map.put("hash","w");
//        map.put("active","2");


        Gson gson = new Gson();
        String json = gson.toJson(map);
        Log.v(TAG,"json:" + json);

        RequestBody requestBody = RequestBody.create(mediaType, json);

        Request request = new Request.Builder()
                .url("http://10.0.8.78:8080/MyJaveEE/T3_text_DB_void")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v(TAG,"失敗:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.v(TAG,"成功:" + response.body().string());
                }
            }
        });
    }

    public void registerJsonApi(String name, String account, String password) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("account", account);
            jsonObject.put("password", password);
//            jsonObject:{"name":"gfd","account":"rr","password":"re"}

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("hank","jsonObject:" + jsonObject);

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody =  RequestBody.create(mediaType,jsonObject.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    Log.v(TAG,"body:" + body);
                }
            }
        });
    }

}
