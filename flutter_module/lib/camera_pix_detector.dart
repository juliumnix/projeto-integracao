import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_barcode_scanner/flutter_barcode_scanner.dart';
import 'package:flutter_module/mymodule.dart';

class CameraPixDetector extends StatefulWidget {
  const CameraPixDetector({super.key});

  @override
  State<CameraPixDetector> createState() => _CameraPixDetectorState();
}

class _CameraPixDetectorState extends State<CameraPixDetector>
    with WidgetsBindingObserver {
  final TextEditingController _textController = TextEditingController();
  final bool verifyIsFlutter = false;

  readQRCode() async {
    String code = await FlutterBarcodeScanner.scanBarcode(
      "#FFFFFF",
      "Cancelar",
      false,
      ScanMode.QR,
    );
    if (code != "-1") {
      navigateToPix(code);
    }
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
        appBar: AppBar(
          backgroundColor: const Color(0xff0099cc),
          systemOverlayStyle: const SystemUiOverlayStyle(
            statusBarColor: Color(0xff0099cc),
          ),
          title: Text(verifyIsFlutter ? "Tela Flutter" : "Pagamento PIX"),
        ),
        body: SizedBox(
          width: MediaQuery.of(context).size.width,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                const Padding(
                  padding: EdgeInsets.only(bottom: 20),
                  child: Text(
                    "Como deseja pagar?",
                    style: TextStyle(fontSize: 24),
                  ),
                ),
                Center(
                  child: ElevatedButton.icon(
                    onPressed: readQRCode,
                    icon: const Icon(Icons.qr_code),
                    label: const Text('Ler QR Code'),
                    style: ButtonStyle(
                        backgroundColor: MaterialStateProperty.all<Color>(
                            const Color(0xff0099cc))),
                  ),
                ),
                const Padding(
                  padding: EdgeInsets.all(16.0),
                  child:
                      Center(child: Text("Ou", style: TextStyle(fontSize: 24))),
                ),
                const Padding(
                  padding: EdgeInsets.only(bottom: 8.0),
                  child: Text("Copiar e colar código PIX"),
                ),
                TextField(
                  controller: _textController,
                  decoration: const InputDecoration(
                    hintText: 'Digite seu código PIX aqui',
                    border: OutlineInputBorder(),
                  ),
                ),
                Center(
                  child: ElevatedButton.icon(
                      style: ButtonStyle(
                          backgroundColor: MaterialStateProperty.all<Color>(
                              const Color(0xff0099cc))),
                      onPressed: () {
                        if (_textController.text != "") {
                          navigateToPix(_textController.text);
                        }
                      },
                      icon: const Icon(Icons.arrow_forward),
                      label: const Text("Pagar")),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
