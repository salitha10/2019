package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2019mad.R;

public class MainActivity extends AppCompatActivity {

    //resources
    private EditText txtUserName, txtPassword;
    private Button btnLogin, btnRegister;
    private DBHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init resources
        txtUserName = (EditText) findViewById(R.id.editTextUserName);
        txtPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //Database handler
        dbh = new DBHandler(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get entered data
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                //Call login function
                dbh.loginUser(userName, password, getApplicationContext());
            }
        });

        //Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                //Register user
                boolean done = dbh.registerUser(userName, password);
                if(done) {
                    Toast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "User not registered", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}