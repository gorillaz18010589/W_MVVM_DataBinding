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

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.w_mvvm_databinding.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
