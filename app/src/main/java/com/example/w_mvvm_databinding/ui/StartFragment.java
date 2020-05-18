package com.example.w_mvvm_databinding.ui;
/*
        * inflate( //fragment綁定Layout
          1. LayoutInflater inflater, => 1.綁定的Layout
          2. int layoutId, =>2.要綁定的LayoutId
          3. ViewGroup parent, => 3.父類別的ViewGroup
          4. boolean attachToParent) =>4.是否直接將layout的元件加入到父類別畫面
          * 回傳(<T extends ViewDataBinding> T)
 * */
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w_mvvm_databinding.R;
import com.example.w_mvvm_databinding.commom.UiAnimation;
import com.example.w_mvvm_databinding.databinding.StartFragmentBinding;
import com.example.w_mvvm_databinding.viewmodels.StartViewModel;

public class StartFragment extends Fragment  {
    private StartViewModel mViewModel;
    private StartFragmentBinding startFragmentBinding;
    private NavController navController;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        startFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.start_fragment, container, false);
        View view = startFragmentBinding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {

        navController = Navigation.findNavController(getView());
        startFragmentBinding.txtDescription.setAnimation(UiAnimation.getInstance().getTopAnimation(getContext()));
        startFragmentBinding.imgLogo.setAnimation(UiAnimation.getInstance().getBottomAnimation(getContext()));
        startFragmentBinding.setHandler(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),"1秒到",Toast.LENGTH_SHORT).show();
            }
        },1000);
    }

    public void onclick(View v){
        switch (v.getId()){
            case R.id.btnLogin:
                navController.navigate(R.id.action_startFragment_to_logInFragment);
                Log.v("hank","btnLogin");
                break;

            case R.id.btnRegister:
                navController.navigate(R.id.action_startFragment_to_registerFragment);
                Log.v("hank","btnRegister");
                break;
        }

    }


}
