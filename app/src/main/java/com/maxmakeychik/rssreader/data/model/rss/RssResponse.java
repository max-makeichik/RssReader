package com.maxmakeychik.rssreader.data.model.rss;

import org.parceler.Parcel;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by admin on 26.07.2016.
 */

@Parcel
@Default(DefaultType.FIELD)
@Root(name = "rss", strict = false)
public class RssResponse {

    public RssResponse(){}

    @Element
    public Channel channel;

    @Override
    public String toString() {
        return "RssResponse{" +
                "channel=" + channel +
                '}';
    }
}
