package com.vozye.sms.dogbreeds.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vozye.sms.dogbreeds.R;

import java.util.List;

import javax.sql.DataSource;

/**
 * Created by sumair on 12/17/2017.
 */

public class ImageAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private List<String > imageUrls;

    public ImageAdapter(Context context, List<String> imageUrls) {
        super(context, R.layout.gridview_item_image, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder viewHolder;

        if (convertView==null) {
            convertView = inflater.inflate(R.layout.gridview_item_image, parent, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(MyViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(imageUrls.get(position)).into(viewHolder.ivImg);
        
        return convertView;
    }
    class MyViewHolder{
        View view;
        ImageView ivImg;
        ProgressBar pbar;
        MyViewHolder(View view){
            this.view = view;
            this.ivImg = (ImageView) view.findViewById(R.id.imageView);
            this.pbar = (ProgressBar) view.findViewById(R.id.pbar);
        }
    }
}
