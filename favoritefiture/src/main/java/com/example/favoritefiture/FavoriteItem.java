package com.example.favoritefiture;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.favoritefiture.DatabaseContract.FavoriteColumns.DATE;
import static com.example.favoritefiture.DatabaseContract.FavoriteColumns.ID;
import static com.example.favoritefiture.DatabaseContract.FavoriteColumns.IMG_URL;
import static com.example.favoritefiture.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.favoritefiture.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.favoritefiture.DatabaseContract.getColumnInt;
import static com.example.favoritefiture.DatabaseContract.getColumnString;

public class FavoriteItem implements Parcelable {
    private int id;
    private String title, img_url, overview, date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.img_url);
        dest.writeString(this.overview);
        dest.writeString(this.date);
    }

    public FavoriteItem() {
    }

    public FavoriteItem(Cursor cursor){
        this.id = getColumnInt(cursor, ID);
        this.title = getColumnString(cursor, TITLE);
        this.title = getColumnString(cursor, IMG_URL);
        this.img_url = getColumnString(cursor, OVERVIEW);
        this.date = getColumnString(cursor,DATE);
    }

    protected FavoriteItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.img_url = in.readString();
        this.overview = in.readString();
        this.date = in.readString();
    }

    public static final Creator<FavoriteItem> CREATOR = new Creator<FavoriteItem>() {
        @Override
        public FavoriteItem createFromParcel(Parcel source) {
            return new FavoriteItem(source);
        }

        @Override
        public FavoriteItem[] newArray(int size) {
            return new FavoriteItem[size];
        }
    };
}
