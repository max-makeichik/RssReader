package com.maxmakeychik.rssreader.data.remote;

import com.maxmakeychik.rssreader.data.model.rss.RssResponse;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiService {

    //@GET("/sample-feed.xml")
    @GET()
    Observable<RssResponse> getPosts(@Url String url);

}
