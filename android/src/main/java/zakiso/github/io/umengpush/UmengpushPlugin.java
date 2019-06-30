package zakiso.github.io.umengpush;

import android.app.Activity;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.json.JSONObject;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * UmengpushPlugin
 */
public class UmengpushPlugin implements MethodCallHandler {
  /**
   * Plugin registration.
   */

  private final String TAG = "UmengPush";
  private Activity activity;

  public UmengpushPlugin(Activity activity) {
    this.activity = activity;
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "umengpush");
    channel.setMethodCallHandler(new UmengpushPlugin(registrar.activity()));
  }

  @Override
  public void onMethodCall(MethodCall call, final Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("init")) {
      if (call.hasArgument("appkey") && call.hasArgument("secret")) {

        String appkey = call.argument("appkey");
        String secret = call.argument("secret");

        UMConfigure.init(activity, appkey, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, secret);
        result.success("umeng initialized");
      } else {
        result.error(call.method, "请传入appid和secret", null);
      }
    } else if (call.method.equals("getDeviceToken")) {
      PushAgent mPushAgent = PushAgent.getInstance(activity);
      mPushAgent.register(new IUmengRegisterCallback() {
        @Override
        public void onSuccess(String deviceToken) {
          //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
          Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
          result.success(deviceToken);
        }

        @Override
        public void onFailure(String s, String s1) {
          String message = "注册失败：-------->  " + "s:" + s + ",s1:" + s1;
          Log.e(TAG, message);
          result.error(TAG, message, null);
        }
      });

    } else {
      result.notImplemented();
    }
  }

}
