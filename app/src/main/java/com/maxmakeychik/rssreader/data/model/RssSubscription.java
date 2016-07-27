package com.maxmakeychik.rssreader.data.model;

import org.parceler.Parcel;

/**
 * Created by admin on 18.05.2016.
 */
@Parcel
public class RssSubscription {

    public RssSubscription(){}

    public RssSubscription(String url) {
        this.url = url;
    }

    public RssSubscription(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public int id;
    public String title;
    public String url;

    @Override
    public String toString() {
        return "\n" + "Subscription {" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
