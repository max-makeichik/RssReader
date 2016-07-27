package com.maxmakeychik.rssreader.ui.main;

import android.util.Log;

import com.maxmakeychik.rssreader.data.DataManager;
import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.maxmakeychik.rssreader.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RssSubscriptionsPresenter extends BasePresenter<RssSubscriptionsMvpView> {

    private static final String TAG = "SubscriptionsPresenter";
    private final DataManager dataManager;
    private Subscription subscription;

    @Inject
    public RssSubscriptionsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(RssSubscriptionsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadSubscriptions() {
        checkViewAttached();
        subscription = dataManager.getSubscriptions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<RssSubscription>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "There was an error loading the subscriptions.");
                        e.printStackTrace();
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<RssSubscription> subscriptions) {
                        getMvpView().showSubscriptions(subscriptions);
                    }
                });
    }
}