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
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.gridview_item_image, parent, false);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.pbar);
        Target t = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                progressBar.setVisibility(View.VISIBLE);
            }
        };
        Picasso.with(context).load(imageUrls.get(position)).into(t);
        imageView.setTag(t);


        return convertView;
    }
//    private SimpleTarget target = new SimpleTarget<Bitmap>() {
//        @Override
//        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
//            // do something with the bitmap
//            // for demonstration purposes, let's just set it to an ImageView
//            imageView.setImageBitmap(bitmap);
//            imageView.setVisibility(View.VISIBLE);
//            pbar.setVisibility(View.GONE);
//        }
//    };
}
