package com.example.projetov2.viewmodel;

import static com.example.projetov2.model.Informations.getChannelFlutter;
import static com.example.projetov2.model.Informations.getChannel_Id;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetov2.MyReactActivity;
import com.example.projetov2.adapter.NavigateAdapter;
import com.example.projetov2.model.Informations;

import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class FlutterIntegrationViewModel extends ViewModel implements NavigateAdapter {
    private final Informations model;
    private AppCompatActivity mainActivity;
    public FlutterIntegrationViewModel() {
        this.model = Informations.getInstance();
    }

    public void saveMessage(String message) {
        model.setMessage_From_Native(message);
    }

    @Override
    public void navigateTo(AppCompatActivity activity) {
        model.setRoute("/");
        Intent intent = FlutterActivity
                .withCachedEngine(getChannel_Id())
                .build(activity.getApplicationContext());
        activity.startActivity(intent);
    }

    @Override
    public void initFramework(AppCompatActivity appCompatActivity) {
        mainActivity = appCompatActivity;
        FlutterEngine flutterEngine = new FlutterEngine(appCompatActivity);
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );
        FlutterEngineCache
                .getInstance()
                .put(getChannel_Id(), flutterEngine);
        FlutterEngine engine = FlutterEngineCache.getInstance().get(getChannel_Id());
        if (engine != null) {
            initializeListenerFromFlutterToNative(engine);
        }
    }

    private void navigateToReact(String code){
        Intent intent = new Intent(mainActivity, MyReactActivity.class);
        intent.putExtra("message_from_native", "pixCode/"+code);
        mainActivity.startActivity(intent);
    }

    private void initializeListenerFromFlutterToNative(FlutterEngine engine) {
        new MethodChannel(engine.getDartExecutor().getBinaryMessenger(), getChannelFlutter())
                .setMethodCallHandler((MethodCall call, MethodChannel.Result result) -> {
                    if ("getMessage".equals(call.method)) {
                        result.success(model.getMessage_From_Native());
                    } else {
                        result.notImplemented();
                    }
                });

        new MethodChannel(engine.getDartExecutor().getBinaryMessenger(), "navigate/flutter")
                .setMethodCallHandler((MethodCall call, MethodChannel.Result result) -> {
                    if ("getNavigationRoute".equals(call.method)) {
                        result.success(model.getRoute());
                    } else {
                        result.notImplemented();
                    }
                });

        new MethodChannel(engine.getDartExecutor().getBinaryMessenger(), "navigate/react")
                .setMethodCallHandler((MethodCall call, MethodChannel.Result result) -> {
                    if ("getNavigateToReact".equals(call.method)) {
                        Map<String, Object> data = call.arguments();
                        assert data != null;
                        String key = (String) data.get("key");
                        navigateToReact(key);
                    } else {
                        result.notImplemented();
                    }
                });
    }
}
