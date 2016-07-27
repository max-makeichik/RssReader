package com.maxmakeychik.rssreader.data.model.rss;

import com.maxmakeychik.rssreader.data.model.Post;

import org.parceler.Parcel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by admin on 25.07.2016.
 */
@Parcel
@Root(name = "channel", strict = false)
public class Channel {

    public Channel(){}

    @ElementList(inline = true, required = false)
    public List<Post> posts;
    @Element
    public String title;

    @Override
    public String toString() {
        return "RssResponse{" +
                ", title=" + title +
                ", posts=" + posts +
                '}';
    }
}
