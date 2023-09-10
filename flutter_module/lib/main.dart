import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/camera_pix_detector.dart';
import 'package:flutter_module/help_page.dart';
import 'package:flutter_module/mymodule.dart';
import 'package:flutter_module/splashscreen.dart';
import 'package:flutter_module/teste.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      debugShowCheckedModeBanner: false,
      routes: {
        "/": (context) => SplashScreen(),
        "/qrCode": (context) => const CameraPixDetector(),
        "/teste": (context) => const Teste(fundDetail: "1234"),
        "/help": (context) => const HelpPage()
      },
    );
  }
}
