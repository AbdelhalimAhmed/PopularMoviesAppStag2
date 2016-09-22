package com.example.abdelhalim.popularmoviesapp;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        TextView textView1 = (TextView) findViewById(R.id.title);
        TextView textView2 = (TextView) findViewById(R.id.filmDate);
        TextView textView3 = (TextView) findViewById(R.id.filmRate);
        TextView textView4 = (TextView) findViewById(R.id.tvOverview);

        ImageView imageView = (ImageView) findViewById(R.id.poster);




        //Intent in1 = getIntent();
        String p = (String) getIntent().getExtras().get("poster");
        String j =(String) getIntent().getExtras().get("title");
        String jj =(String) getIntent().getExtras().get("date");
        Double jjj =(Double) getIntent().getExtras().get("Vote");
        String over = (String) getIntent().getExtras().get("overview");
        int id = getIntent().getIntExtra("id", 1);

        String s = jjj.toString();
        Log.d("log", "onCreate: "+id);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url="http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=0091b1f19f36fbfe0321a163832a4c77";
        asyncHttpClient.get(this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                TrailersAPI trailersAPI = gson.fromJson(new String(responseBody), TrailersAPI.class);

                for (TrailersAPI.ResultsBean result :  trailersAPI.getResults()){
                    result.getKey();
                    Log.d("log", "onSuccess: "+ result.getKey());


                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        textView1.setText(j);
        textView2.setText(jj);
        textView3.setText(s);
        textView4.setText(over);

        Picasso.with(this)
                .load(p)
                .into(imageView);



    }





}