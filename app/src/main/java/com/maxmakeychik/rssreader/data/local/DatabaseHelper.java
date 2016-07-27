package com.maxmakeychik.rssreader.data.local;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.maxmakeychik.rssreader.data.model.Post;
import com.maxmakeychik.rssreader.data.model.RssSubscription;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@Singleton
public class DatabaseHelper {

    private static final String TAG = "DatabaseHelper";

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<List<RssSubscription>> getSubscriptions() {
        return mDb.createQuery(Db.SubscriptionTable.TABLE_NAME,
                "SELECT * FROM " + Db.SubscriptionTable.TABLE_NAME)
                .mapToList(Db.SubscriptionTable::parseCursor);
    }

    public Observable<List<Post>> getPosts(int subscriptionId) {
        Log.d(TAG, "getPosts: " + subscriptionId);
        return mDb.createQuery(Db.PostTable.TABLE_NAME,
                "SELECT " + Db.PostTable.TABLE_NAME + "." + Db.PostTable.COLUMN_TITLE + ", " +
                        Db.PostTable.TABLE_NAME + "." + Db.PostTable.COLUMN_DESCRIPTION +
                        " FROM " + Db.PostTable.TABLE_NAME + " WHERE " + Db.PostTable.COLUMN_SUBSCRIPTION_ID + " = " + subscriptionId)
                .mapToList(Db.PostTable::parseCursor);
    }

    public Observable<Post> setPosts(List<Post> posts, int subscriptionId) {
        Log.d(TAG, "setPosts");
        return Observable.create(new Observable.OnSubscribe<Post>() {
            @Override
            public void call(Subscriber<? super Post> subscriber) {
                Log.d(TAG, "setPosts call");
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    for (Post post : posts) {
                        post.subscriptionId = subscriptionId;
                        long result = mDb.insert(Db.PostTable.TABLE_NAME,
                                Db.PostTable.toContentValues(post),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) {
                            //Log.d(TAG, "insert successful");
                            subscriber.onNext(post);
                        }
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }
}