package com.vozye.sms.dogbreeds.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vozye.sms.dogbreeds.R;

import java.util.List;

/**
 * Created by sumair on 12/16/2017.
 */

public class BreedListingAdapter extends RecyclerView.Adapter<BreedListingAdapter.MyViewHolder>  {
    private List<String> breedsList;


    public BreedListingAdapter(Context context, List<String> breedsList) {
        this.breedsList = breedsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.breeds_listing_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvBreedName.setText(breedsList.get(position));
    }

    @Override
    public int getItemCount() {
        return breedsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvBreedName;

        MyViewHolder(View view) {
            super(view);
            tvBreedName = (TextView) view.findViewById(R.id.tvName);
        }
    }

}