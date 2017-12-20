package com.wshunli.assets;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface CopyListener {

    void pending(final CopyCreator copyCreator, final String oriPath, final String desPath, List<String> names);

    void progress(final CopyCreator copyCreator, final File currentFile, int copyProgress);

    void completed(final CopyCreator copyCreator, final Map<File, Boolean> results);

    void error(final CopyCreator copyCreator, Throwable e);

}
