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
package com.wshunli.assets;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public final class CopyCreator {

    private final Context mContext;
    private String oriPath, desPath;

    CopyCreator(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 指定起始路径
     *
     * @param oriPath Assets下文件夹路径
     * @return CopyCreator实例
     */
    public CopyCreator from(String oriPath) {
        this.oriPath = oriPath;
        return this;
    }

    /**
     * 指定目的路径
     *
     * @param desPath 任意路径
     * @return CopyCreator实例
     */
    public CopyCreator to(String desPath) {
        this.desPath = desPath;
        return this;
    }

    /**
     * 复制操作
     *
     * @return {@code true}: 复制成功<br>{@code false}: 复制失败
     */
    public boolean copy() {
        return this.copy(mContext, oriPath, desPath);
    }

    /**
     * 复制Assets下文件及文件夹到设备指定路径
     *
     * @param context  上下文
     * @param filePath Assets下级目录，全部写""
     * @param desDir   目标文件夹,默认内部存储空间
     * @return {@code true}: 复制成功<br>{@code false}: 复制失败
     */
    public boolean copy(Context context, String filePath, String desDir) {

        filePath = filePath == null ? "" : filePath;
        desDir = desDir == null ? context.getFilesDir().getAbsolutePath() : desDir;

        ArrayList<String> allAssetsFilePath = getAssetsFilePath(context, filePath, null);

        for (String path : allAssetsFilePath) {

            File desFile = getFileByPath(desDir + "/" + path);
            if (desFile == null) return false;
            try {
                InputStream is = context.getAssets().open(path);
                boolean result = writeFileFromIS(desFile, is);
                if (!result) return false;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;

    }

    /**
     * 获得Assets下所有文件列
     *
     * @param context 上下文
     * @param oriPath Assets下级目录，全部写""
     * @param paths   用于迭代的文件列表，初始值为n
     * @return 返回文件列表
     */
    private ArrayList<String> getAssetsFilePath(Context context, String oriPath, ArrayList<String> paths) {

        if (paths == null) paths = new ArrayList<>();

        try {
            String[] list = context.getAssets().list(oriPath);
            for (String l : list) {
                int length = context.getAssets().list(l).length;
                String desPath = oriPath.equals("") ? l : oriPath + "/" + l;
                if (length == 0) {
                    paths.add(desPath);
                } else {
                    getAssetsFilePath(context, desPath, paths);
                }
            }
            return paths;
        } catch (IOException e) {
            e.printStackTrace();
            return paths;
        }

    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    private File getFileByPath(String filePath) {
        return (filePath == null || filePath.trim().length() == 0) ? null : new File(filePath);
    }

    /**
     * 将输入流写入文件
     *
     * @param file 文件
     * @param is   输入流
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    private boolean writeFileFromIS(File file, InputStream is) {
        if (file == null || is == null) return false;
        if (!this.createOrExistsFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[1024];
            int len;
            while ((len = is.read(data, 0, 1024)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            this.closeIO(is, os);
        }
    }


    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!this.createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    private void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
