package com.dragonfly.vanta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dragonfly.vanta.Views.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}