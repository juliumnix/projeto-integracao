package com.example.projetov2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends AppCompatActivity {
    public FlutterEngine flutterEngine;
    private static final String CHANNEL = "samples.flutter.dev/battery";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnReact = findViewById(R.id.btn_react);
        Button btnFlutter = findViewById(R.id.btn_flutter);
        EditText txtMessage = findViewById(R.id.txt_texto);


        flutterEngine = new FlutterEngine(this);
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );

        FlutterEngineCache
                .getInstance()
                .put("my_engine_id", flutterEngine);

        // aqui faz a comunicacao nativo - flutter
        FlutterEngine engine = FlutterEngineCache.getInstance().get("my_engine_id");
        if (engine != null) {
            // Set a MethodCallHandler to that engine.
            new MethodChannel(engine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                    .setMethodCallHandler((MethodCall call, MethodChannel.Result result) -> {
                        if ("getMessage".equals(call.method)) {
                            result.success(txtMessage.getText().toString()); // Send whatever you need here.
                        } else {
                            result.notImplemented();
                        }
                    });
        }
        btnFlutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        FlutterActivity
                                .withCachedEngine("my_engine_id")
                                .build(view.getContext())
                );
            }
        });
        btnReact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyReactActivity.class);
                intent.putExtra("message_from_native", txtMessage.getText().toString());
                startActivity(intent);
            }
        });
    }
}