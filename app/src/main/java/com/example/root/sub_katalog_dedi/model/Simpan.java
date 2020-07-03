package com.example.root.sub_katalog_dedi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Simpan implements Parcelable {


    private String title_parcelable;
    private Integer id;
    private String release_parselable;
    private String img_url_parselable;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Simpan(){

    }

    private Simpan(Parcel in) {
        id = in.readInt();
        title_parcelable = in.readString();
        release_parselable = in.readString();
        img_url_parselable = in.readString();
        overview_parselable = in.readString();
    }

    public static final Creator<Simpan> CREATOR = new Creator<Simpan>() {
        @Override
        public Simpan createFromParcel(Parcel in) {
            return new Simpan(in);
        }

        @Override
        public Simpan[] newArray(int size) {
            return new Simpan[size];
        }
    };

    public String getRelease_parselable() {
        return release_parselable;
    }

    public void setRelease_parselable(String release_parselable) {
        this.release_parselable = release_parselable;
    }

    public String getImg_url_parselable() {
        return img_url_parselable;
    }

    public void setImg_url_parselable(String img_url_parselable) {
        this.img_url_parselable = img_url_parselable;
    }

    public String getOverview_parselable() {
        return overview_parselable;
    }

    public void setOverview_parselable(String overview_parselable) {
        this.overview_parselable = overview_parselable;
    }

    private String overview_parselable;


    public String getTitle_parcelable() {
        return title_parcelable;
    }

    public void setTitle_parcelable(String title_parcelable) {
        this.title_parcelable = title_parcelable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title_parcelable);
        dest.writeString(release_parselable);
        dest.writeString(img_url_parselable);
        dest.writeString(overview_parselable);
    }
}
