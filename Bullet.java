package com.example.a2dgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.a2dgame.GameView.screenRatioX;
import static com.example.a2dgame.GameView.screenRatioY;

public class Bullet {
    int x,y,width,height;
    Bitmap bullet;

    Bullet(Resources res){                                                      //Constructor
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
         width = bullet.getWidth();
         height = bullet.getHeight();
        //Reduce the bullet size
        width/= 4;
        height/= 4;

        width = (int) (width * screenRatioX);              //Make compatible with different screen sizes
        height = (int) (height * screenRatioY);
        bullet = Bitmap.createScaledBitmap(bullet , width, height, false);           //Resize the bullet bitmap


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x+ width, y+height);
    }
}
