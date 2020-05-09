package com.example.pratik.theflyingbird;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;



public class GameView extends View {


    // Canvas
    private int canvasWidth;
    private int canvasHeight;

    // Bird
    //private Bitmap bird;
    private Bitmap bird[] = new Bitmap[2];
    private int birdX = 10;
    private int birdY;
    private int birdSpeed;

    // Crow
    private Bitmap crow;
    private int crowX;
    private int crowY;
    private int crowSpeed = 25;

    // Blue Ball
    private int blueX;
    private int blueY;
    private int blueSpeed = 15;
    private Paint bluePaint = new Paint();

    // Black Ball
    private int blackX;
    private int blackY;
    private int blackSpeed = 20;
    private Paint blackPaint = new Paint();

    // Red Ball
    private int redX;
    private int redY;
    private int redSpeed = 25;
    private Paint redPaint = new Paint();

    // Green Ball
    private int greenX;
    private int greenY;
    private int greenSpeed = 30;
    private Paint greenPaint = new Paint();

    // Background
    private Bitmap bgImage;
    private Bitmap startImage;
    private Bitmap startButton;

    // Score
    private Paint scorePaint = new Paint();
    private int score;

    // Level
    private Paint levelPaint = new Paint();
    private int level;

    // Life
    private Bitmap life[] = new Bitmap[2];
    private int life_count;

    // Status Check
    private boolean touch_flg = false;
    private Object button;

    public GameView(Context context) {
        super(context);

        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);

        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        startImage= BitmapFactory.decodeResource(getResources(),R.drawable.start);
        startButton= BitmapFactory.decodeResource(getResources(),R.drawable.start_btn);

        crow = BitmapFactory.decodeResource(getResources(), R.drawable.flycrow);

        bluePaint.setColor(Color.BLUE);
        bluePaint.setAntiAlias(false);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        levelPaint.setColor(Color.DKGRAY);
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);

        // First position.
        birdY = 500;
        score = 0;
        life_count = 3;
        level = 1;


    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        canvas.drawBitmap(startImage, 0, 0, null);
        canvas.drawBitmap(startButton, 50, 50, null);

            canvas.drawBitmap(bgImage, 0, 0, null);

            // Bird
            int minBirdY = bird[0].getHeight();
            int maxBirdY = canvasHeight - bird[0].getHeight() * 3;

            birdY += birdSpeed;
            if (birdY < minBirdY) birdY = minBirdY;
            if (birdY > maxBirdY) birdY = maxBirdY;
            birdSpeed += 2;

            if (touch_flg) {
                // Flap wings.
                canvas.drawBitmap(bird[1], birdX, birdY, null);
                touch_flg = false;
            } else {
                canvas.drawBitmap(bird[0], birdX, birdY, null);
            }


            // Blue
            blueX -= blueSpeed;
            if (hitCheck(blueX, blueY)) {
                score += 10;
                blueX = -100;
            }
            if (blueX < 0) {
                blueX = canvasWidth + 20;
                blueY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
            }
            canvas.drawCircle(blueX, blueY, 10, bluePaint);

            if(score<100) {
                // Black
                blackX -= blackSpeed;
                if (hitCheck(blackX, blackY)) {
                    blackX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (blackX < 0) {
                    blackX = canvasWidth + 200;
                    blackY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
                }
                canvas.drawCircle(blackX, blackY, 20, blackPaint);
            }
            else if(score<200)
            {
                level = 2;
                // red
                redX -= redSpeed;
                if (hitCheck(redX, redY)) {
                    redX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (redX < 0) {
                    redX = canvasWidth + 200;
                    redY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
                }
                canvas.drawCircle(redX, redY, 20, redPaint);
            }
            else if(score<300)
            {
                level = 3;
                // red & black
                redX -= redSpeed;
                if (hitCheck(redX, redY)) {
                    redX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (redX < 0) {
                    redX = canvasWidth + 200;
                    redY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
                }
                canvas.drawCircle(redX, redY, 20, redPaint);

                blackX -= blackSpeed;
                if (hitCheck(blackX, blackY)) {
                    blackX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (blackX < 0) {
                    blackX = canvasWidth + 200;
                    blackY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY + 30;
                }
                canvas.drawCircle(blackX, blackY, 20, blackPaint);
            }
            else if(score<400)
            {
                level = 4;
                // red & black & green
                redX -= redSpeed;
                if (hitCheck(redX, redY)) {
                    redX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (redX < 0) {
                    redX = canvasWidth + 200;
                    redY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
                }
                canvas.drawCircle(redX, redY, 20, redPaint);

                blackX -= blackSpeed;
                if (hitCheck(blackX, blackY)) {
                    blackX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (blackX < 0) {
                    blackX = canvasWidth + 200;
                    blackY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY + 30;
                }
                canvas.drawCircle(blackX, blackY, 20, blackPaint);

                greenX -= greenSpeed;
                if (hitCheck(greenX, greenY)) {
                    greenX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (greenX < 0) {
                    greenX = canvasWidth + 200;
                    greenY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY + 20;
                }
                canvas.drawCircle(greenX, greenY, 20, greenPaint);
            }
            else
            {
                level = 5;
                // red & black & green & crow
                redX -= redSpeed;
                if (hitCheck(redX, redY)) {
                    redX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (redX < 0) {
                    redX = canvasWidth + 200;
                    redY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
                }
                canvas.drawCircle(redX, redY, 20, redPaint);

                blackX -= blackSpeed;
                if (hitCheck(blackX, blackY)) {
                    blackX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (blackX < 0) {
                    blackX = canvasWidth + 200;
                    blackY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY + 30;
                }
                canvas.drawCircle(blackX, blackY, 20, blackPaint);

                greenX -= greenSpeed;
                if (hitCheck(greenX, greenY)) {
                    greenX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (greenX < 0) {
                    greenX = canvasWidth + 200;
                    greenY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY + 20;
                }
                canvas.drawCircle(greenX, greenY, 20, greenPaint);

                crowX -= crowSpeed;
                if (hitCheck(crowX, crowY)) {
                    crowX = -100;
                    life_count--;
                    if (life_count == 0) {
                        gameOver();
                        // Log.v("Message", "Game Over");
                    }
                }
                if (crowX < 0) {
                    crowX = canvasWidth + 200;
                   // crowY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY + 20;
                    crowY = birdY;
                }
                canvas.drawBitmap(crow, crowX ,crowY ,null);
            }



            // Score
            canvas.drawText("Score : " + score, 20, 60, scorePaint);

            // Level
            canvas.drawText("Lv. " + level, (canvasWidth / 2)-20, 60, levelPaint);

            // Life
            for (int i = 0; i < 3; i++) {
                int x = (int) (600 + life[0].getWidth() * 1.5 * i);
                int y = 30;

                if (i < life_count) {
                    canvas.drawBitmap(life[0], x, y, null);
                } else {
                    canvas.drawBitmap(life[1], x, y, null);
                }
            }
        }

    private void gameOver() {

        Intent intent = new Intent(getContext(), SecondActivity.class);

        intent.putExtra("EXTRA_MESSAGE", score);
        getContext().startActivity(intent);


      //  intent.putExtra("EXTRA_MESSAGE", score);



    }


    public boolean hitCheck ( int x, int y){
            if (birdX < x && x < (birdX + bird[0].getWidth()) &&
                    birdY < y && y < (birdY + bird[0].getHeight())) {
                return true;
            }
            return false;
        }

        @Override
        public boolean onTouchEvent (MotionEvent event){
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touch_flg = true;
                birdSpeed = -20;
            }
            return true;
        }






}
