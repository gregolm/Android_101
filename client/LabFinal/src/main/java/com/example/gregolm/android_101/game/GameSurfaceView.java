package com.example.gregolm.android_101.game;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.gregolm.android_101.dto.Map;
import com.example.gregolm.android_101.game.actor.Hero;
import com.example.gregolm.android_101.utilities.BitmapAssetFactory;

import java.io.FileDescriptor;
import java.io.IOException;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    protected GameThread gameThread;
    protected Hero hero;
    protected MediaPlayer mediaPlayer = new MediaPlayer();
    protected GestureDetector mGesture;
    public GameSurfaceView(Context context, Map map) {
        super(context);
        setClickable(true); // SurfaceView doesn't dispatch ACTION_UP unless it's clickable...
        getHolder().addCallback(this);
        // make the GamePanel focusable so it can handle events
        setFocusable(true);
        hero = new Hero(BitmapAssetFactory.getAssetBitmap(context,"RedHeadBoySpriteSheet-2x.png"),
                map.getStartX(),
                map.getStartY(),
                64,64,6,
                5);
        //hero.setSpeed(new Speed(5,0.25f));
        gameThread = new GameThread(getHolder(), this);
        try {
            AssetFileDescriptor afd = context.getAssets().openFd("Grasslands Theme_0.mp3");
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IOException ex) {
            Log.d("GameSurfaceView", ex.toString());
        }
        mGesture = new GestureDetector(context, onGesture);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
        mediaPlayer.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException ex) {
                // cough, cough, empty catch...
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getY() > getHeight() - 100) {
                gameThread.setRunning(false);
                ((Activity) getContext()).finish();
            } else if ((event.getX() > getWidth() - 100) &&
                      (event.getY() < 100)) {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                else
                    mediaPlayer.start();
            } else {
                Log.d("GameSurfaceView", "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        }
        mGesture.onTouchEvent(event);
        return true;
    }

    private GestureDetector.SimpleOnGestureListener onGesture = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("GameSurfaceView","velocityX = " + velocityX + " & velocityY = " + velocityY);
            float vX=velocityX/1500f, vY=velocityY/1500f;
            if (vX > 8.0f) vX = 8.0f;
            if (vY > 8.0f) vY = 8.0f;
            hero.setSpeed(new Speed(vX,vY));
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    public void update() {
        // check collision with right wall if heading right
        if (hero.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                && hero.getX() + hero.getSpriteWidth() / 2 >= getWidth()) {
            hero.getSpeed().toggleXDirection();
        }
        // check collision with left wall if heading left
        if (hero.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                && hero.getX() - hero.getSpriteWidth() / 2 <= 0) {
            hero.getSpeed().toggleXDirection();
        }
        // check collision with bottom wall if heading down
        if (hero.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                && hero.getY() + hero.getSpriteHeight() / 2 >= getHeight()) {
            hero.getSpeed().toggleYDirection();
        }
        // check collision with top wall if heading up
        if (hero.getSpeed().getyDirection() == Speed.DIRECTION_UP
                && hero.getY() - hero.getSpriteHeight() / 2 <= 0) {
            hero.getSpeed().toggleYDirection();
        }
        hero.update(System.currentTimeMillis());
    }

    public void render(Canvas canvas) {
        AssetManager assetManager = getContext().getAssets();
        try {
            canvas.drawBitmap(BitmapFactory.decodeStream(assetManager.open("layer-1.png")),0f,0f,null);
            hero.draw(canvas);
        } catch (Exception e) {}
    }

}