package com.wshunli.assets;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by wshunli on 2017/12/17.
 */

public class CopyAssets {


    public static boolean copy(Context context, String filePath, String desDir) {


        filePath = filePath == null ? "" : filePath;
        desDir = desDir == null ? context.getFilesDir().getAbsolutePath() : desDir;

        ArrayList<String> allAssetsFilePath = getAssetsFilePath(context, filePath, null);

        for (String path : allAssetsFilePath) {

            File desFile = getFileByPath(desDir + "/" + path);
            if (desFile == null) return false;
            try {
                InputStream is = context.getAssets().open(path);
                writeFileFromIS(desFile, is);
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


    private static ArrayList<String> getAssetsFilePath(Context context, String oriPath, ArrayList<String> paths) {

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
     * 复制文件到指定路径
     *
     * @param file 文件
     * @param desPath 文件路径
     * @return {@code true}: 复制成功<br>{@code false}: 复制失败
     */
    private static boolean copyFile2Path(File file, String desPath) {

        File desFile = getFileByPath(desPath);
        if (file == null || desFile == null) return false;

        try {
            InputStream is = new FileInputStream(file);
            return CopyAssets.writeFileFromIS(desFile, is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    private static File getFileByPath(String filePath) {
        return (filePath == null || filePath.trim().length() == 0) ? null : new File(filePath);
    }

    /**
     * 将输入流写入文件
     *
     * @param file 文件
     * @param is   输入流
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    private static boolean writeFileFromIS(File file, InputStream is) {
        if (file == null || is == null) return false;
        if (!CopyAssets.createOrExistsFile(file)) return false;
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
            CopyAssets.closeIO(is, os);
        }
    }


    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!CopyAssets.createOrExistsDir(file.getParentFile())) return false;
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
    private static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    private static void closeIO(Closeable... closeables) {
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


    interface onCopyStart {

    }

    interface onCopyProgress {

    }

    interface onCopyError {

    }

    interface onCopySuccess {

    }
}
