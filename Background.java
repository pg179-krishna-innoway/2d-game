package com.example.a2dgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int x=0,y=0;
    Bitmap background;
    Background(int screenX, int screenY, Resources res){                                     //Resources res - used to decode the bitmap from drawable folder
        background = BitmapFactory.decodeResource(res, R.drawable.background);               //use this method in order to decode a Bitmap which is stored inside the res folder
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);   //resize -- covers the whole screen that is screenX and screenY
    }
}
