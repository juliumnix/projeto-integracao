package com.example.projetov2.viewmodel;

import static com.example.projetov2.model.Informations.getChannelFlutter;
import static com.example.projetov2.model.Informations.getChannel_Id;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetov2.CustomFlutterActivity;
import com.example.projetov2.MainActivity;
import com.example.projetov2.MyReactActivity;
import com.example.projetov2.adapter.NavigateAdapter;
import com.example.projetov2.model.Informations;

import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.FlutterEngineGroup;
import io.flutter.embedding.engine.FlutterEngineGroupCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class FlutterIntegrationViewModel extends ViewModel implements NavigateAdapter {
    private final Informations model;
    private Activity flutterActivityInstance;

    private FlutterEngine customEngine;
    public FlutterIntegrationViewModel() {
        this.model = Informations.getInstance();
    }

    public void saveMessage(String message) {
        model.setMessage_From_Native(message);
    }

    @Override
    public void navigateTo(AppCompatActivity activity, String route) {
        model.setRoute(route);
        Intent intent = new Intent(activity, CustomFlutterActivity.class);
        activity.startActivity(intent);
    }

    public void destroyInstance(){
        if (customEngine != null) {
            customEngine.getNavigationChannel().popRoute();
            customEngine.destroy();
            customEngine = null;
        }
    }

    @Override
    public void initFramework(Activity flutterActivity) {
        flutterActivityInstance = flutterActivity;
        if (customEngine == null) {
            customEngine = new FlutterEngine(flutterActivity);
            customEngine.getDartExecutor().executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault()
            );
        }

    }

    private void navigateToReact(String code){
        Intent intent = new Intent(flutterActivityInstance, MyReactActivity.class);
        intent.putExtra("message_from_native", "pixCode/"+code);
        flutterActivityInstance.startActivity(intent);
    }

    public void initializeListenerFromFlutterToNative(FlutterEngine engine) {
        GeneratedPluginRegistrant.registerWith(engine);
        new MethodChannel(engine.getDartExecutor().getBinaryMessenger(), getChannelFlutter())
                .setMethodCallHandler((call, result) -> {
                    if ("getMessage".equals(call.method)) {
                        result.success(model.getMessage_From_Native());
                    } else {
                        result.notImplemented();
                    }
                });

        new MethodChannel(engine.getDartExecutor().getBinaryMessenger(), "navigate/flutter")
                .setMethodCallHandler((call, result) -> {
                    if ("getNavigationRoute".equals(call.method)) {
                        result.success(model.getRoute());
                    } else {
                        result.notImplemented();
                    }
                });

        new MethodChannel(engine.getDartExecutor().getBinaryMessenger(), "navigate/react")
                .setMethodCallHandler((call, result) -> {
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
