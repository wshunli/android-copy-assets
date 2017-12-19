# android-copy-assets

[![bintray Download](https://api.bintray.com/packages/wshunli/maven/android-copy-assets/images/download.svg)](https://bintray.com/wshunli/maven/android-copy-assets/_latestVersion)
[![Build Status](https://travis-ci.org/wshunli/android-copy-assets.svg?branch=master)](https://travis-ci.org/)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/56d5eb885b924b058b1e0f6c3bc0f8cf)](https://www.codacy.com/app/wshunli/android-copy-assets?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=wshunli/android-copy-assets&amp;utm_campaign=Badge_Grade)
[![Author](https://img.shields.io/badge/Author-wshunli-3388FF.svg)](http://www.wshunli.com)
[![GitHub license](https://img.shields.io/github/license/wshunli/android-copy-assets.svg)](https://github.com/wshunli/android-copy-assets)

Library for copying assets files and folders to the device

## Download

Install android-copy-assets library.

``` groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.wshunli.android:android-copy-assets:2.0.0'
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
CopyAssets.with(this)
        .from("dir1")
        .to(externalPath)
        .copy();
```

For more information about [Data Storage](https://developer.android.com/guide/topics/data/data-storage.html).

#### More

If you want copy all the files and and folders in assets, you can remove the `from` method.

``` Java
CopyAssets.with(this)
        .to(externalPath)
        .copy();
```

or

``` Java
CopyAssets.with(this)
        .from("")
        .to(externalPath)
        .copy();
```

You can also remove the `to` method, while it means copy files and folders to Internal Storage.

``` Java
CopyAssets.with(this)
        .from("")
        .copy();
```

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
