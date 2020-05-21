package com.example.w_mvvm_databinding.api.result;

import com.google.gson.annotations.SerializedName;

public class LoginResult extends ApiResult {
   private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
