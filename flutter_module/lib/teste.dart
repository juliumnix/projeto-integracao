import 'package:flutter/material.dart';

class Teste extends StatefulWidget {
  final String pixCode;
  const Teste({super.key, required this.pixCode});

  @override
  State<Teste> createState() => _TesteState();
}

class _TesteState extends State<Teste> {
  @override
  Widget build(BuildContext context) {
    return const Placeholder();
  }
}