package com.maxmakeychik.rssreader.ui.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maxmakeychik.rssreader.R;
import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.maxmakeychik.rssreader.ui.base.BaseActivity;
import com.maxmakeychik.rssreader.ui.posts.PostsActivity;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 10.04.2016.
 */
public class RssSubscriptionsActivity extends BaseActivity implements RssSubscriptionsMvpView {

    private static final String TAG_SUBSCRIPTIONS_FRAGMENT = "subscriptions_fragment";

    @Inject
    RssSubscriptionsPresenter rssSubscriptionsPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final String TAG = "SubscriptionsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        setContentView(R.layout.activity_subscriptions);
        ButterKnife.bind(this);

        setToolbar();

        if (savedInstanceState == null) {
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
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    public void onRssSubscriptionClicked(RssSubscription rssSubscriptionId) {
        Intent intent = new Intent(this, PostsActivity.class);
        intent.putExtra(PostsActivity.KEY_RSS_SUBSCRIPTION, Parcels.wrap(rssSubscriptionId));
        startActivity(intent);
    }

    @Override
    public void showSubscriptions(List<RssSubscription> rssSubscriptions) {
        Log.d(TAG, "showSubscriptions: " + rssSubscriptions);
        getFragmentManager().beginTransaction()
                .add(R.id.container, RssSubscriptionsFragment.newInstance(rssSubscriptions), TAG_SUBSCRIPTIONS_FRAGMENT)
                .commitAllowingStateLoss();
    }

    @Override
    public void showError() {
    }

    @Override
    public void onSubscriptionRemoved(int id) {
        if (getFragmentManager().findFragmentByTag(TAG_SUBSCRIPTIONS_FRAGMENT) == null) {
            return;
        }
        ((RssSubscriptionsFragment) getFragmentManager().findFragmentByTag(TAG_SUBSCRIPTIONS_FRAGMENT)).onSubscriptionRemoved(id);
    }

    @OnClick(R.id.add_subscription_fab)
    void onFabClicked() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_add_subscription, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(mView);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Button theButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        theButton.setOnClickListener(new CustomListener(alertDialog));
    }

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        private EditText urlEditText;
        private TextInputLayout inputLayoutUrl;

        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
            urlEditText = (EditText) dialog.findViewById(R.id.input_url);
            urlEditText.setSelection(urlEditText.getText().length());
            inputLayoutUrl = (TextInputLayout) dialog.findViewById(R.id.input_layout_url);
        }

        @Override
        public void onClick(View v) {
            String url = (urlEditText).getText().toString();
            if (TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches()) {
                inputLayoutUrl.setErrorEnabled(true);
                Toast.makeText(RssSubscriptionsActivity.this, getString(R.string.err_input_url), Toast.LENGTH_SHORT).show();
                return;
            }
            dialog.dismiss();
            addSubscription(url);
        }
    }

    private void addSubscription(String url) {
        rssSubscriptionsPresenter.addSubscription(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rssSubscriptionsPresenter.detachView();
    }

    public void removeRssSubscription(int subscriptionId) {
        rssSubscriptionsPresenter.removeRssSubscription(subscriptionId);
    }
}