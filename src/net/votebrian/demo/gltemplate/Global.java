package net.votebrian.demo.gltemplate;

import android.app.Application;

public class Global extends Application {
    private static int something = 0;

    public int getSomething() {
        return something;
    }

    public void setSomething(int num) {
        something = num;
    }
}