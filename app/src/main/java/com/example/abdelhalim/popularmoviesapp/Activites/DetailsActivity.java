package com.example.abdelhalim.popularmoviesapp.Activites;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdelhalim.popularmoviesapp.API.ReviewsAPI;
import com.example.abdelhalim.popularmoviesapp.API.TrailersAPI;
import com.example.abdelhalim.popularmoviesapp.DataBase.Database;
import com.example.abdelhalim.popularmoviesapp.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import cz.msebera.android.httpclient.Header;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    int i =0;
    String p;
    String j;
    String jj;
    Double jjj;
    String over;
    int id;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        TextView textView1 = (TextView) findViewById(R.id.title);
        TextView textView2 = (TextView) findViewById(R.id.filmDate);
        TextView textView3 = (TextView) findViewById(R.id.filmRate);
        TextView textView4 = (TextView) findViewById(R.id.tvOverview);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lTrailers);
        final LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.lReviews);
        findViewById(R.id.btnFavorite);

        ImageView imageView = (ImageView) findViewById(R.id.poster);




        //Intent in1 = getIntent();
         p = (String) getIntent().getExtras().get("poster");
         j =(String) getIntent().getExtras().get("title");
         jj =(String) getIntent().getExtras().get("date");
         jjj =(Double) getIntent().getExtras().get("Vote");
         over = (String) getIntent().getExtras().get("overview");
         id = getIntent().getIntExtra("id", 1);

         s = jjj.toString();

        Log.d("log", "onCreate: "+id);
        realm = Realm.getDefaultInstance();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url="http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=0091b1f19f36fbfe0321a163832a4c77";
        asyncHttpClient.get(this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                TrailersAPI trailersAPI = gson.fromJson(new String(responseBody), TrailersAPI.class);

                for (final TrailersAPI.ResultsBean result :  trailersAPI.getResults()){
                    result.getKey();
                    Log.d("log", "onSuccess: "+ result.getKey());
                    Button button = new Button(getBaseContext());
                    button.setId(i+1);
                    button.setText(result.getName());
                    button.setLayoutParams(layoutParams);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+result.getKey())));
                        }
                    });

                    i++;
                    linearLayout.addView(button);

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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        AsyncHttpClient asyncHttpClient1 = new AsyncHttpClient();
        String url1="http://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=0091b1f19f36fbfe0321a163832a4c77";
        asyncHttpClient.get(this, url1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                ReviewsAPI reviewsAPI = gson.fromJson(new String(responseBody), ReviewsAPI.class);
                int i = 0;
                for (final ReviewsAPI.ResultsBean result :  reviewsAPI.getResults()){
                    TextView tv = new TextView(getBaseContext());
                    tv.setId(i+1);
                    tv.setText(result.getAuthor());
                    tv.setLayoutParams(layoutParams);
                    linearLayout1.addView(tv);

                    TextView tv1 = new TextView(getBaseContext());
                    tv1.setId(i+20);
                    tv1.setText(result.getContent());
                    tv1.setLayoutParams(layoutParams);
                    linearLayout1.addView(tv1);
                    i++;
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });




    }


    @Override
    public void onClick(View v) {
        faviroutMovies();
    }
    Realm realm ;
    private void faviroutMovies(){
        try {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    Database database = realm.createObject(Database.class, id);
                    database.name = j;
                    database.data = jj;
                    database.overView = over;
                    database.rating = jjj;
                    database.poster = p;
                    database.id = id;


                }
            });
            Toast.makeText(getBaseContext(),"Add to DataBase",Toast.LENGTH_SHORT).show();
        }catch (RealmPrimaryKeyConstraintException exception){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    RealmResults<Database> results = realm.where(Database.class).equalTo("id",id).findAll();
                    results.deleteAllFromRealm();
                }
            });

            Toast.makeText(getBaseContext(),"Removed from DataBase",Toast.LENGTH_SHORT).show();
        }
    }


}