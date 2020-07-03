package com.example.root.sub_katalog_dedi.navigasi;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.activity.FavoriteActivity;
import com.example.root.sub_katalog_dedi.activity.SearchActivity;
import com.example.root.sub_katalog_dedi.database.DatabaseContract;
import com.example.root.sub_katalog_dedi.fragment.NowPlayingFragment;
import com.example.root.sub_katalog_dedi.fragment.UpComingFragment;
import com.example.root.sub_katalog_dedi.model.MovieFavorite;
import com.example.root.sub_katalog_dedi.setting.SettingsActivity;

import java.util.ArrayList;

import butterknife.BindView;



public class NavigasiActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG ="Navigasi" ;
    @BindView(R.id.fragment_container)
    FrameLayout fragment_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigasi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null){
            //Handle the initial fragment transaction
            FragmentManager fM = getSupportFragmentManager();
            fM.beginTransaction().replace(R.id.fragment_container,new NowPlayingFragment()).commit();
        }
    }



            @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigasi, menu);

        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra(SearchActivity.MOVIE_TITLE, query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(this, SettingsActivity.class);
            startActivity(mIntent);
            return true;
        }
        if (id == R.id.action_bahasa){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
            return true;
        }
        if (id == R.id.action_favorite) {
            ArrayList<MovieFavorite> movieFavoriteArrayList = new ArrayList<>();
            Log.i(TAG, "onOptionsItemSelected: hh "+DatabaseContract.CONTENT_URI);
            Cursor cursor = getContentResolver().query(DatabaseContract.CONTENT_URI, null,
                    null, null, null, null);
            cursor.moveToFirst();
            MovieFavorite favorite;
            if(cursor.getCount() > 0){
                do{
                    favorite = new MovieFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(
                            DatabaseContract.NoteColumns.ID)));
                    movieFavoriteArrayList.add(favorite);
                    cursor.moveToNext();
                }while(!cursor.isAfterLast());
            }
            Intent intent = new Intent(NavigasiActivity.this, FavoriteActivity.class);
            intent.putExtra(DatabaseContract.NoteColumns.ID, movieFavoriteArrayList);
            intent.putExtra(DatabaseContract.NoteColumns.TITLE, "detail");
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_now_playin) {
            initFragment(new NowPlayingFragment());
        } else if (id == R.id.nav_upcoming) {
            initFragment(new UpComingFragment());
        }
        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void initFragment(Fragment classFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, classFragment);
        transaction.commit();
    }


}
