package com.example.gregolm.android_101.utilities.openGLes;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


public class GameGLSurfaceView extends GLSurfaceView {
    public GameGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setRenderer(new GameGLRenderer());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
