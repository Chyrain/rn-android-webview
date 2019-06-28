
package com.chyrain.rn.webview;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;

public class RNAAndroidWebviewPackage implements ReactPackage {

    private RNAAndroidWebviewManager manager;
    private RNAAndroidWebviewModule module;

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        module = new RNAAndroidWebviewModule(reactContext);
        module.setPackage(this);
        return Arrays.<NativeModule>asList(new RNAAndroidWebviewModule(reactContext));
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
      return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        manager = new RNAAndroidWebviewManager();
        manager.setPackage(this);
        return Arrays.<ViewManager>asList(manager);
    }

    public RNAAndroidWebviewManager getManager(){
        return manager;
    }

    public RNAAndroidWebviewModule getModule(){
        return module;
    }
}