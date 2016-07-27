package com.maxmakeychik.rssreader.ui.main;

import android.util.Log;

import com.maxmakeychik.rssreader.data.DataManager;
import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.maxmakeychik.rssreader.data.model.rss.Channel;
import com.maxmakeychik.rssreader.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RssSubscriptionsPresenter extends BasePresenter<RssSubscriptionsMvpView> {

    private static final String TAG = "SubscriptionsPresenter";
    private final DataManager dataManager;
    private CompositeSubscription subscription = new CompositeSubscription();

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
        subscription.add(dataManager.getSubscriptions()
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
                }));
    }

    public void addSubscription(String url){
        subscription.add(dataManager.addRssSubscription(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Channel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "There was an error adding the subscription.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Channel channel) {
                        //Log.d(TAG, "onNext: " + rssResponse);
                    }
                }));
    }

    public void removeRssSubscription(int id){
        subscription.add(dataManager.removeRssSubscription(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "There was an error removing the subscription.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Integer id) {
                        getMvpView().onSubscriptionRemoved(id);
                    }
                }));
    }
}