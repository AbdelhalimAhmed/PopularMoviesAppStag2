package com.example.abdelhalim.popularmoviesapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by abdelhalim on 12/08/16.
 */
public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    LayoutInflater inflater;
    ArrayList<String> posters;

    public ImageAdapter(Context c, ArrayList<String> posters) {
        mContext = c;
        this.posters = posters;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return posters.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
        convertView =  inflater.inflate(R.layout.grid_item, null);
        }

        Log.d("TAG", "getView: "+posters.get(position));

        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_imageview);
        Picasso.with(mContext)
                .load( "http://image.tmdb.org/t/p/w500"+posters.get(position))
                .into(imageView);

//        TextView textView = convertView.findViewById();
//        textView.setText(title.get(position));

        return convertView;
    }


}
