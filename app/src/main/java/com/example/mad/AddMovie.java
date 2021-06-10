package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2019mad.R;

public class AddMovie extends AppCompatActivity {

    private EditText mName, mYear;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        mName = (EditText) findViewById(R.id.editTextMovieName);
        mYear = (EditText) findViewById(R.id.editTextMovieYear);
        add = (Button) findViewById(R.id.btnAdd);

        DBHandler dbh = new DBHandler(getApplicationContext());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = mName.getText().toString();
                int year = Integer.parseInt(mYear.getText().toString());
                boolean done = dbh.addMovie(movieName, year);
                if(done) {
                    Toast.makeText(getApplicationContext(), "Movie added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Movie not added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}