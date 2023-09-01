package com.example.projetov2;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import io.flutter.embedding.android.FlutterActivity;

public class CalendarModule extends ReactContextBaseJavaModule {

    public CalendarModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "CalendarModule";
    }

    @ReactMethod
    public void createCalendarEvent() {

    }
    @ReactMethod
    public void createCalendarPromise(Promise promise){
        MyReactActivity currentActivity = (MyReactActivity) getCurrentActivity();
        try {
            assert currentActivity != null;
            currentActivity.testeNavegation();
            promise.resolve("Data return from promise");
        } catch (Exception e){
            promise.reject("Error returned", e);
        }
    }


}
