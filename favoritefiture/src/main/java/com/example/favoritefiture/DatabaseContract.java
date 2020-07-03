package com.example.favoritefiture;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAVORITE = "tbl_favorite";

    public static final class FavoriteColumns implements BaseColumns {

        public static String ID = "_id";
        public static String TITLE = "title";
        public static String IMG_URL = "img_url";
        public static String OVERVIEW = "overview";
        public static String DATE = "date";
    }

    public static final String AUTHORITY = "com.example.root.sub_katalog_dedi";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}
