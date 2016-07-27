package com.maxmakeychik.rssreader.ui.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxmakeychik.rssreader.R;
import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.maxmakeychik.rssreader.util.ItemClickSupport;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 15.05.2016.
 */
public class RssSubscriptionsFragment extends Fragment {

    private static final String KEY_CATEGORY = "category";
    private static final String KEY_SONGS = "subscriptions";

    @BindView(R.id.subscriptions_list)
    RecyclerView subscriptionsList;
    private Unbinder unbinder;

    private List<RssSubscription> rssSubscriptions;

    private static final String TAG = "SubscriptionsFragment";
    private RssSubscriptionsAdapter rssRssSubscriptionsAdapter;

    public static RssSubscriptionsFragment newInstance(List<RssSubscription> rssSubscriptions) {
        RssSubscriptionsFragment fragment = new RssSubscriptionsFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_SONGS, Parcels.wrap(rssSubscriptions));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        setRetainInstance(true);
        if(getArguments() != null){
            rssSubscriptions = Parcels.unwrap(getArguments().getParcelable(KEY_SONGS));
        }
        Log.d(TAG, "onCreate: " + rssSubscriptions);
    }

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subscriptions, container, false);
        unbinder = ButterKnife.bind(this, view);

        setList();

        return view;
    }

    private void setList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        subscriptionsList.setLayoutManager(linearLayoutManager);

        rssRssSubscriptionsAdapter = new RssSubscriptionsAdapter(rssSubscriptions, getActivity());
        subscriptionsList.setAdapter(rssRssSubscriptionsAdapter);

        ItemClickSupport.addTo(subscriptionsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ((RssSubscriptionsActivity) getActivity()).onRssSubscriptionClicked(rssSubscriptions.get(position));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}