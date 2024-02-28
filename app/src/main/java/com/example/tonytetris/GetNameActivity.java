package com.example.tonytetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GetNameActivity extends AppCompatActivity {

    private EditText getName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        getName = findViewById(R.id.EditTextGetName);
    }//end onCreate

    public void onStartGame(View view){
        String name = getName.getText().toString();
        if(name.length() == 3){
            Intent intent = new Intent(this, PlayTetrisActivity.class);
            intent.putExtra("USER_NAME", name);
            startActivity(intent);
        }else {
            Toast.makeText(this,"3 Char Name!", Toast.LENGTH_SHORT).show();
        }//end if else
    }//end onStartGame
}//end GetNameActivity