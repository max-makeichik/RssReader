package com.maxmakeychik.rssreader.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxmakeychik.rssreader.R;
import com.maxmakeychik.rssreader.data.model.RssSubscription;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RssSubscriptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RssSubscription> rssSubscriptions;
    private Context context;

    private static final String TAG = "SubscriptionsAdapter";

    public RssSubscriptionsAdapter(List<RssSubscription> rssSubscriptions, Context context) {
        this.rssSubscriptions = rssSubscriptions;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription, parent, false));
    }

    private RssSubscription getItem(int position){
        return rssSubscriptions.get(position);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ViewHolder holderItem = (ViewHolder) holder;
        holderItem.title.setText(getItem(position).title);
        holderItem.url.setText(getItem(position).url);
    }

    @Override
    public int getItemCount() {
        return rssSubscriptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.url)
        TextView url;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}