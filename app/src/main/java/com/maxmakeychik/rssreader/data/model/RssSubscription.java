package com.maxmakeychik.rssreader.data.model;

import org.parceler.Parcel;

/**
 * Created by admin on 18.05.2016.
 */
@Parcel
public class RssSubscription {

    public RssSubscription(){}

    public int id;
    public String url;
    public ImageEntity imageEntity;
    public String title;

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", imageEntity='" + imageEntity + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getImageUrl() {
        return imageEntity == null ? null : imageEntity.url;
    }

    public float getAspectRatio() {
        if(imageEntity == null || imageEntity.width == 0) {
            return 0;
        }
        return Math.max(imageEntity.width, imageEntity.height) / Math.min(imageEntity.width, imageEntity.height);
    }
}
