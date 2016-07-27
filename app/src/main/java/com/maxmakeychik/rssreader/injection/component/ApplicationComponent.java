package com.maxmakeychik.rssreader.injection.component;

import android.app.Application;
import android.content.Context;

import com.maxmakeychik.rssreader.data.DataManager;
import com.maxmakeychik.rssreader.data.local.PreferencesHelper;
import com.maxmakeychik.rssreader.injection.ApplicationContext;
import com.maxmakeychik.rssreader.injection.module.ApplicationModule;
import com.maxmakeychik.rssreader.util.MainThreadBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();
    MainThreadBus eventBus();
}