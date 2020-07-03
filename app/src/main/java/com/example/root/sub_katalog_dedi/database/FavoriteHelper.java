package com.example.root.sub_katalog_dedi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.ID;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {
    private static String DATABASE_TABLE = TABLE_FAVORITE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,ID + " = ?", new String[]{id});
    }
}
