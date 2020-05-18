package com.example.w_mvvm_databinding.commom;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.w_mvvm_databinding.R;

public class UiAnimation {
    private static UiAnimation instance;


    public static UiAnimation getInstance(){
        if(instance == null) instance = new UiAnimation();
        return instance;
    }

    public Animation getTopAnimation(Context context){
       return  AnimationUtils.loadAnimation(context, R.anim.bottom_animation);
    }

    public Animation getBottomAnimation(Context context){
        return  AnimationUtils.loadAnimation(context, R.anim.top_animation);
    }
}
