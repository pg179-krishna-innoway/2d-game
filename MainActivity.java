package com.example.a2dgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);   //to make the activity full screen
        setContentView(R.layout.activity_main);       //render layout on the screen

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {   //find play textView by its id (onClickListener())
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));   //start Activity i.e, from mainactivity to gameActivity
            }
        });
    }
}