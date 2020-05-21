package com.example.w_mvvm_databinding.commom;

import com.google.gson.Gson;

public class GsonUtlis {
    private static GsonUtlis instance;

    public static GsonUtlis getInstance(){
        if(instance == null){
            instance = new GsonUtlis();
        }
        return instance;
    }

    public GsonUtlis() {

    }

    public  <T> T jsonStringToBean(String json, Class<T> clazz) {
        T t = null;
        Gson gson = new Gson();
        t = gson.fromJson(json, clazz);
        System.out.println("jsonStringToBean()" + "/json:" + json  +"class:" +clazz.toString());
        return t;
    }

    public  String toJson(Object object) {
        Gson gson = new Gson();
        String json =gson.toJson(object);
        return json;
    }
}
