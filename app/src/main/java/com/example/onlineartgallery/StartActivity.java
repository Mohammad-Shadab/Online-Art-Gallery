package com.example.onlineartgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.onlineartgallery.ui.login.loginArtist;


public class StartActivity extends AppCompatActivity {

    public void login(View view)

    {
        Intent intent = new Intent(this, loginArtist.class);

        startActivity(intent);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
}