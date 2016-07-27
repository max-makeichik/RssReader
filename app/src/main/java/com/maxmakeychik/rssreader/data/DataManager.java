package com.maxmakeychik.rssreader.data;

import android.util.Log;

import com.maxmakeychik.rssreader.data.local.DatabaseHelper;
import com.maxmakeychik.rssreader.data.local.PreferencesHelper;
import com.maxmakeychik.rssreader.data.model.Post;
import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.maxmakeychik.rssreader.data.remote.ApiService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager {

    private static final String TAG = "DataManager";
    private final ApiService apiService;
    private final DatabaseHelper databaseHelper;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public DataManager(ApiService apiService, PreferencesHelper preferencesHelper, DatabaseHelper databaseHelper) {
        this.apiService = apiService;
        this.preferencesHelper = preferencesHelper;
        this.databaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    public Observable<List<Post>> getPosts(int subscriptionId) {
        return databaseHelper.getPosts(subscriptionId).distinct();
    }

    public Observable<Post> getPostsFromNetwork(RssSubscription rssSubscription) {
        return apiService.getPosts(rssSubscription.url)
                .doOnNext(posts -> Log.d(TAG, "posts " + posts))
                .map(rssResponse -> rssResponse.channel.posts)
                .flatMap(posts -> databaseHelper.setPosts(posts, rssSubscription.id));
    }

    public Observable<List<RssSubscription>> getSubscriptions() {
        return databaseHelper.getSubscriptions();
    }

}