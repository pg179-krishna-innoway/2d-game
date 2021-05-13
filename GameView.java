package com.example.a2dgame;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying,isGameOver = false;
    private int screenX, screenY;                //size of screen on x and y axis
    public static float screenRatioX, screenRatioY;

    private Paint paint;   //used to style a shape drawn by a canvas
    private Bird[] birds;  //Array of Bird class
    private Random random;   //Object of Random class

    private List<Bullet> bullets;   //Create a list
    private Flight flight;
    private Background background1, background2;
    public GameView(Context context, int screenX, int screenY)
    {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX=1920f / screenX;       //
        screenRatioY=1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());    //instantiate objects of background class , getResources()- return the resources of the module in which this class exists
        background2 = new Background(screenX, screenY, getResources());

        flight = new Flight(this, screenY , getResources());    //initialize flight object  (this for gameView Object as parameter

        bullets = new ArrayList<>();                                      //initialize the list

        background2.x = screenX;   //modify x value of background2 , it will be placed just after the screen ends on the x axis

        paint = new Paint();     //initialize paint object
        birds = new Bird[3];     //initialize bird object
        for(int i=0;i<3;i++){
            Bird bird = new Bird(getResources());      //getResources() returns the resources of the module in which class exists
            birds[i] = bird;                           //Add the bird to birds array
        }

        random = new Random();                         //Initialize object of random class
    }

    @Override        //Callbacks  (will be called by their own)
    public void run() {
        while (isPlaying){

            update();
            draw();
            sleep();
        }

    }
    private void update(){

        background1.x -=10*screenRatioX;    //change the position of background on x axis by 10 pixels
        background2.x -=10*screenRatioY;

        if(background1.x + background1.background.getWidth()<0){        //when background is completely off the screen
            background1.x = screenX;
        }

        if(background2.x + background2.background.getWidth()<0){
            background2.x = screenX;
        }
        if(flight.isGoingUp)
            flight.y -=30 * screenRatioY;                            //Reduce Y VALUE of the flight by 30 i.e,  placing our flight towards top of the screen
        else
            flight.y +=30*screenRatioY;                              //Pulling the flight down

        if(flight.y<0)                                              //if flight is going of the screen
            flight.y=0;

        if(flight.y > screenY - flight.height)                     //if flight is off the screen from the bottom
            flight.y=screenY - flight.height;

        List<Bullet> trash = new ArrayList<>();                   //create one more list of bullet class

        for(Bullet bullet : bullets) {           //for each loop for the bullets
            if (bullet.x > screenX)              //bullet is off the screen
                trash.add(bullet);               //add the bullet which is off the screen to the trash list
            bullet.x += 50 * screenRatioX;       //move this bullet by 50 pixels on the x axis


            for (Bird bird : birds) {            //Run through each bird in the birds array
                if (Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())) {             //if the bird and the bullet collides

                    bird.x = -500;                                                                     //To make the bird off the screen
                    bullet.x = screenX + 500;                                                   //To make the bullet of the screen and bullet will be added to trash when update will be called next time
                    bird.wasShot = true;                                                              //When the bullet shoots the bird wasShot made true
                }
            }
        }

        for(Bullet bullet : trash)                     //for each loop for bullets in the trash list
            bullets.remove(bullet);                    //remove the bullet from the trash list

        for(Bird bird : birds){
            bird.x -= bird.speed;                      //Move the birds towards our flight
            if(bird.x + bird.width < 0){               //Bird is off the screen

                if(!bird.wasShot){                    //If the Bird was not shot
                    isGameOver = true;               //Game over
                    return;
                }

                int bound = (int) (20 * screenRatioX);           //speed limit set to 30 pixels
                bird.speed = random.nextInt(bound);              //Give the random speed to our bird

                if(bird.speed < 10 * screenRatioX)              //If speed of the bird is less than 10 pixels
                    bird.speed = (int) (10 * screenRatioX);     //Set the speed to 10 pixels i.e, our minimum speed

                bird.x = screenX;                               //Placing the bird towards the end of the screen on x axis
                bird.y = random.nextInt(screenY - bird.height);                //Position of bird will be random on y axis (If Random return screenY then it will be off the screen so we bound it

                bird.wasShot = false;
            }

            if(Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())){           //If the two rectangles (bird and flight) will intersect/collide then the game will be over
                isGameOver = true;                                                              //GameOver
                return;
            }
        }



    }
    private void draw(){

        if(getHolder().getSurface().isValid()) {            //our surfaceview object has been successfully initiated
            Canvas canvas = getHolder().lockCanvas();       //lockCanvas - it will return the current canvas that is being displayed on the screen
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);          //draw background1 on canvas
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);          //draw background2 on canvas
            for(Bird bird : birds)                                                                   //for each loop to create birds on the canvas
                canvas.drawBitmap(bird.getBird() , bird.x, bird.y, paint);
            if(isGameOver){                                                                           //If the isGameOver is true
                isPlaying=false;
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);                       //Create the Dead Bitmap when the game is over
                getHolder().unlockCanvasAndPost(canvas);                                              //Draw the canvas on the screen
                return;
            }


            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);                        //Draw the flight Bitmap

            for(Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);                         //Draw the bullets on the screen


            getHolder().unlockCanvasAndPost(canvas);                                                //To show the canvas on the screen

        }

    }
    private void sleep(){
        try {
            Thread.sleep(17);            //If we divide 1000 millis(1sec) by 17millis then we get 60 ie, in 1 second we are updating the positions and drawing it on our screen 60 time -> 60 fps
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume(){                              //To resume or start the game
        isPlaying = true;                             //isPlaying is made to true when the game resumes
        thread = new Thread(this);  //new thread created
        thread.start();                    //starting this thread wil call the run() function
    }
    public void pause(){                              //To Pause the game
        try {
            isPlaying = false;                //isPlaying is made false when the game pauses
            thread.join();                     //Terminating the thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override                                                 //callback - called when user touches
    public boolean onTouchEvent(MotionEvent event) {         //onTouchEvent- method from surfaceView   ,    function executed when the user touches the screen
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:                    //Action down when screen not touched
            if(event.getX() < screenX / 2.0){                //If the user taps on the left side of the screen
                flight.isGoingUp = true;
            }
            break;
            case MotionEvent.ACTION_UP:  //motionevent class captures touch
                flight.isGoingUp=false;
                if(event.getX() > screenX / 2.0)        //if TouchEvent coming from right side of the screen
                flight.toShoot++;
            break;
        }
        return true;
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());         //Object of bullet class
        bullet.x = flight.x + flight.width;                 //initially the bullet will be placed near the wings of the flight(x value of bullet)
        bullet.y = flight.y + (flight.height/2);            //y value of bullet

        bullets.add(bullet);                                //Add the bullet to the bullets' list
    }
}
