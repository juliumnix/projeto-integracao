import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/camera_pix_detector.dart';
import 'package:flutter_module/mymodule.dart';
import 'package:flutter_module/teste.dart';

import 'my_home_page.dart';

class SplashScreen extends StatefulWidget {
  SplashScreen({
    super.key,
  });

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen>
    with WidgetsBindingObserver {
  var module = MyModule();

  Future<void> _getNativeParams() async {
    try {
      String navigate = await module.getNavigate();
      print(navigate);

      if (navigate == "/") {
        _navigateTo(CameraPixDetector());
      }

      if (navigate == "/teste") {
        _navigateTo(const Teste(
          pixCode: "teste",
        ));
      }
    } on PlatformException catch (e) {
      // print(e.message);
    }
  }

  void _navigateTo(StatefulWidget page) {
    Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => page,
        ));
  }

  @override
  void initState() {
    super.initState();
    _getNativeParams();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("AAAAAAAAAAAAAAA"),
              ElevatedButton(
                  onPressed: () {
                    _getNativeParams();
                  },
                  child: Text("cuideapito"))
            ]),
      ),
    );
  }
}
