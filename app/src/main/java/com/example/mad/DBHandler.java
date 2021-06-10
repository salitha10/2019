package com.example.mad;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    //Declare variables
    private static final String DB_NAME = "movieDB";
    private static final int VERSION = 1;

    //Constructor
    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //User table query;
        final String USER_TABLE_QUERY = "CREATE TABLE " + DatabaseMaster.User.TABLE_NAME + " (" +
                "id " + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                DatabaseMaster.User.COLUMN_NAME_USER_NAME + " TEXT, " +
                DatabaseMaster.User.COLUMN_NAME_PASSWORD + " TEXT, " +
                DatabaseMaster.User.COLUMN_NAME_USER_TYPE + " INTEGER);";

        //Movie table query
        final String MOVIE_TABLE_QUERY = "CREATE TABLE " + DatabaseMaster.Movie.TABLE_NAME + "  (" +
                "id " + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseMaster.Movie.COLUMN_NAME_MOVIE_NAME + " TEXT, " +
                DatabaseMaster.Movie.COLUMN_NAME_MOVIE_YEAR + " TEXT); ";

        //Comment table query
        final String COMMENT_TABLE_QUERY = "CREATE TABLE " + DatabaseMaster.Comments.TABLE_NAME + " (" +
                "id " + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseMaster.Comments.COLUMN_NAME_MOVIE_NAME + " TEXT, " +
                DatabaseMaster.Comments.COLUMN_NAME_MOVIE_RATING + " INTEGER, " +
                DatabaseMaster.Comments.COLUMN_NAME_MOVIE_COMMENTS + " TEXT);";

        //Execute query
        db.execSQL(USER_TABLE_QUERY);
        db.execSQL(MOVIE_TABLE_QUERY);
        db.execSQL(COMMENT_TABLE_QUERY);

        Log.d("DB CREATE", "DB Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop table query
        final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + DatabaseMaster.User.TABLE_NAME;
        final String DROP_TABLE_MOVIE = "DROP TABLE IF EXISTS " + DatabaseMaster.Movie.TABLE_NAME;
        final String DROP_TABLE_COMMENTS = "DROP TABLE IF EXISTS " + DatabaseMaster.Comments.TABLE_NAME;

        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_MOVIE);
        db.execSQL(DROP_TABLE_COMMENTS);

        //Create tables again
        onCreate(db);
        db.close();

    }


    public boolean registerUser(String userName, String password) {
        //writable Db
        SQLiteDatabase db = getWritableDatabase();

        //Vales
        ContentValues values = new ContentValues();

        //Set values
        values.put(DatabaseMaster.User.COLUMN_NAME_USER_NAME, userName);
        values.put(DatabaseMaster.User.COLUMN_NAME_PASSWORD, password);

        //values.put(DatabaseMaster.User.COLUMN_NAME_USER_TYPE, userType);

        try {
            //Insert values
            db.insert(DatabaseMaster.User.TABLE_NAME, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Login
    public void loginUser(String userName, String password, Context context) {
        SQLiteDatabase db = getReadableDatabase();
        int count = 0;

        final String QUERY = DatabaseMaster.User.COLUMN_NAME_USER_NAME + " = ? AND " + DatabaseMaster.User.COLUMN_NAME_PASSWORD + " = ?";
        String[] selection = {userName, password};

        //Columns to return
        String[] columns = {"id"};

        if (userName.equals("admin")) {
            //Goto addMovie
            context.startActivity(new Intent(context, AddMovie.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        } else {

            try {
                //Run query and return id of matching record
                Cursor cursor = db.query(DatabaseMaster.User.TABLE_NAME, columns, QUERY, selection, null, null, null, null);
                count = cursor.getCount();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //No matching records
            if (count > 0) {
                //Go to movieList
                context.startActivity(new Intent(context, MovieList.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                Toast.makeText(context, "Login Failed! Account doesn't exist", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Insert movies
    public boolean addMovie(String name, int year){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //Values
        values.put(DatabaseMaster.Movie.COLUMN_NAME_MOVIE_NAME, name);
        values.put(DatabaseMaster.Movie.COLUMN_NAME_MOVIE_YEAR, year);

        //Insert data
        try{
            db.insert(DatabaseMaster.Movie.TABLE_NAME, null, values);
        }catch(SQLException e){
            e.printStackTrace();
            db.close();
            return false;
        }

        db.close();
        return true;
    }

    //Get movies
    public List<String> viewMovies(){

        SQLiteDatabase db = getReadableDatabase();
        //Columns returned
        String[] columns = {DatabaseMaster.Movie.COLUMN_NAME_MOVIE_NAME, DatabaseMaster.Movie.COLUMN_NAME_MOVIE_YEAR};

        List <String> movies = new ArrayList<>();

        //Get data
        Cursor cursor = db.query(DatabaseMaster.Movie.TABLE_NAME, columns, null, null, null,null, null);



        //Loop though data
        while(cursor.moveToNext()){

            //Get row data
            String movieName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Movie.COLUMN_NAME_MOVIE_NAME));

            //Save object in list
            movies.add(movieName);
        }

        cursor.close();
        db.close();

        return movies;
    }


    //Insert comments
    public boolean insertComments(String movie, String comments, int ratings){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //values
        values.put(DatabaseMaster.Comments.COLUMN_NAME_MOVIE_NAME, movie);
        values.put(DatabaseMaster.Comments.COLUMN_NAME_MOVIE_COMMENTS, comments);
        values.put(DatabaseMaster.Comments.COLUMN_NAME_MOVIE_RATING, ratings);

        //Insert data
        try{
            db.insert(DatabaseMaster.Comments.TABLE_NAME, null, values);
        }catch(SQLException e){
            e.printStackTrace();
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public List<Comment> viewComments(){

        SQLiteDatabase db = getReadableDatabase();
        List <Comment> comments = new ArrayList<>();

        String [] columns = {DatabaseMaster.Comments.COLUMN_NAME_MOVIE_NAME, DatabaseMaster.Comments.COLUMN_NAME_MOVIE_COMMENTS, DatabaseMaster.Comments.COLUMN_NAME_MOVIE_RATING};

        Cursor cursor =  db.query(DatabaseMaster.Comments.TABLE_NAME, columns, null, null, null,null,null);

        //Get rows
        while(cursor.moveToNext()){

            String movieName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.COLUMN_NAME_MOVIE_NAME));
            String movieComments = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.COLUMN_NAME_MOVIE_COMMENTS));
            int ratings = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.COLUMN_NAME_MOVIE_RATING));

            //Save data in object
            Comment comment = new Comment(movieName, movieComments, ratings);
            comments.add(comment);
        }

        cursor.close();
        db.close();
        return comments;
    }

}
