package com.wshunli.android.assets.sample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wshunli.assets.CopyAssets;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String innerPath = getFilesDir().getAbsolutePath();
        Log.d(TAG, "innerPath: "+innerPath);
        CopyAssets.copy(MainActivity.this, "", innerPath);

        String externalPath = getExternalFilesDir(null).getAbsolutePath();
        Log.d(TAG, "externalPath: "+externalPath);
        CopyAssets.copy(MainActivity.this, "", innerPath);

        String externalDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/com.wshunli.android.assets";
        Log.d(TAG, "externalDirectory: " + externalDirectory);
        CopyAssets.copy(MainActivity.this, "", externalDirectory);

    }


}
