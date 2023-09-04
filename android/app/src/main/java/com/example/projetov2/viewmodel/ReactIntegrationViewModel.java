package com.example.projetov2.viewmodel;

import static androidx.core.content.ContextCompat.startActivity;

import static com.example.projetov2.model.Informations.getChannel_Id;
import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.example.projetov2.BuildConfig;
import com.example.projetov2.CustomFlutterActivity;
import com.example.projetov2.MyReactActivity;
import com.example.projetov2.adapter.NavigateAdapter;
import com.example.projetov2.model.Informations;
import com.example.projetov2.packages.ReactPackageNative;
import com.facebook.hermes.reactexecutor.HermesExecutorFactory;
import com.facebook.react.PackageList;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.soloader.SoLoader;

import java.util.List;

import io.flutter.embedding.android.FlutterActivity;

public class ReactIntegrationViewModel extends ViewModel implements NavigateAdapter {
    private final Informations model;
    private ReactRootView mReactRootView;

    private ReactInstanceManager mReactInstanceManager;

    public ReactIntegrationViewModel() {
        this.model = Informations.getInstance();
    }

    public void saveMessage(String message) {
        model.setMessage_From_Native(message);
    }

    public ReactInstanceManager getmReactInstanceManager() {
        return mReactInstanceManager;
    }

    @Override
    public void navigateTo(AppCompatActivity activity, String route) {
        Intent intent = new Intent(activity, MyReactActivity.class);
        intent.putExtra("message_from_native", model.getMessage_From_Native());
        activity.startActivity(intent);
    }

    public void renderFlutterInsideReact(String route, AppCompatActivity activity) {
        model.setRoute(route);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity, CustomFlutterActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void initFramework(Activity appCompatActivity) {
        SoLoader.init(appCompatActivity, false);
        mReactRootView = new ReactRootView(appCompatActivity);
        List<ReactPackage> packages = new PackageList(appCompatActivity.getApplication()).getPackages();
        // Packages that cannot be autolinked yet can be added manually here, for example:
        // packages.add(new MyReactNativePackage());
        packages.add(new ReactPackageNative());
        // Remember to include them in `settings.gradle` and `app/build.gradle` too.
        SoLoader.init(appCompatActivity, false);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(appCompatActivity.getApplication())
                .setCurrentActivity(appCompatActivity)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackages(packages)
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .setJavaScriptExecutorFactory(new HermesExecutorFactory())
                .build();
        // The string here (e.g. "MyReactNativeApp") has to match
        // the string in AppRegistry.registerComponent() in index.js
        Bundle initialProperties = new Bundle();
        String messageFromNative = appCompatActivity.getIntent().getStringExtra("message_from_native");
        initialProperties.putString("message_from_native", messageFromNative);
        mReactRootView.startReactApplication(mReactInstanceManager, "MyReactNativeApp", initialProperties);
    }

    public ReactRootView getmReactRootView() {
        return mReactRootView;
    }
}