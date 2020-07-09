package com.samashish.notetrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int SPLASH_TIME_OUT = 1750;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this, Main.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}