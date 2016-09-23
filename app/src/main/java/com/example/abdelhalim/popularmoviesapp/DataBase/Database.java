package com.example.abdelhalim.popularmoviesapp.DataBase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by abdelhalim on 22/09/16.
 */
public class Database extends RealmObject {
    public String name;
    public String data;

    public Database() {
    }

    public String overView;
    public double rating;

    @PrimaryKey
    public int id;
    public String poster;

    public Database(String name, String data, String overView, double rating, int id, String poster) {
        this.name = name;
        this.data = data;
        this.overView = overView;
        this.rating = rating;
        this.id = id;
        this.poster = poster;
    }
}
