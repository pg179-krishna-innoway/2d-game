package com.example.a2dgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.a2dgame.GameView.screenRatioX;
import static com.example.a2dgame.GameView.screenRatioY;

public class Bird {
    public int speed = 15;                               //Speed of the bird
    public boolean wasShot=true;
    int x = 0, y, width, height, birdCounter = 1;
    Bitmap bird1, bird2, bird3, bird4;                    //Bitmap objects

    Bird(Resources res) {

        //decode the bitmaps from drawable
        bird1 = BitmapFactory.decodeResource(res, R.drawable.bird1);
        bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2);
        bird3 = BitmapFactory.decodeResource(res, R.drawable.bird3);
        bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4);

        width = bird1.getWidth();            //get width of the bird
        height = bird1.getHeight();         //get height of the bird


        //reduce the size of the birds
        width /= 8;                         //reduce the width of bird
        height /= 8;                        //reduce the height of the bird



        //Make the size of bird compatible with different screen sizes
        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        //Resize the Bitmaps

        bird1 = Bitmap.createScaledBitmap(bird1, width, height, false);
        bird2 = Bitmap.createScaledBitmap(bird2, width, height, false);
        bird3 = Bitmap.createScaledBitmap(bird3, width, height, false);
        bird4 = Bitmap.createScaledBitmap(bird4, width, height, false);

        y = -height;              //Bird will be placed off the screen in the start


    }

    Bitmap getBird() {                      //get Birds on screen
        if (birdCounter == 1) {
            birdCounter++;
            return bird1;
        }
        if (birdCounter == 2) {
            birdCounter++;
            return bird2;
        }
        if (birdCounter == 3) {
            birdCounter++;
            return bird3;
        }

        birdCounter = 1;               //To restart the animation after the animation ends
        return bird4;
    }

    Rect getCollisionShape ()         //This function will create a rectangle around the bird

    {
        return new Rect(x, y, x+ width, y+height);     //Return the rectangle based on height and width o
    }
}
