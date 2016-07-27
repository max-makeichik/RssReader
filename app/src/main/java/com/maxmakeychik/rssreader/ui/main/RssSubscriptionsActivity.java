package com.maxmakeychik.rssreader.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.maxmakeychik.rssreader.R;
import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.maxmakeychik.rssreader.ui.base.BaseActivity;
import com.maxmakeychik.rssreader.ui.posts.PostsActivity;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 10.04.2016.
 */
public class RssSubscriptionsActivity extends BaseActivity implements RssSubscriptionsMvpView {

    private static final String TAG_SUBSCRIPTIONS_FRAGMENT = "subscriptions_fragment";

    @Inject
    RssSubscriptionsPresenter rssSubscriptionsPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final String TAG = "SubscriptionsListActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        setContentView(R.layout.activity_subscriptions);
        ButterKnife.bind(this);

        setToolbar();

        if(savedInstanceState == null) {
        }

        rssSubscriptionsPresenter.attachView(this);
        rssSubscriptionsPresenter.loadSubscriptions();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.title_activity_subscriptions);
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    public void onRssSubscriptionClicked(RssSubscription rssSubscriptionId){
        Intent intent = new Intent(this, PostsActivity.class);
        intent.putExtra(PostsActivity.KEY_RSS_SUBSCRIPTION, Parcels.wrap(rssSubscriptionId));
        startActivity(intent);
    }

    @Override
    public void showSubscriptions(List<RssSubscription> rssSubscriptions) {
        getFragmentManager().beginTransaction()
                .add(R.id.container, RssSubscriptionsFragment.newInstance(rssSubscriptions), TAG_SUBSCRIPTIONS_FRAGMENT)
                .commitAllowingStateLoss();
    }

    @Override
    public void showError() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rssSubscriptionsPresenter.detachView();
    }
}