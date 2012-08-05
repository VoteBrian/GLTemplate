package net.votebrian.demo.gltemplate;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;

class GLESRenderer implements GLSurfaceView.Renderer {
    Global gbl;

    public GLESRenderer(Context context) {
        gbl = (Global) context.getApplicationContext();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //
    }

    public void onDrawFrame(GL10 gl) {
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
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //
    }
}