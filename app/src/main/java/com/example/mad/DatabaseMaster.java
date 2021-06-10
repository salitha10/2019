package com.example.mad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public final class DatabaseMaster {

    //Default constructor
    DatabaseMaster(){}

    //User class
    public static class User{

        //Variables
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USER_NAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_USER_TYPE = "usertype";
    }


    //Movie class
    public static class Movie{

        //Variables
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_MOVIE_NAME = "name";
        public static final String COLUMN_NAME_MOVIE_YEAR = "year";
    }


    //Comments class
    public static class Comments{

        //Variables
        public static final String TABLE_NAME = "comments";
        public static final String COLUMN_NAME_MOVIE_NAME = "moviename";
        public static final String COLUMN_NAME_MOVIE_RATING = "rating";
        public static final String COLUMN_NAME_MOVIE_COMMENTS = "comments";

    }

}
