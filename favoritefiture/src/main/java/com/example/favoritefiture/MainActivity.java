package com.example.favoritefiture;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.favoritefiture.adapter.FavoriteAdapterCataloge;

import static com.example.favoritefiture.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {


    private FavoriteAdapterCataloge FavoriteAdapter;
    ListView lvNotes;
    private final int LOAD_NOTES_ID = 110;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Dicoding Notes");

        lvNotes = (ListView)findViewById(R.id.lv_favorite);
        FavoriteAdapter = new FavoriteAdapterCataloge(this, null, true);
        lvNotes.setAdapter(FavoriteAdapter);
        lvNotes.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        FavoriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        FavoriteAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_NOTES_ID);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Cursor cursor = (Cursor) FavoriteAdapter.getItem(position);

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID));
//        Intent intent = new Intent(MainActivity.this, FormActivity.class);
//        intent.setData(Uri.parse(CONTENT_URI+"/"+id));
//        startActivity(intent);
        Toast.makeText(this, "ID "+id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
