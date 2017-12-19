package com.wshunli.assets;

import java.io.File;

/**
 * Created by wshunli on 2017/12/19.
 */

public abstract class CopyListenter {

    public CopyListenter() {
    }

    protected abstract void pending(final CopyAssets copyAssets, final int total);

    protected abstract void progress(final CopyAssets copyAssets, final File file, int progress);

    protected abstract void completed(final CopyAssets copyAssets, final int total);

    protected abstract void error(final CopyAssets copyAssets, Throwable e);

}
