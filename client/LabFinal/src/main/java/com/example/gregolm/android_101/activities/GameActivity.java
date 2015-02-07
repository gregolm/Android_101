package com.example.gregolm.android_101.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

import com.example.gregolm.android_101.dto.Map;
import com.example.gregolm.android_101.game.GameSurfaceView;
import com.example.gregolm.android_101.utilities.MapLibrary;
import com.example.gregolm.android_101.utilities.openGLes.GameGLSurfaceView;

import java.io.IOException;


public class GameActivity extends Activity {

    //protected GLSurfaceView mGLView;
    protected GameSurfaceView mGSView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*  We would need this if we weren't setting the theme in the manifest
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */
        //mGLView = new GameGLSurfaceView(this);
        //setContentView(mGLView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        String mapName = getIntent().getStringExtra("mapToPlay");
        setContentView(new GameSurfaceView(this, MapLibrary.getInstance().getMap(mapName)));

    }

    @Override
    protected void onResume() {

        super.onResume();
        //mGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mGLView.onPause();
    }
}
