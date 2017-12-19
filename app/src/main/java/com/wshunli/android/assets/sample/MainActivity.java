/*
 * Copyright 2017 wshunli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wshunli.android.assets.sample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
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
        Log.d(TAG, "innerPath: " + innerPath);
        CopyAssets.with(this)
                .to(innerPath)
                .copy();


        String externalPath = ContextCompat.getExternalFilesDirs(this, null)[0].getAbsolutePath();
        Log.d(TAG, "externalPath: " + externalPath);
        CopyAssets.with(this)
                .from("dir1")
                .to(externalPath)
                .copy();

        String externalDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/com.wshunli.android.assets";
        Log.d(TAG, "externalDirectory: " + externalDirectory);
        CopyAssets.with(this)
                .from("")
                .to(externalDirectory)
                .copy();

    }


}
