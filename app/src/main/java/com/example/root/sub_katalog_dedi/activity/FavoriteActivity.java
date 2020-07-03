package com.example.root.sub_katalog_dedi.activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.adapter.FavoriteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.root.sub_katalog_dedi.database.DatabaseContract.CONTENT_URI;


public class FavoriteActivity extends AppCompatActivity {
    @BindView(R.id.rvresult_favorite)
    RecyclerView rvFavorite;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private Cursor list;
    private FavoriteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setHasFixedSize(true);
        getSupportActionBar().setTitle(R.string.favorite);
        adapter = new FavoriteAdapter(this);
        adapter.setListFavorite(list);
        rvFavorite.setAdapter(adapter);
        new LoadNoteAsync().execute();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor favorites) {
            super.onPostExecute(favorites);
            progressBar.setVisibility(View.GONE);

            list = favorites;
            adapter.setListFavorite(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void showSnackbarMessage(String message){
        Snackbar.make(rvFavorite, message, Snackbar.LENGTH_SHORT).show();
    }
}
