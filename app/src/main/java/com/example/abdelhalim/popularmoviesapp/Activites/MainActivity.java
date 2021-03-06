package com.example.abdelhalim.popularmoviesapp.Activites;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.abdelhalim.popularmoviesapp.Adapter.TabsAdapter;
import com.example.abdelhalim.popularmoviesapp.Fragments.DetailsFragment;
import com.example.abdelhalim.popularmoviesapp.Fragments.FavoriteFragment;
import com.example.abdelhalim.popularmoviesapp.Fragments.PopularFragment;
import com.example.abdelhalim.popularmoviesapp.Fragments.TopRateFragment;
import com.example.abdelhalim.popularmoviesapp.R;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity implements DetailsFragment.OnFragmentInteractionListener, FavoriteFragment.OnFragmentInteractionListener {

    public static boolean mTwoPane;
    public static final String DETAIL_FRAGMENT_TAG = "DFTAG";
    @Override
    protected void onStart() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Fragment f1 = new PopularFragment();
        Fragment f2 = new TopRateFragment();
        Fragment f3 = new DetailsFragment();
        Fragment[] fragments = {f1, f2};

        TabsAdapter tabsAdapterUser = new TabsAdapter(getSupportFragmentManager(), fragments);
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(tabsAdapterUser);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };
        actionBar.addTab(actionBar.newTab().setText("Popular Movies").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Top Rate Movies").setTabListener(tabListener));



        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });


        FrameLayout flPanel2 = (FrameLayout) findViewById(R.id.flPanel_two);
        if(null == flPanel2){
            mTwoPane=true;
            getSupportActionBar().setElevation(0f);
        }else {
            mTwoPane = false;
            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flPanel_two, new DetailsFragment(), DETAIL_FRAGMENT_TAG)
                        .commit();}
        }



    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Favorite:
                Toast.makeText(getApplicationContext(),"Favourite Movies",Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity, new FavoriteFragment()).addToBackStack("FavouriteMovies").commit();

                return true;
            case R.id.action_refresh:
                Toast.makeText(getApplicationContext(),"Refresh",Toast.LENGTH_LONG).show();
                //getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,this).commit();

                //progressBar.setVisibility(View.VISIBLE);ss
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
