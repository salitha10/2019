package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a2019mad.R;

import java.util.List;

public class MovieList extends AppCompatActivity {

    private ListView movieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        DBHandler dbh = new DBHandler(getApplicationContext());
        movieList = (ListView) findViewById(R.id.movieList);
        List <String> listOfMovies = dbh.viewMovies();

        //Toast.makeText(getApplicationContext(), listOfMovies.get(0), Toast.LENGTH_SHORT).show();

        //Display list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, listOfMovies
        );
        movieList.setAdapter(arrayAdapter);

        //Set item onclick listener
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MovieList.this, MovieOverview.class);
                intent.putExtra("movie", listOfMovies.get(position));
                startActivity(intent);
            }
        });
    }
}