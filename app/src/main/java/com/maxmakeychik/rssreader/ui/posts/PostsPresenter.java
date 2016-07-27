package com.maxmakeychik.rssreader.ui.posts;

import android.util.Log;

import com.maxmakeychik.rssreader.data.DataManager;
import com.maxmakeychik.rssreader.data.model.Post;
import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.maxmakeychik.rssreader.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PostsPresenter extends BasePresenter<PostsMvpView> {

    private static final String TAG = "SubscriptionsPresenter";
    private final DataManager dataManager;
    private CompositeSubscription subscription = new CompositeSubscription();

    @Inject
    public PostsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(PostsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    public void getPosts(RssSubscription rssSubscription) {
        checkViewAttached();
        subscription.add(dataManager.getPosts(rssSubscription.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "There was an error loading the posts.");
                        e.printStackTrace();
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        getMvpView().showPosts(posts);
                    }
                }));

        subscription.add(dataManager.getPostsFromNetwork(rssSubscription)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Post>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "There was an error loading the posts from network.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Post post) {
                    }
                }));
    }

    public void onCategoryClicked(int category) {
    }
}