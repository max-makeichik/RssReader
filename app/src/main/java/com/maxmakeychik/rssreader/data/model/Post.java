package com.maxmakeychik.rssreader.data.model;

import org.parceler.Parcel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by admin on 18.05.2016.
 */
@Parcel
@Root(name = "item", strict = false )
public class Post {

    public Post(){}

    @Element
    public String title;
    @Element
    public String description;
    public int subscriptionId;

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", subscriptionId='" + subscriptionId + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
