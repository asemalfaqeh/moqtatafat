package com.af.moqtatfat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.af.moqtatfat.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(WelcomeScreenActivity.this, MainActivity.class));
            finish();
            overridePendingTransition(0,0);
        },2000);

    }


}
