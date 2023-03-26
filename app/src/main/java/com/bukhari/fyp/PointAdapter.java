package com.bukhari.fyp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.PointViewHolder> implements Filterable {

    private ArrayList<PointCardData> dataList;
    private ArrayList<PointCardData> fullList;



    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        // run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<PointCardData> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.addAll(fullList);
            }else{
                String patternfilter = constraint.toString().toLowerCase().toString().trim();

                for (PointCardData point: fullList) {
                    if(point.getTitle().toLowerCase().contains(patternfilter)){
                        filteredList.add(point);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        // run on ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((Collection<? extends PointCardData>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class PointViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView title;
        public TextView subTitle;

        public PointViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textViewTitle);
            subTitle = itemView.findViewById(R.id.textViewSubTitle);
        }
    }

    public PointAdapter(ArrayList<PointCardData> list){
        dataList = list;
        fullList = new ArrayList<>(dataList);
    }


    @NonNull
    @Override
    public PointAdapter.PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.undergraudate_item,parent,false);
        PointAdapter.PointViewHolder AdViewHolder = new PointAdapter.PointViewHolder(view);
        return AdViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointAdapter.PointViewHolder holder, int position) {
        PointCardData currentCard = dataList.get(position);
        holder.mImageView.setImageResource(currentCard.getmImageResource());
        holder.title.setText(currentCard.getTitle());
        holder.subTitle.setText(currentCard.getSubTitle());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
