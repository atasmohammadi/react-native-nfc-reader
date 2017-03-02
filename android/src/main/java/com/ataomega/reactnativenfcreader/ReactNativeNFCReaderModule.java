package com.ataomega.reactnativenfcreader;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import java.nio.ByteBuffer;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.HashMap;

public class ReactNativeNFCReaderModule extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener  {
  public static final String TAG = "NFCReaderModule";
  private static final String		REACT_CLASS = "ReactNativeNFCReaderModule";
	private ReactApplicationContext reactContext;
  private static final char[] HAX_ARRAY = "0123456789ABCDEF".toCharArray();
  String serialNumber;
  String intentText = "Empty";
  Promise tagPromise;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public ReactNativeNFCReaderModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.reactContext = reactContext;
      this.reactContext.addActivityEventListener(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
      Log.i(TAG, "onNewIntent");
      intentText = "onNewIntent";
      Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
      /*ByteBuffer bb = ByteBuffer.wrap(tag.getId());
      int id = bb.getInt();
      WritableMap idData = Arguments.createMap();
      idData.putInt("id", id);
      this.tagPromise.resolve(idData);*/
  		serialNumber = bytesToHex(tag.getId());
      WritableMap params = Arguments.createMap();
      params.putString("serial", serialNumber);
      sendEvent(this.reactContext, "NFCTagDetected", params);

    }

    @ReactMethod
    public void getCardId(Promise promise) {
      this.tagPromise = promise;
    }

    @Override
    public void onHostResume() {
      Log.i(TAG, "onResume");
    }

    @Override
    public void onHostPause() {
      Log.i(TAG, "onPause");
    }

    @Override
    public void onHostDestroy() {
      Log.i(TAG, "onDestroy");
    }

    @Override
      public void onActivityResult(
        final Activity activity,
        final int requestCode,
        final int resultCode,
        final Intent intent) {
          Log.i(TAG, "onActivityResult");
      }

    @ReactMethod
    public void show() {
      Log.i(TAG, "show invoked.");
      if(serialNumber != null) {
        Log.i(TAG, serialNumber);
      } else{
        Log.i(TAG, intentText);
      }
    }

    private static String bytesToHex(byte[] bytes) {
      char[] hexChars = new char[bytes.length * 2];
      for ( int j = 0; j < bytes.length; j++ ) {
          int v = bytes[bytes.length - j - 1] & 0xFF;
          hexChars[j * 2] = HAX_ARRAY[v >>> 4];
          hexChars[j * 2 + 1] = HAX_ARRAY[v & 0x0F];
      }
      return new String(hexChars);
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
      if (reactContext.hasActiveCatalystInstance()) {
        reactContext
          .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
          .emit(eventName, params);
      } else {
        Log.i(TAG, "Waiting for CatalystInstance...");
      }
    }

}
