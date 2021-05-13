package com.example.a2dgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.a2dgame.GameView.screenRatioX;
import static com.example.a2dgame.GameView.screenRatioY;

public class Flight {

    public boolean isGoingUp=false;
    public int toShoot=0;
    int x,y,width, height,wingCounter=0,shootCounter=1;
    Bitmap flight1, flight2,shoot1,shoot2, shoot3, shoot4, shoot5, dead;     //Objects of bitmap class
    private GameView gameView;

    Flight (GameView gameView, int screenY, Resources res){                    //accepts object of gameView, screen size on Y axis and resources object

        this.gameView = gameView;                                             //refer the gameView object of flight const to gamView
        flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1);         //use this method in order to decode a Bitmap which is stored inside the res folder
        flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2);         //use this method in order to decode a Bitmap which is stored inside the res folder

        width = flight1.getWidth();            //width of bitmap
        height = flight1.getHeight();          //height of bitmap

        //To reduce the size of image flight1
        width/=6;
        height/=6;
        width = (int) (width * screenRatioX);      //To make the image's width compatible on other devices
        height = (int) (height * screenRatioY);    //To make the image's height compatible on other devices

        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);   //Resize the flight1 Bitmap
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);   //Resize the flight2 Bitmap

        ////create the bitmaps from drawable folder for shoot1,shoot2,shoot3,shoot4,shoot5
        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);

        //Resize the Bitmaps
        shoot1 = Bitmap.createScaledBitmap(shoot1 , width, height, false);
        shoot2 = Bitmap.createScaledBitmap(shoot2 , width, height, false);
        shoot3 = Bitmap.createScaledBitmap(shoot3 , width, height, false);
        shoot4 = Bitmap.createScaledBitmap(shoot4 , width, height, false);
        shoot5 = Bitmap.createScaledBitmap(shoot5 , width, height, false);

        dead = BitmapFactory.decodeResource(res, R.drawable.dead);                    //Refer the bitmap to the image in drawable folder
        dead = Bitmap.createScaledBitmap(dead, width, height, false);           //Resize the bitmap
        y = screenY / 2;                                                     //Place the flight in the center of the screen vertically
        x = (int) (64 * screenRatioX);                                       //Place the flight horizontally at 64 pixels

    }

    Bitmap getFlight(){       //this function will be called when we will be drawing our flight on the canvas

        if(toShoot != 0){                         //we have some bullets to shoot
            if(shootCounter == 1){
                shootCounter++;
                return shoot1;
            }
            if(shootCounter == 2){
                shootCounter++;
                return shoot2;
            }
            if(shootCounter == 3){
                shootCounter++;
                return shoot3;
            }
            if(shootCounter == 4){
                shootCounter++;
                return shoot4;
            }

            shootCounter = 1;             //To repeat the animation set shootCounter to 1 again
            toShoot--;
            gameView.newBullet();

            return shoot5;
        }
        if(wingCounter == 0){
            wingCounter++;
            return flight1;    //Fan is off
        }
        wingCounter--;
        return flight2;      //Fan is on

    }

    Rect getCollisionShape ()       //Create rectangle around the flight
    {
        return new Rect(x, y, x+ width, y+height);
    }

    Bitmap getDead() {
        return dead;
    }                  //Return the dead bitmap
}
