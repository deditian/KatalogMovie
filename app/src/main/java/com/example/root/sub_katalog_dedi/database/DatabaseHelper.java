

package com.example.root.sub_katalog_dedi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.DATE;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.ID;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.IMG_URL;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.OVERVIEW;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.TITLE;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.TABLE_FAVORITE;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "catalogue_movie";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_FAVORITE = "create table " + TABLE_FAVORITE + " (" +
                ID + " integer primary key autoincrement, " +
                IMG_URL + " text not null, " +
                TITLE + " text not null, " +
                OVERVIEW + " text not null, " +
                DATE + " text not null " +

                ");";

        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(sqLiteDatabase);

    }
}
