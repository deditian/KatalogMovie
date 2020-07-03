package com.example.root.sub_katalog_dedi.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.activity.Detail_Main;
import com.example.root.sub_katalog_dedi.model.ResultsItem;
import com.example.root.sub_katalog_dedi.model.Simpan;
import com.example.root.sub_katalog_dedi.util.DataFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;




public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder>{
    private String ukuran = "w92";
    private List<ResultsItem> resultItemList ;
    private Context mContext;
    private ResultsItem resultsItem;
    public GridAdapter(List<ResultsItem> resultItemList, Context mContext) {
        this.resultItemList = new ArrayList<>();
        this.resultItemList = resultItemList;
        this.resultItemList.addAll(resultItemList);
        this.resultItemList = resultItemList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }
    public List<ResultsItem> getList(){
        return resultItemList;
    }
    public void setResultItem(List<ResultsItem> resultItem){
        this.resultItemList = resultItem;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_list_grid,parent,false);
        return new GridViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, final int position) {
        resultsItem = resultItemList.get(position);
        if (resultsItem.getTitle().length() >=15){
            holder.txt_title.setText(resultsItem.getTitle().substring(0,15)+"...");
        }else{
            holder.txt_title.setText(resultsItem.getTitle());
        }

        holder.txt_overview.setText(resultsItem.getOverview());
        if (resultsItem.getOverview().equals("")){
            holder.txt_overview.setText(mContext.getResources().getString(R.string.overview_empty));
        }
        else{
            holder.txt_overview.setText(resultsItem.getOverview());
        }
        holder.txt_releasedate.setText(DataFormat.input(resultsItem.getReleaseDate()));
        Log.i(TAG, "onBindViewHolder: tanggal"+resultsItem.getReleaseDate());
        final String url_img = "http://image.tmdb.org/t/p/"+ukuran+resultsItem.getPosterPath();
        Log.i(TAG, "onBindViewHolder: "+url_img);
        Glide.with(holder.itemView.getContext())
                .load(url_img)
                .apply(new RequestOptions()
                        .placeholder(R.color.colorPrimary))
                .into(holder.img_poster);
        Log.i(TAG, "onBindViewHolder: "+resultsItem.getTitle());
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Holder ::: "+resultItemList.get(position).getTitle()
                        , Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onClick: " + resultItemList.get(position).getTitle());
                Log.i(TAG, "onClick: "+position);

                Log.i(TAG, "onClick: "+position);

                Simpan msimpan = new Simpan();
                msimpan.setId(resultItemList.get(position).getId());
                msimpan.setTitle_parcelable(resultItemList.get(position).getTitle());
                msimpan.setImg_url_parselable("http://image.tmdb.org/t/p/"+ukuran+resultItemList.get(position).getPosterPath());
                msimpan.setOverview_parselable(resultItemList.get(position).getOverview());
                msimpan.setRelease_parselable(resultItemList.get(position).getReleaseDate());
                Intent detail_main = new Intent(v.getContext(),Detail_Main.class);
                detail_main.putExtra(Detail_Main.EXTRA_PERSON,msimpan);
                v.getContext().startActivity(detail_main);
            }
        });

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = resultItemList.get(position).getTitle();
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
        return resultItemList.size();
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

    static class GridViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.txt_title_grid)
        TextView txt_title;
        @BindView(R.id.txt_overview_grid)
        TextView txt_overview;
        @BindView(R.id.txt_releasedate_grid)
        TextView txt_releasedate;
        @BindView(R.id.img_poster_grid)
        ImageView img_poster;
        @BindView(R.id.btn_detail_grid)
        Button btn_detail;
        @BindView(R.id.btn_share_grid)
        Button btn_share;


        public GridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}


