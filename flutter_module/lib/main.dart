import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'mymodule.dart';

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
      home: const MyHomePage(title: 'Teste Bonito'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _batteryLevel = 'Unknown battery level.';
  static const platform = MethodChannel('samples.flutter.dev/battery');

  Future<void> _getMessage() async {
    String batteryLevel;
    try {
      final String result = await platform.invokeMethod('getMessage');
      batteryLevel = result;
    } on PlatformException catch (e) {
      batteryLevel = "Failed to get message from native: '${e.message}'.";
    }

    setState(() {
      _batteryLevel = batteryLevel;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            ElevatedButton(
              onPressed: _getMessage,
              child: const Text('Get Message from native'),
            ),
            Text(_batteryLevel),
          ],
        ),
      ),
    );
  }
}
