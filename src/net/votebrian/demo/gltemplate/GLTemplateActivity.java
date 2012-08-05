package net.votebrian.demo.gltemplate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GLTemplateActivity extends Activity
{
    Global gbl;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // if this works, the app should start with a red-ish SurfaceView background
        gbl = (Global) getApplication();
        gbl.setSomething(1);  // blue = 0, red = 1, green = 2 in GLESRenderer.

    }
}
