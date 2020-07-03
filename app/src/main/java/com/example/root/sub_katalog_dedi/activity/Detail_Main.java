package com.example.root.sub_katalog_dedi.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.database.DatabaseContract;
import com.example.root.sub_katalog_dedi.model.ResultsItem;
import com.example.root.sub_katalog_dedi.model.Simpan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Detail_Main extends AppCompatActivity {

    public static final String EXTRA_PERSON = "extra_person";

    private static final String TAG = "DETAIL_MAIN";

    @BindView(R.id.txt_date_detail)
    TextView txt_date_detail;
    @BindView(R.id.txt_overview_detail)
    TextView txt_overview_detail;
    @BindView(R.id.txt_title_detail)
    TextView txt_title_detail;
    @BindView(R.id.img_url_detail)
    ImageView img_url_detail;
    @BindView(R.id.floatingActionButtonFavorite)
    FloatingActionButton floatingActionButtonFavorite;

    ResultsItem resultItem ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_main);
        ButterKnife.bind(this);

        Simpan msimpan = getIntent().getParcelableExtra(EXTRA_PERSON);
        final Integer  id = msimpan.getId();
        final String  title = msimpan.getTitle_parcelable();
        final String  overview = msimpan.getOverview_parselable();
        final String  release_date = msimpan.getRelease_parselable();
        final String img_ulr = msimpan.getImg_url_parselable();
        Log.i(TAG, "onCreate: gambar"+img_ulr);
        Log.i(TAG, "onCreate: "+title);
        resultItem = getIntent().getParcelableExtra(title);
        txt_title_detail.setText(title);
        txt_overview_detail.setText(overview);
        txt_date_detail.setText(release_date);


        Glide.with(Detail_Main.this)
                .load(img_ulr)
                .apply(new RequestOptions()
                        .placeholder(R.color.colorPrimary))
                .into(img_url_detail);
        if (isRecordExists(id.toString())) {
            if (floatingActionButtonFavorite != null) {
                floatingActionButtonFavorite.setImageResource(R.drawable.ic_favorite_border);
                Log.v("MovieDetail", "" + id);
            }
        }
        if(floatingActionButtonFavorite != null) {
            floatingActionButtonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isRecordExists(id.toString())) {
                        Uri uri = DatabaseContract.CONTENT_URI;
                        uri = uri.buildUpon().appendPath(String.valueOf(id)).build();
                        Log.v("MovieDetail", "" + uri);

                        getContentResolver().delete(uri, null, null);
                        floatingActionButtonFavorite.setImageResource(R.drawable.ic_favorite_border);
                        Log.v("MovieDetail", uri.toString());
                        Snackbar.make(view, "This movie has been remove from your favorite", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseContract.NoteColumns.ID, id);
                        contentValues.put(DatabaseContract.NoteColumns.TITLE, title);
                        contentValues.put(DatabaseContract.NoteColumns.IMG_URL, img_ulr);
                        contentValues.put(DatabaseContract.NoteColumns.DATE, release_date);
                        contentValues.put(DatabaseContract.NoteColumns.OVERVIEW, overview);

                        getContentResolver().insert(DatabaseContract.CONTENT_URI, contentValues);
                        floatingActionButtonFavorite.setImageResource(R.drawable.ic_favorite);
                        Snackbar.make(view, "This movie has been add to your favorite", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }

    }
    private boolean isRecordExists(String id) {
        String selection = "_id = ?";
        String[] selectionArgs = { id };
        String[] projection = {DatabaseContract.NoteColumns.ID};
        Uri uri = DatabaseContract.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();

        Cursor cursor = getContentResolver().query(uri, projection ,
                selection, selectionArgs, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        Log.v("isi", Boolean.toString(exists));
        return exists;
    }


}
