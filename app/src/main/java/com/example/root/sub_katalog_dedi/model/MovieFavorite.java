package com.example.root.sub_katalog_dedi.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.DATE;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.ID;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.IMG_URL;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.OVERVIEW;
import static com.example.root.sub_katalog_dedi.database.DatabaseContract.NoteColumns.TITLE;

public class MovieFavorite implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String image;
    private String date;


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String Date) {
        date = Date;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieFavorite(Integer id, String image,String overview, String title,String date) {
        this.id = id;
        this.image = image;
        this.overview = overview;
        this.title = title;
        this.date = date;
    }

    public MovieFavorite(Integer id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.image);
        dest.writeString(this.date);
    }

    protected MovieFavorite(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.image = in.readString();
        this.date = in.readString();
    }

    public static final Creator<MovieFavorite> CREATOR = new Creator<MovieFavorite>() {
        @Override
        public MovieFavorite createFromParcel(Parcel source) {
            return new MovieFavorite(source);
        }

        @Override
        public MovieFavorite[] newArray(int size) {
            return new MovieFavorite[size];
        }
    };


    public MovieFavorite(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
        this.title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
        this.overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
        this.date = cursor.getString(cursor.getColumnIndexOrThrow(DATE));
        this.image = cursor.getString(cursor.getColumnIndexOrThrow(IMG_URL));
    }
}
