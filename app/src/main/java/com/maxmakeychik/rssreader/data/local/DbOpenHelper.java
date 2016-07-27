package com.maxmakeychik.rssreader.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.maxmakeychik.rssreader.injection.ApplicationContext;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import javax.inject.Inject;

public class DbOpenHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "rss_reader.db";
    private static final int DATABASE_VERSION = 1;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

}