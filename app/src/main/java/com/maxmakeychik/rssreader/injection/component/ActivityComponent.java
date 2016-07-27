package com.maxmakeychik.rssreader.injection.component;

import com.maxmakeychik.rssreader.injection.PerActivity;
import com.maxmakeychik.rssreader.injection.module.ActivityModule;
import com.maxmakeychik.rssreader.ui.main.RssSubscriptionsActivity;
import com.maxmakeychik.rssreader.ui.posts.PostsActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(RssSubscriptionsActivity rssSubscriptionsActivity);

    void inject(PostsActivity postsActivity);
}