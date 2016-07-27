package com.maxmakeychik.rssreader.ui.posts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxmakeychik.rssreader.R;
import com.maxmakeychik.rssreader.data.model.Post;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PostsFragment extends Fragment {

    private static final String TAG = "PostsFragment";

    @BindView(R.id.posts_list)
    RecyclerView postsList;
    private PostsAdapter postsAdapter;

    private Unbinder unbinder;
    private List<Post> posts;

    public static PostsFragment newInstance(List<Post> posts) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putParcelable(PostsActivity.KEY_POSTS, Parcels.wrap(posts));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);
        if (getArguments() != null) {
            posts = Parcels.unwrap(getArguments().getParcelable(PostsActivity.KEY_POSTS));
        } else if (savedInstanceState != null) {
            posts = Parcels.unwrap(savedInstanceState.getParcelable(PostsActivity.KEY_POSTS));
        }
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);

        setList();

        return view;
    }

    private void setList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        postsList.setLayoutManager(linearLayoutManager);

        postsAdapter = new PostsAdapter(posts, getActivity());
        postsList.setAdapter(postsAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PostsActivity.KEY_POSTS, Parcels.wrap(posts));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}