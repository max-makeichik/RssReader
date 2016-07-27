package com.maxmakeychik.rssreader.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.maxmakeychik.rssreader.data.model.ImageEntity;
import com.maxmakeychik.rssreader.data.model.Post;
import com.maxmakeychik.rssreader.data.model.RssSubscription;

public class Db {

    public Db() { }

    public abstract static class SubscriptionTable {
        public static final String TABLE_NAME = "subscription";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_IMAGE_WIDTH = "image_width";
        public static final String COLUMN_IMAGE_HEIGHT = "image_height";
        public static final String COLUMN_TITLE = "title";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_URL + " TEXT NOT NULL, " +
                        COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                        COLUMN_IMAGE_WIDTH + " TEXT NOT NULL, " +
                        COLUMN_IMAGE_HEIGHT + " TEXT NOT NULL, " +
                        COLUMN_TITLE + " TEXT NOT NULL" +
                " ); ";

        public static RssSubscription parseCursor(Cursor cursor) {
            RssSubscription rssSubscription = new RssSubscription();
            rssSubscription.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            rssSubscription.url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL));
            rssSubscription.imageEntity = new ImageEntity(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_WIDTH)), cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_HEIGHT)));
            rssSubscription.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            return rssSubscription;
        }
    }

    public abstract static class PostTable {
        public static final String TABLE_NAME = "post";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_SUBSCRIPTION_ID = "subscription_id";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                        COLUMN_SUBSCRIPTION_ID + " INTEGER, " +
                        " FOREIGN KEY (" + COLUMN_SUBSCRIPTION_ID + ") REFERENCES " + SubscriptionTable.TABLE_NAME + "(" + SubscriptionTable.COLUMN_ID + ")" +
                        " ); ";

        public static ContentValues toContentValues(Post post) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, post.title);
            values.put(COLUMN_DESCRIPTION, post.description);
            values.put(COLUMN_SUBSCRIPTION_ID, post.subscriptionId);
            return values;
        }

        public static Post parseCursor(Cursor cursor) {
            Post post = new Post();
            post.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            post.description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            return post;
        }
    }

}
