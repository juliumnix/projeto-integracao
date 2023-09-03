import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MyHomePage extends StatefulWidget {
  final String valueFromNative;
  const MyHomePage({super.key, required this.valueFromNative});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _message = '';

  @override
  void initState() {
    _message = widget.valueFromNative;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        if (Navigator.canPop(context)) {
          SystemChannels.platform.invokeMethod<void>('SystemNavigator.pop');
          return false;
        } else {
          return true;
        }
      },
      child: Scaffold(
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const Text("Welcome to Flutter", style: TextStyle(fontSize: 20)),
              Container(height: 10),
              const Text("This is the message coming from the native",
                  style: TextStyle(fontSize: 16)),
              Container(height: 10),
              Text(_message,
                  style: const TextStyle(
                      fontSize: 18, fontWeight: FontWeight.bold)),
            ],
          ),
        ),
      ),
    );
  }
}
