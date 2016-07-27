package com.maxmakeychik.rssreader.ui.posts;


import com.maxmakeychik.rssreader.data.model.Post;
import com.maxmakeychik.rssreader.ui.base.MvpView;

import java.util.List;

public interface PostsMvpView extends MvpView {

    void showPosts(List<Post> posts);

    void showError();

}
