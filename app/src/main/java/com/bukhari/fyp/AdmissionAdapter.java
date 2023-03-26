package com.bukhari.fyp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdmissionAdapter extends RecyclerView.Adapter<AdmissionAdapter.AdmissionViewHolder> {

    private ArrayList<AdmissionCardData> dataList;

    public static class AdmissionViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView title;
        public TextView subTitle;
        public AdmissionViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textViewTitle);
            subTitle = itemView.findViewById(R.id.textViewSubTitle);
        }
    }

    public AdmissionAdapter(ArrayList<AdmissionCardData> list){
        dataList = list;
    }


    @NonNull
    @Override
    public AdmissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.undergraudate_item,parent,false);
        AdmissionViewHolder AdViewHolder = new AdmissionViewHolder(view);
        return AdViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdmissionViewHolder holder, int position) {
        AdmissionCardData currentCard = dataList.get(position);
        holder.mImageView.setImageResource(currentCard.getmImageResource());
        holder.title.setText(currentCard.getTitle());
        holder.subTitle.setText(currentCard.getSubTitle());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
