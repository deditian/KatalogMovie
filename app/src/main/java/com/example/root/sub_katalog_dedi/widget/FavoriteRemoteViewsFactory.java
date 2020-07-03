package com.example.root.sub_katalog_dedi.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.database.DatabaseContract;
import com.example.root.sub_katalog_dedi.model.MovieFavorite;
import com.example.root.sub_katalog_dedi.util.DataFormat;

import java.util.concurrent.ExecutionException;

import static android.support.constraint.Constraints.TAG;

public class FavoriteRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    int mAppWidgetId;
    private Cursor cursor;
    private Context mContext;


    public FavoriteRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                DatabaseContract.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                DatabaseContract.CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        MovieFavorite movieFavorite = getFavPos(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.fav_widget_item);
        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(movieFavorite.getImage())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            rv.setImageViewBitmap(R.id.img_widget,bmp);
            Log.i(TAG, "getViewAt: gambar"+movieFavorite.getImage());
            rv.setTextViewText(R.id.tv_favorite_date, DataFormat.input(movieFavorite.getDate()));
        }catch (InterruptedException | ExecutionException e){
            Log.i("Widget Load Error",e.toString().trim());
        }
        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.img_widget, fillInIntent);
        return rv;
    }

    private MovieFavorite getFavPos(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new MovieFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(
                DatabaseContract.NoteColumns.ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.IMG_URL)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.OVERVIEW)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE)));
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
