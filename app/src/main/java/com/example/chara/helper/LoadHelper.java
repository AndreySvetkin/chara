package com.example.chara.helper;

import java.lang.reflect.Method;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadHelper {

    private Object activity;

    private String methodName;

    private Class[] classes;

    public LoadHelper(Object object, String methodName, Class... classes) {
        this.activity = object;
        this.methodName = methodName;
        this.classes = classes;
    }

    public <T> void loadData(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                try {
                    if (response.errorBody() != null) {
                        System.out.println(call.request());
                        System.out.println(response.errorBody().string());
                    }
                    Method method = activity.getClass().getMethod(methodName, classes);
                    method.invoke(activity, response.body());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
