package com.example.tonytetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }//end onCreate

    public void onPlayTetrisClick(View view){
        Intent intent = new Intent(this, GetNameActivity.class);
        startActivity(intent);
    }//end onPLayTetrisClick

    public void onSettingsClick(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }//end onSettingsClick
}