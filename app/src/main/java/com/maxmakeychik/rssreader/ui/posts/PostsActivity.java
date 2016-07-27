package com.maxmakeychik.rssreader.ui.posts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maxmakeychik.rssreader.R;
import com.maxmakeychik.rssreader.data.model.Post;
import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.maxmakeychik.rssreader.ui.base.BaseActivity;
import com.maxmakeychik.rssreader.util.Util;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 10.04.2016.
 */
public class PostsActivity extends BaseActivity implements PostsMvpView {

    private static final String TAG = "PostsActivity";
    private static final String TAG_POSTS_FRAGMENT = "posts_fragment";
    public static final String KEY_POSTS = "posts";
    public static final String KEY_RSS_SUBSCRIPTION = "rss_subscription";

    @Inject
    PostsPresenter postsPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RssSubscription rssSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);

        setToolbar();

        if(savedInstanceState == null) {
            rssSubscription = Parcels.unwrap(getIntent().getParcelableExtra(KEY_RSS_SUBSCRIPTION));
        }
        else {
            rssSubscription = Parcels.unwrap(savedInstanceState.getParcelable(KEY_RSS_SUBSCRIPTION));
        }

        //Log.d(TAG, "onCreate: " + rssSubscription);
        postsPresenter.attachView(this);
        postsPresenter.getPosts(rssSubscription);

        setTitle(rssSubscription.title);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title_activity_posts));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_RSS_SUBSCRIPTION, Parcels.wrap(rssSubscription));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPosts(List<Post> posts) {
        //Log.d(TAG, "showPosts: " + posts);
        if(posts.size() == 0){
            Util.toastIfNoConnection(this);
        }
        getFragmentManager().beginTransaction()
                .add(R.id.container, PostsFragment.newInstance(posts), TAG_POSTS_FRAGMENT)
                .commitAllowingStateLoss();
    }

    @Override
    public void showError() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        postsPresenter.detachView();
    }
}