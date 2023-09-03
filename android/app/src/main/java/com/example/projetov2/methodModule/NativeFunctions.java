package com.example.projetov2.methodModule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetov2.MyReactActivity;
import com.example.projetov2.viewmodel.ReactIntegrationViewModel;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class NativeFunctions extends ReactContextBaseJavaModule {

    public NativeFunctions(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "NativeFunctions";
    }
    @ReactMethod
    public void navigateToFlutter(){
        MyReactActivity currentActivity = (MyReactActivity) getCurrentActivity();
        assert currentActivity != null;
        ReactIntegrationViewModel viewModelReact = new ViewModelProvider(currentActivity).get(ReactIntegrationViewModel.class);
        viewModelReact.renderFlutterInsideReact("/teste", currentActivity);
    }


}
