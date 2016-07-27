package com.maxmakeychik.rssreader.injection.module;

import android.app.Application;
import android.content.Context;

import com.maxmakeychik.rssreader.data.remote.ApiFactory;
import com.maxmakeychik.rssreader.data.remote.ApiService;
import com.maxmakeychik.rssreader.injection.ApplicationContext;
import com.maxmakeychik.rssreader.util.MainThreadBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    MainThreadBus provideEventBus() {
        return new MainThreadBus();
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return ApiFactory.createService(ApiService.class);
    }

}