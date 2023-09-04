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
  TextEditingController _textController = TextEditingController();

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
    _textController.clear();
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
            children: [
              const Text("Como gostaria de pagar seu PIX?"),
              ElevatedButton.icon(
                onPressed: readQRCode,
                icon: const Icon(Icons.qr_code),
                label: const Text('Ler QR Code'),
              ),
              const Text("Copiar e colar código PIX"),
              TextField(
                controller: _textController,
                decoration: const InputDecoration(
                  hintText: 'Digite seu código PIX aqui',
                  labelText: 'Código PIX',
                  border: OutlineInputBorder(),
                ),
              ),
              ElevatedButton.icon(
                  onPressed: () {
                    navigateToPix(_textController.text);
                  },
                  icon: Icon(Icons.arrow_forward),
                  label: Text("Pagar"))
            ],
          ),
        ),
      ),
    );
  }
}
