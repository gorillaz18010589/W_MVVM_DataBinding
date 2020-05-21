package com.example.w_mvvm_databinding.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.w_mvvm_databinding.R;
import com.example.w_mvvm_databinding.api.MyApi;
import com.example.w_mvvm_databinding.api.MyOkHttpApi;
import com.example.w_mvvm_databinding.api.request.ApiRequest;
import com.example.w_mvvm_databinding.api.request.LoginRequest;
import com.example.w_mvvm_databinding.api.result.ApiResponse;
import com.example.w_mvvm_databinding.api.result.LoginResult;
import com.example.w_mvvm_databinding.commom.CheckUtils;
import com.example.w_mvvm_databinding.databinding.FragmentLogInBinding;
import com.example.w_mvvm_databinding.databinding.StartFragmentBinding;
import com.example.w_mvvm_databinding.viewmodels.LoginViewModel;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LogInFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private FragmentLogInBinding fragmentLogInBinding;
    private String url ="http://10.0.8.78:8080/MyJaveEE/S17_mysql";
    private String TAG = "hank";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        fragmentLogInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false);
        fragmentLogInBinding.setHandler(this);
        fragmentLogInBinding.setViewModel(loginViewModel);
        View view = fragmentLogInBinding.getRoot();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnLogin:
//                Login();
//                loginApi();
//                userApi();
                formBodytoTomCat();
                break;
        }
    }

//    public void Login() {
//        String password = loginViewModel.password.get();
//        String phone = loginViewModel.phone.get();
//        if (CheckUtils.getInstance(getContext()).isOK(phone, password)) {
//            MyApi.getInstance().loginApi(phone, password, "").observe(getViewLifecycleOwner(), new Observer<ApiResponse<LoginResult>>() {
//                @Override
//                public void onChanged(ApiResponse<LoginResult> loginResultApiResponse) {
//                    if (loginResultApiResponse.getResult().isOk()) {
//                        String userName = loginResultApiResponse.getResult().userName;
//                        Log.v(TAG, "loginApi =>userName:" + userName + "msg:" + loginResultApiResponse.getResult().getMessage());
//                        Toast.makeText(getContext(), loginResultApiResponse.getResult().getMessage(), Toast.LENGTH_SHORT).show();
////                        String accessId = loginResultApiResponse.getResult().accessId;
////                        tokenApi(accessId);
//                    } else {
//                        String httpMsg = loginResultApiResponse.getError().getHttpMsg();
//                        Log.v(TAG, "錯誤:" + httpMsg);
//                        Toast.makeText(getContext(), loginResultApiResponse.getResult().getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            });
//        }
//
////        13232236359
////        aaaa1111
//
////        boolean isPhoneInput =  TextUtils.isEmpty(phone) || phone.length()<10;
//
////        if (!isPhoneInput && !TextUtils.isEmpty(password)) { //有輸入
////            MyApi.getInstance().loginApi(phone, password, "").observe(getViewLifecycleOwner(), new Observer<ApiResponse<LoginResult>>() {
////                @Override
////                public void onChanged(ApiResponse<LoginResult> loginResultApiResponse) {
////                    if (loginResultApiResponse.getResult().isOk()) {
////                        String userName = loginResultApiResponse.getResult().userName;
////                        Log.v(TAG, "loginApi =>userName:" + userName + "msg:" + loginResultApiResponse.getResult().getMessage());
////
////                        Toast.makeText(getContext(), loginResultApiResponse.getResult().getMessage(), Toast.LENGTH_SHORT).show();
////                    } else {
////                        Log.v(TAG, "錯誤");
////                        Toast.makeText(getContext(), loginResultApiResponse.getResult().getMessage(), Toast.LENGTH_SHORT).show();
////                    }
////                }
////            });
////
////
////        } else {//沒輸入
////            Toast.makeText(getContext(),getString(R.string.s_conFrimInput), Toast.LENGTH_SHORT).show();
////        }
//
//        Log.v(TAG,"Login" +"帳號:" + phone +"/密碼:" + password);
//    }


    //示範API
    public void loginApi() {
        Log.v(TAG,"loginApit");
        RequestBody requestBody = new FormBody.Builder()
                .add("module", "app")
                .add("action", "login")
                .add("app", "login")
                .add("phone", "13232236359")
                .add("password", "aaaa1111")
                .add("cart_id", "")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v(TAG, "失敗:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    Log.v(TAG, "onResponse成功:" + body);

                    //取得headers的name,value,hashCode
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        String name = headers.name(i);
                        String value = headers.value(i);
                        int hashCode = headers.hashCode();
                        Log.v(TAG, "headers:" + "name:" +name + "/value:" + value +"/hashCode" + hashCode);
                    }
                }
            }
        });
    }


    public void tokenApi(String accessId) {
        Log.v(TAG, "tokenApi");
        LoginResult loginResult = new LoginResult();
        RequestBody requestBody = new FormBody.Builder()
                .add("module", "app")
                .add("action", "login")
                .add("app", "token")
                .add("access_id", accessId)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v(TAG, "失敗:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.v(TAG, "onResponse成功:" + body);

                    //取得headers的name,value,hashCode
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        String name = headers.name(i);
                        String value = headers.value(i);
                        int hashCode = headers.hashCode();
                        Log.v(TAG, "headers:" + "name:" + name + "/value:" + value + "/hashCode" + hashCode);
                    }
                }
            }
        });
    }

    //用Json方式傳送參數,給後端
    public void userApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "carter");
        map.put("account", "87654321@gmail.com");
        map.put("password", "aaavvvv");

        Gson gson = new Gson();
        String json = gson.toJson(map);
        Log.v(TAG,"json:" + json);

        RequestBody requestBody = RequestBody.create(mediaType, json);

        Request request = new Request.Builder()
                .url("http://10.0.8.78:8080/MyJaveEE/S17_mysql")
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

    public void formBodytoTomCat(){
        Map<String,String> map = new HashMap<>();
        map.put("name", "formbody");
        map.put("account", "addasdas@gmail.com");
        map.put("password", "123456");
      MyOkHttpApi.instance().doPostFormBody("http://10.0.8.78:8080/MyJaveEE/T1_formBody", map, new MyOkHttpApi.OkHttpCallBack() {
          @Override
          public void onFailure(IOException e) {
              Log.v(TAG,"onFailure()成功");
          }

          @Override
          public void onSuccess(Response response) {
                if(response.isSuccessful()){
                    try {
                        String body = response.body().string();
                        Log.v(TAG,"formBodytoTomCat()成功:" + body);
                         Headers headers = response.headers();
                         for(int i=0; i<headers.size(); i++){
                             String name = headers.name(i);
                             String vlaue = headers.value(i);
                             Log.v(TAG,"headerName:" + name +":" + vlaue);
                         }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
          }
      });


    }

}
