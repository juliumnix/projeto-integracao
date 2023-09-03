import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_barcode_scanner/flutter_barcode_scanner.dart';
import 'package:flutter_module/mymodule.dart';
import 'package:flutter_module/teste.dart';

class CameraPixDetector extends StatefulWidget {
  const CameraPixDetector({super.key});

  @override
  State<CameraPixDetector> createState() => _CameraPixDetectorState();
}

class _CameraPixDetectorState extends State<CameraPixDetector>
    with WidgetsBindingObserver {
  final FocusNode _focusNode = FocusNode();

  readQRCode() async {
    String code = await FlutterBarcodeScanner.scanBarcode(
      "#FFFFFF",
      "Cancelar",
      false,
      ScanMode.QR,
    );
    navigateToPix(code);
  }

  navigateToPix(String pixCode) {
    MyModule module = MyModule();
    module.navigateToReact(pixCode);
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
        body: SizedBox(
          width: MediaQuery.of(context).size.width,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              ElevatedButton.icon(
                onPressed: readQRCode,
                icon: const Icon(Icons.qr_code),
                label: const Text('Validar'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
