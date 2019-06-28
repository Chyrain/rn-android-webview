
package com.chyrain.rn.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNAAndroidWebviewModule extends ReactContextBaseJavaModule implements ActivityEventListener {

  private final ReactApplicationContext reactContext;

  private ValueCallback<Uri> mUploadMessage;
  private ValueCallback<Uri[]> mUploadCallbackAboveL;

  private RNAAndroidWebviewPackage aPackage;
  private Uri imageUri;

  public void setImageUri(Uri uri) {
    this.imageUri = uri;
  }

  public Uri getImageUri() {
    return this.imageUri;
  }

  public void setPackage(RNAAndroidWebviewPackage aPackage) {
    this.aPackage = aPackage;
  }

  public RNAAndroidWebviewPackage getPackage() {
    return this.aPackage;
  }

  public RNAAndroidWebviewModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    reactContext.addActivityEventListener(this);
  }

  @Override
  public String getName() {
    return "RNAWebViewModule";
  }

  @SuppressWarnings("unused")
  public Activity getActivity() {
    return getCurrentActivity();
  }

  public void setUploadMessage(ValueCallback<Uri> uploadMessage) {
    mUploadMessage = uploadMessage;
  }

  public void setmUploadCallbackAboveL(ValueCallback<Uri[]> mUploadCallbackAboveL) {
    this.mUploadCallbackAboveL = mUploadCallbackAboveL;
  }
  public ValueCallback<Uri[]> getmUploadCallbackAboveL() {
    return this.mUploadCallbackAboveL;
  }

  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    // super.onActivityResult(requestCode, resultCode, data);
    Log.i("customwebview-", "onActivityResult requestCode:" + requestCode + " resultCode:" + resultCode + " data:" + data);
    if (requestCode >= RNAAndroidWebviewManager.REQUEST_MIN && requestCode <= RNAAndroidWebviewManager.REQUEST_MAX) {
      if (null == mUploadMessage && null == mUploadCallbackAboveL){
        return;
      }
      Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
      if (mUploadCallbackAboveL != null) {
        onActivityResultAboveL(requestCode, resultCode, data);
      } else if (mUploadMessage != null) {
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
    if ((requestCode < RNAAndroidWebviewManager.REQUEST_MIN && requestCode > RNAAndroidWebviewManager.REQUEST_MAX) || mUploadCallbackAboveL == null) {
      return;
    }
    Uri[] results = null;
    if (resultCode == Activity.RESULT_OK) {
//      if (data != null) {
//        results = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
//      }
      if (data != null) {
        String dataString = data.getDataString();
        ClipData clipData = data.getClipData();
        if (clipData != null) {
          results = new Uri[clipData.getItemCount()];
          for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item item = clipData.getItemAt(i);
            results[i] = item.getUri();
          }
        }
        Log.i("customwebview-", "dataString:" + dataString);
        if (dataString != null)
          results = new Uri[]{Uri.parse(dataString)};
      } else if (imageUri != null) {
        Log.i("customwebview-", "imageUri:" + imageUri);
        results = new Uri[]{imageUri};
      } else {
        results = new Uri[]{};
      }
    }
    mUploadCallbackAboveL.onReceiveValue(results);
    mUploadCallbackAboveL = null;
    imageUri = null;
  }
  public void onNewIntent(Intent intent) {}
}