package com.maxmakeychik.rssreader.data.model;

import org.parceler.Parcel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by admin on 25.07.2016.
 */
@Parcel
@Root(name = "image", strict = false)
public class ImageEntity {

    public ImageEntity() {
    }

    public ImageEntity(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    @Element
    public String url;
    @Element
    public int width;
    @Element
    public int height;

    @Override
    public String toString() {
        return "ImageEntity{" +
                "url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
