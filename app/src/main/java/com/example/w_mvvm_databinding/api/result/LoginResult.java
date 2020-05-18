package com.example.w_mvvm_databinding.api.result;

import com.google.gson.annotations.SerializedName;

public class LoginResult extends ApiResult {
    @SerializedName("access_id")
    public String accessId;
    @SerializedName("user_name")
    public String userName;
    @SerializedName("relationId")
    public String relationId;
    @SerializedName("mch_status")
    public String mchStatus;
    @SerializedName("headimgurl")
    public String headImgurl;
    @SerializedName("y_password")
    public String yPassword;
    @SerializedName("wx_status")
    public String wxStatus;


}
