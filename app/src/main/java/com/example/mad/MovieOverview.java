package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2019mad.R;

import java.util.ArrayList;
import java.util.List;

public class MovieOverview extends AppCompatActivity {

    private SeekBar ratingBar;
    private EditText comments;
    private TextView title, ratingView;
    private ListView commentListView;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);

        ratingBar = (SeekBar) findViewById(R.id.seekBarRating);
        comments = (EditText) findViewById(R.id.editTextComments);
        title = (TextView) findViewById(R.id.txtMovieName);
        submit = (Button) findViewById(R.id.btnSubmit);
        ratingView = (TextView) findViewById(R.id.txtCurrentRating);
        commentListView = (ListView) findViewById(R.id.ListViewComments);

    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHandler dbh = new DBHandler(getApplicationContext());

        //Get movie name from intent
        Intent intent = getIntent();
        String movieName = intent.getStringExtra("movie");

        //set title
        title.setText(movieName);

        //Get values from user

        List<Comment> commentList  = dbh.viewComments();
        List<String> movieComments = new ArrayList<>();

        float ratings = 0;
        int count = 0, rat = 0;

        //Loop through objects to get move ratings
        for(Comment commentItem : commentList){
            if(commentItem.getMovieName().equals(movieName)){
                rat += commentItem.getRatings();
                count++;
                movieComments.add(commentItem.getComments());
            }
        }

        try {
            ratings = (float)(rat / count);
        }
        catch (ArithmeticException e){
            ratings = 0;
            e.printStackTrace();
        }

        ratingView.setText(String.valueOf(ratings));

        /**
         *  TO Change textview vale based on seekbar change
         *
        ratingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ratingView.setText(String.valueOf(ratingBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        */


        //Add comments to list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getApplication(), android.R.layout.simple_list_item_1, movieComments
        );
        commentListView.setAdapter(arrayAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = (int) ratingBar.getProgress();
                String comment = comments.getText().toString();

                boolean done = dbh.insertComments(movieName, comment, rating);

                if(done){
                    Toast.makeText(getApplicationContext(), "Comment Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Comment not added", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
}