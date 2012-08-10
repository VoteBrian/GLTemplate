package net.votebrian.demo.gltemplate;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

class GLESRenderer implements GLSurfaceView.Renderer {
    Global gbl;

    private int mViewW = 0;
    private int mViewH = 0;
    private float mViewAngle = 20f;

    private float mNearH = 0f;
    private float mNearW = 0f;
    private float mNearZ = 0.01f;
    private float mFarZ  = 20f;

    public GLESRenderer(Context context) {
        gbl = (Global) context.getApplicationContext();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        setDisplayProperties(gl);
    }

    public void onDrawFrame(GL10 gl) {
        /*
         *  Set the background color
         */
        if(gbl.getSomething() == 0) {
            // blue
            gl.glClearColor(0.8f, 0.8f, 1.0f, 1.0f);
        } else if(gbl.getSomething() == 1) {
            // red
            gl.glClearColor(1.0f, 0.8f, 0.8f, 1.0f);
        } else if(gbl.getSomething() == 2) {
            // green
            gl.glClearColor(0.8f, 1.0f, 0.8f, 1.0f);
        }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glLoadIdentity();

        gbl.draw(gl);

    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mViewW = width;
        mViewH = height;

        setDisplayProperties(gl);
        setProjection(gl);
    }

    private void setProjection(GL10 gl) {
        float ratio = (float) mViewW / (float) mViewH;

        // determine the "half-width" and "half-height" of our view at the near cutoff Z value stuff
        // stuff stuff
        mNearH = (float) (mNearZ * (Math.tan(Math.toRadians(mViewAngle))));
        mNearW = mNearH * ratio;

        // Define orthographic projection
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glFrustumf(-mNearW, mNearW, -mNearH, mNearH, mNearZ, mFarZ);
        gl.glViewport(0, 0, mViewW, mViewH);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    private void setDisplayProperties(GL10 gl) {
        // set a black background color and clear view
        gl.glClearColor(0f, 0f, 0f, 1f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // gl.glMatrixMode(GL10.GL_MODELVIEW);

        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glDisable(GL10.GL_COLOR_MATERIAL);
    }
}