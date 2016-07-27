package com.maxmakeychik.rssreader;

import android.app.Application;
import android.content.Context;

import com.maxmakeychik.rssreader.injection.component.ApplicationComponent;
import com.maxmakeychik.rssreader.injection.component.DaggerApplicationComponent;
import com.maxmakeychik.rssreader.injection.module.ApplicationModule;

/**
 * Created by admin on 01.05.2016.
 */
public class App extends Application {

    ApplicationComponent mApplicationComponent;
    private static Context context;

    public static Context getAppContext() {
        return App.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}