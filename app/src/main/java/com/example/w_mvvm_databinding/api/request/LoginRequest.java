package com.example.w_mvvm_databinding.api.request;

public class LoginRequest extends ApiRequest {
    protected String phone;
    protected String password;
    protected String cart_id;

    public LoginRequest(String phone, String password, String cart_id) {
        this.module ="app";
        this.action ="login";
        this.app ="login";
        this.phone = phone;
        this.password = password;
        this.cart_id = cart_id;
    }
}
