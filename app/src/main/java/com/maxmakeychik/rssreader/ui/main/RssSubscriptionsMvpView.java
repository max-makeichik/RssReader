package com.maxmakeychik.rssreader.ui.main;

import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.maxmakeychik.rssreader.ui.base.MvpView;

import java.util.List;

public interface RssSubscriptionsMvpView extends MvpView {

    void showSubscriptions(List<RssSubscription> rssSubscriptions);

    void showError();

}
