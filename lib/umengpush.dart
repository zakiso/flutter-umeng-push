import 'dart:async';

import 'package:flutter/services.dart';

class Umengpush {
  static const MethodChannel _channel = const MethodChannel('umengpush');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> init(String appkey, String secret) async {
    final String result = await _channel.invokeMethod(
        "init", <String, dynamic>{"appkey": appkey, "secret": secret});
    return result;
  }

  static Future<String> getDeviceToken() async {
    final String deviceToken = await _channel.invokeMethod("getDeviceToken");
    return deviceToken;
  }
}
