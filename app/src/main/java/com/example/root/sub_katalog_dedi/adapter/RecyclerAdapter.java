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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;




public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    private String ukuran = "w92";
    private List<ResultsItem> resultItemList;
    private Context mContext;
    private ResultsItem resultsItem;

    public RecyclerAdapter(List<ResultsItem> resultItemList, Context mContext) {
        this.resultItemList = new ArrayList<>();
        this.resultItemList = resultItemList;
        this.resultItemList.addAll(resultItemList);
        this.mContext = mContext;
        notifyDataSetChanged();
    }

    public void replaceAll(List<ResultsItem> items) {
        resultItemList.clear();
        resultItemList = items;
        notifyDataSetChanged();
    }
//    public List<ResultsItem> getList(){
//        return resultItemList;
//    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_list,parent,false);
        return new RecyclerViewHolder(itemView);
    }

    public void setResultItem(List<ResultsItem> resultItem){
        this.resultItemList = resultItem;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        resultsItem = resultItemList.get(position);
        if (resultsItem.getTitle().length() >=20){
            holder.txt_title.setText(resultsItem.getTitle().substring(0,20)+"...");
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
//                Toast.makeText(v.getContext(), "Holder ::: "+resultItemList.get(position).getTitle()
//                        , Toast.LENGTH_SHORT).show();
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
    static class RecyclerViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.txt_title)
        TextView txt_title;
        @BindView(R.id.txt_overview)
        TextView txt_overview;
        @BindView(R.id.txt_releasedate)
        TextView txt_releasedate;
        @BindView(R.id.img_poster)
        ImageView img_poster;
        @BindView(R.id.base_layout)
        LinearLayout layout;
        @BindView(R.id.btn_detail)
        Button btn_detail;
        @BindView(R.id.btn_share)
        Button btn_share;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}


