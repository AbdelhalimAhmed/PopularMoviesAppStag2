package com.example.abdelhalim.popularmoviesapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abdelhalim.popularmoviesapp.API.ApiMovie;
import com.example.abdelhalim.popularmoviesapp.Adapter.ImageAdapter;
import com.example.abdelhalim.popularmoviesapp.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TopRateFragment extends Fragment {
    final String TAG = "TOPRATEACTIVITYtesting";
    ApiMovie apiMovie = new ApiMovie();
    ProgressBar progressBar2 ;
    ArrayList<String> posters = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> date =  new ArrayList<>();
    ArrayList<Double> vote = new ArrayList<>();
    ArrayList<String> overview = new ArrayList<>();
    ArrayList<Integer> ids = new ArrayList<>();
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_top_rate_fragment,container,false);

        progressBar2 = (ProgressBar) v.findViewById(R.id.progressBar2);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = "http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=0091b1f19f36fbfe0321a163832a4c77";
        asyncHttpClient.get(getActivity(), url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar2.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                apiMovie = gson.fromJson(new String(responseBody), ApiMovie.class);
                List<ApiMovie.ResultsBean> results = apiMovie.getResults();
                for (ApiMovie.ResultsBean result : results){
                    posters.add(result.getPoster_path());
                    title.add(result.getOriginal_title());
                    date.add(result.getRelease_date());
                    vote.add(result.getVote_average());
                    overview.add(result.getOverview());
                    ids.add(result.getId());
                }
                Log.d(TAG, "onSuccess: "+posters);

                GridView gridview = (GridView) v.findViewById(R.id.GridV1);
                gridview.setAdapter(new ImageAdapter(getActivity(), posters));


                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        Toast.makeText(getActivity(), "" + position,
                                Toast.LENGTH_SHORT).show();
                        DetailsFragment detailsFragment = new DetailsFragment();
                        Bundle bundle = new Bundle();

                        bundle.putString("poster", "https://image.tmdb.org/t/p/w185" + posters.get(position));
                        bundle.putString("title",title.get(position));
                        bundle.putString("date",date.get(position));
                        bundle.putDouble("Vote", vote.get(position));
                        bundle.putString("overview",overview.get(position));
                        bundle.putInt("id",ids.get(position));

                        detailsFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,detailsFragment)
                                .addToBackStack("DetailsMovies").commit();
                    }
                });
                progressBar2.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return v;
    }
}
