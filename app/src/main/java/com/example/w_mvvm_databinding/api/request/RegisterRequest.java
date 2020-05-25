package com.example.w_mvvm_databinding.api.request;

public class RegisterRequest extends ApiRequest {
    private String name;
    private String account;
    private String password;
    private int active;

    public RegisterRequest(String name, String account, String password, int active) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.active = active;
        action = "register";
    }


}
