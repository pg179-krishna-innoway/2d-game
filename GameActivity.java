// Emtpy Activity




package com.example.a2dgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //To make this activity full screen
        Point point = new Point();                                  //Point - represents a location in a two-dimensional (x, y) coordinate space
        getWindowManager().getDefaultDisplay().getSize(point);     //To get size of the screen on x and y axis
        gameView = new GameView(this,point.x, point.y);     //sending size of the screen on x and y axis

        setContentView(gameView);          //set screen of GameActivity (gameView) to surfaceView
    }

    @Override
    protected void onPause() {      //onPause() - when the activity is visible but another activity has the focus
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {    //called immediately before the activity is about to start interacting with the user.
            super.onResume();
        gameView.resume();
    }
}