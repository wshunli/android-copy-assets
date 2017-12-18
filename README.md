# android-copy-assets

[![Build Status](https://travis-ci.org/wshunli/android-copy-assets.svg?branch=master)](https://travis-ci.org/)
[![GitHub license](https://img.shields.io/github/license/wshunli/android-copy-assets.svg)](https://github.com/wshunli/android-copy-assets)

Library for copying assets files and folders to the device

## Download


Install android-copy-assets library.

``` groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.wshunli.android:android-copy-assets:1.0.0'
}
```

Check out [android-copy-assets releases](https://github.com/wshunli/android-copy-assets/releases) to see more unstable versions.

## How do I use android-copy-assets?

#### Permission

If you need write file into the EXTERNAL_STORAGE , you should add the permission:

``` XML
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

So if you are targeting Android 6.0+, you need to handle runtime permission request before next step.

#### Simple usage snippet

``` Java
CopyAssets.copy(Context context, String filePath, String desDir)
```

#### More

Find more details about android-copy-assets in [sample](https://github.com/wshunli/android-copy-assets/tree/master/app).

## License

    Copyright 2017 wshunli

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
