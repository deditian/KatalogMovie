package com.example.favoritefiture.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.favoritefiture.R;

import static android.support.constraint.Constraints.TAG;
import static com.example.favoritefiture.DatabaseContract.FavoriteColumns.DATE;
import static com.example.favoritefiture.DatabaseContract.FavoriteColumns.IMG_URL;
import static com.example.favoritefiture.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.favoritefiture.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.favoritefiture.DatabaseContract.getColumnString;

public class FavoriteAdapterCataloge extends CursorAdapter {



    public FavoriteAdapterCataloge(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);
        return view;
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            TextView tvTitle = view.findViewById(R.id.txt_title);
            TextView tvDate = view.findViewById(R.id.txt_releasedate);
            TextView tvoverview = view.findViewById(R.id.txt_overview);
            ImageView imgPoster = view.findViewById(R.id.img_poster);

            tvTitle.setText(getColumnString(cursor,TITLE));
            tvoverview.setText(getColumnString(cursor,OVERVIEW));
            tvDate.setText(getColumnString(cursor,DATE));
            String img = getColumnString(cursor,IMG_URL);
            Log.i(TAG, "bindView: img"+img);
            Glide.with(view.getContext())
                    .load(img)
                    .apply(new RequestOptions()
                            .placeholder(R.color.colorPrimary))
                    .into(imgPoster);
        }
    }
}