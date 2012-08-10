package net.votebrian.demo.gltemplate;

import android.util.AttributeSet;
import android.content.Context;
import android.view.MotionEvent;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;

public class SurfaceView extends GLSurfaceView {

    Global gbl;

    public SurfaceView(Context context, AttributeSet atr) {
        super(context, atr);

        // setEGLContextClientVersion(2);
        setRenderer(new GLESRenderer(context));

        gbl = new Global();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action) {
            case MotionEvent.ACTION_UP:
                int curr = gbl.getSomething();

                if(curr == 0) {
                    gbl.setSomething(1);
                } else if(curr == 1) {
                    gbl.setSomething(2);
                } else if(curr == 2) {
                    gbl.setSomething(0);
                }
                break;
        }

        return true;
    }
}