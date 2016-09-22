package com.example.abdelhalim.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import cz.msebera.android.httpclient.Header;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    @Override
    public void onStop() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onStop();
    }

    @Override
    public void onStart() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onStart();
    }

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onResume();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {

        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    String p;
    String j;
    String jj;
    Double jjj;
    String over;
    int id;
    String s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //Intent in1 = getIntent();
        p = (String) getArguments().get("poster");
        j =(String) getArguments().get("title");
        jj =(String) getArguments().get("date");
        jjj =(Double) getArguments().get("Vote");
        over = (String) getArguments().get("overview");
        id = getArguments().getInt("id", 1);

        s = jjj.toString();

        Log.d("log", "onCreate: "+id);

        realm = Realm.getDefaultInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        final TextView textView1 = (TextView) view.findViewById(R.id.title);
        TextView textView2 = (TextView) view.findViewById(R.id.filmDate);
        TextView textView3 = (TextView) view.findViewById(R.id.filmRate);
        TextView textView4 = (TextView) view.findViewById(R.id.tvOverview);
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lTrailers);
        final LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.lReviews);
        ImageView imageView = (ImageView) view.findViewById(R.id.poster);
        view.findViewById(R.id.btnFavorite).setOnClickListener(this);

        textView1.setText(j);
        textView2.setText(jj);
        textView3.setText(s);
        textView4.setText(over);

        Picasso.with(getActivity())
                .load(p)
                .into(imageView);
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url="http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=0091b1f19f36fbfe0321a163832a4c77";
        asyncHttpClient.get(getActivity(), url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                TrailersAPI trailersAPI = gson.fromJson(new String(responseBody), TrailersAPI.class);
                int i = 0;
                for (final TrailersAPI.ResultsBean result :  trailersAPI.getResults()){
                    result.getKey();
                    Log.d("log", "onSuccess: "+ result.getKey());
                    Button button = new Button(getContext());
                    button.setId(i+1);
                    button.setText(result.getName());
                    button.setLayoutParams(layoutParams);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v="+result.getKey())));
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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        AsyncHttpClient asyncHttpClient1 = new AsyncHttpClient();
        String url1="http://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=0091b1f19f36fbfe0321a163832a4c77";
        asyncHttpClient.get(getActivity(), url1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                ReviewsAPI reviewsAPI = gson.fromJson(new String(responseBody), ReviewsAPI.class);
                int i = 0;
                for (final ReviewsAPI.ResultsBean result :  reviewsAPI.getResults()){
                    TextView tv = new TextView(getContext());
                    tv.setId(i+1);
                    tv.setText(result.getAuthor());
                    tv.setLayoutParams(layoutParams);
                    linearLayout1.addView(tv);

                    TextView tv1 = new TextView(getContext());
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




        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
            Toast.makeText(getContext(),"Add to DataBase",Toast.LENGTH_SHORT).show();
        }catch (RealmPrimaryKeyConstraintException exception){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    RealmResults<Database> results = realm.where(Database.class).equalTo("id",id).findAll();
                    results.deleteAllFromRealm();
                }
            });

            Toast.makeText(getContext(),"Removed from DataBase",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
