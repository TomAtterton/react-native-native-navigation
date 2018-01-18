package com.example;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.mediamonks.rnnativenavigation.bridge.RNNativeNavigationPackage;
import com.mediamonks.rnnativenavigation.data.Node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private ReactNativeHost mReactNativeHost;

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mReactNativeHost = new ReactNativeHost(this) {
            @Override
            public boolean getUseDeveloperSupport() {
                return BuildConfig.DEBUG;
            }

            @Override
            protected List<ReactPackage> getPackages() {
                HashMap<String, Class<? extends Node>> hashMap = new HashMap<>();
                hashMap.put(ExampleNode.JS_NAME, ExampleNode.class);

                return Arrays.asList(
                        new RNNativeNavigationPackage(hashMap),
                        new MainReactPackage()
                );
            }
        };
    }
}
