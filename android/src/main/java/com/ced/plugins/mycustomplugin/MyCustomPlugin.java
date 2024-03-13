package com.ced.plugins.mycustomplugin;

import android.util.Log;

public class MyCustomPlugin {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
