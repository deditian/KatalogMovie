package com.example.root.sub_katalog_dedi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.activity.Detail_Main;
import com.example.root.sub_katalog_dedi.activity.FavoriteActivity;
import com.example.root.sub_katalog_dedi.model.MovieFavorite;
import com.example.root.sub_katalog_dedi.model.Simpan;
import com.example.root.sub_katalog_dedi.util.DataFormat;

import static android.support.constraint.Constraints.TAG;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewholder>{
    private Cursor listFavorite;
    private String ukuran = "w92";
    private List<MovieFavorite> favoriteModelList ;
    private Activity activity;
    private Context mContext;
    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }
    public FavoriteAdapter(Cursor listFavorite) {
        this.listFavorite = listFavorite;
    }


    @NonNull
    @Override
    public FavoriteViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new FavoriteViewholder(view);
    }

    public void setListFavorite(Cursor listFavorites) {
        this.listFavorite = listFavorites;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewholder holder, final int position) {
        final MovieFavorite favoriteModel = getItem(position);
        holder.txt_title.setText(favoriteModel.getTitle());
        holder.txt_releasedate.setText(DataFormat.input(favoriteModel.getDate()));
        holder.txt_overview.setText(favoriteModel.getOverview());
        final String url_img = favoriteModel.getImage();
        Log.i(TAG, "onBindViewHolder: gambar"+url_img);
        Glide.with(holder.itemView.getContext())
                .load(url_img)
                .apply(new RequestOptions()
                        .placeholder(R.color.colorPrimary))
                .into(holder.img_poster);
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Holder ::: "+favoriteModel.getTitle()
                        , Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onClick: " + favoriteModel.getTitle());
                Log.i(TAG, "onClick: "+position);

                Log.i(TAG, "onClick: "+position);

                Simpan msimpan = new Simpan();
                msimpan.setId(favoriteModel.getId());
                msimpan.setTitle_parcelable(favoriteModel.getTitle());
                msimpan.setImg_url_parselable(favoriteModel.getImage());
                msimpan.setOverview_parselable(favoriteModel.getOverview());
                msimpan.setRelease_parselable(input(favoriteModel.getDate()));

                Intent detail_main = new Intent(v.getContext(),Detail_Main.class);
                detail_main.putExtra(Detail_Main.EXTRA_PERSON,msimpan);

                v.getContext().startActivity(detail_main);
            }
        });

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = favoriteModel.getTitle();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT ,text);
                sendIntent.setType("text/plain");
                v.getContext().startActivity(sendIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    private MovieFavorite getItem(int position){
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
       return new MovieFavorite(listFavorite);

    }

    class FavoriteViewholder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_title)
        TextView txt_title;
        @BindView(R.id.txt_overview)
        TextView txt_overview;
        @BindView(R.id.txt_releasedate)
        TextView txt_releasedate;
        @BindView(R.id.img_poster)
        ImageView img_poster;
        @BindView(R.id.btn_detail)
        Button btn_detail;
        @BindView(R.id.btn_share)
        Button btn_share;

        FavoriteViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public   String  input (String input) {
        if (input.equals("") || input.isEmpty() || input == null) {
            Log.i(TAG, "input: kosong " + input);
            input = "kosong";
        }
        Log.i(TAG, "input: isi "+input);
        DateFormat inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = inputFormatter1.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat outputFormatter1 = new SimpleDateFormat("EEE, MMM d, yyyy");
        input = outputFormatter1.format(date1);


        return input;
    }
}