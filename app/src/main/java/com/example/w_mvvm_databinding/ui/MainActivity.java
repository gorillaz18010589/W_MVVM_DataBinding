package com.example.w_mvvm_databinding.ui;
//1.加入API:
/* Navigation:
* def nav_version = "2.3.0-alpha06"
* implementation "androidx.navigation:navigation-fragment:$nav_version"
* implementation "androidx.navigation:navigation-ui:$nav_version"
* */
//2.res/navigation創建資源區
//3.創建graph視野圖
//4.主要Activity拉入NavHostFrgament
//5.加入start頁面
//6.新加入google sign_in功能

/*GoogleSignInAccount => 使用者登入帳號的訊息物件
* */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.w_mvvm_databinding.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG ="hank";
    private static final int  REQUEST_CODE_SIGN_IN  =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        StartFragmentBinding startFragmentBinding = DataBindingUtil.setContentView(this,R.layout.start_fragment); //綁定activity要呈現的view,並取得dataBinding物件(1.欲綁定的頁面,2.要呈現的layout頁面)
//        startFragmentBinding.googleBtn.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED)
        {
            //沒有權限,去要權限
            ActivityCompat.requestPermissions(this,//1.這個this content
                    new String[]{Manifest.permission.FOREGROUND_SERVICE},//2.要求的權限擺在陣列可以要求多個權限
                    3);//權限要得如何回應code
        }else{
            //有權限
            initService();
        }

    }

    //當使用者選擇了授權或不授權
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.v("hank","onRequestPermissionsResult();");


        if(requestCode == 3){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                initService();
            }else{
                finish();
            }

        }
    }

    //推波Serivce
    private void initService() {
        Intent intentService = new Intent(this, RegisterService.class);
        startService(intentService);
        Log.v("hank","initService()");

    }

    //1.Googl_Sign_in
    private void googleSignIn() {

        //2.設定googleSignInOptions的基本設定
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //3.取得GoogleSignInClient,裡要他的intent,傳到acitvityForResulut
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        startActivityForResult(googleSignInClient.getSignInIntent(),REQUEST_CODE_SIGN_IN);
        Log.v(TAG,"googleSignIn()");
    }

    //4.接收到InIntent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                    if(requestCode == REQUEST_CODE_SIGN_IN){
                        Log.v(TAG,"REQUEST_CODE_SIGN_IN");
                        handleSiginResult(data);
                    }


        }


    private void handleSiginResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        String email = googleSignInAccount.getEmail();//取得Google-email
                        String displayName = googleSignInAccount.getDisplayName();//取得Google-名稱
                        String familyName = googleSignInAccount.getFamilyName(); //取得Google-姓氏
                        String givenName = googleSignInAccount.getGivenName(); //取得Google-指定的名稱
                        Set<Scope> scopeSet = googleSignInAccount.getGrantedScopes(); //取得Google允許的權限範圍
                        String id = googleSignInAccount.getId(); //取得Google-ID
                        String token = googleSignInAccount.getIdToken();  //取得Google-Token
                        Uri photoUrl = googleSignInAccount.getPhotoUrl(); //取得Google-大頭貼照片網址
                        String serverAuthCode = googleSignInAccount.getServerAuthCode();
                        Log.v(TAG, "addOnSuccessListener =>  googleAccount.getEmail():" + email + "\n" + "/displayName:" + displayName + "\n" + "/familyName:" + familyName + "\n" + "/givenName:" + givenName + "\n" + "/id:" + id + "\n" + "/token:" + token + "\n" + "/serverAuthCode:" + serverAuthCode + photoUrl );
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v(TAG,"onFailure()" + e.toString());
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.google_btn:
                googleSignIn();
                Log.v(TAG,"activity:" +"=>google_btn");
                break;
        }
    }
}
