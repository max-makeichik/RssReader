package com.maxmakeychik.rssreader.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.maxmakeychik.rssreader.data.model.Post;
import com.maxmakeychik.rssreader.data.model.RssSubscription;

public class Db {

    public Db() { }

    public abstract static class SubscriptionTable {
        public static final String TABLE_NAME = "subscription";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_URL = "url";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_URL + " TEXT NOT NULL" +
                        " );";

        public static final String INSERT_1 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + "," + COLUMN_URL + ")" +
                " VALUES ('sample1', 'http://www.feedforall.com/sample.xml'" +
                ");";
        public static final String INSERT_2 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + "," + COLUMN_URL + ")" +
                " VALUES ('sample-feed', 'http://www.feedforall.com/sample-feed.xml'" +
                ");";
        public static final String INSERT_3 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + "," + COLUMN_URL + ")" +
                " VALUES ('Netflix Top 100', 'http://dvd.netflix.com/Top100RSS'" +
                ");";
        public static final String INSERTS = INSERT_1 + INSERT_2 + INSERT_3;
        private static final String TAG = "Db";

        public static RssSubscription parseCursor(Cursor cursor) {
            RssSubscription rssSubscription = new RssSubscription();
            rssSubscription.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            rssSubscription.url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL));
            rssSubscription.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            return rssSubscription;
        }

        public static ContentValues toContentValues(RssSubscription rssSubscription) {
            ContentValues values = new ContentValues();
            Log.d(TAG, "toContentValues: " + rssSubscription);
            values.put(COLUMN_URL, rssSubscription.url);
            if(rssSubscription.title != null) {
                values.put(COLUMN_TITLE, rssSubscription.title);
            }
            Log.d(TAG, "toContentValues: " + values);
            return values;
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
