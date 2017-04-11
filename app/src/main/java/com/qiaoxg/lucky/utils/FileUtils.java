package com.qiaoxg.lucky.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import com.qiaoxg.lucky.app.AppConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by admin on 2017/3/14.
 */

public class FileUtils {

    /**
     * 根据路径加载本地图片
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmapByLocalPath(String path) {
        boolean isFile = isFileExistByPath(path);
        if (!isFile) {
            return null;
        }
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 保存截取好的用户头像
     * @param bitmap
     * @return
     */
    public static String saveCropAvatar(Bitmap bitmap) {
        String path = null;
        if (bitmap != null) {
            path = FileUtils.getLocalUserHeaderTempPath();
            FileUtils.saveBitmap(path, bitmap);
            if (bitmap != null && bitmap.isRecycled() == false) {
                bitmap.recycle();
            }
        }
        return path;
    }

    /**
     * 保存bitmap到本地
     *
     * @param filePath
     * @param bitmap
     */
    public static void saveBitmap(String filePath, Bitmap bitmap) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
            }
        }
    }


    /**
     * 获取手机内置存储路径
     *
     * @return
     */
    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 根据路径判断文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExistByPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File f = new File(path);
        if (f.exists() && f.isFile()) {
            return true;
        }
        return false;
    }

    /**
     * 获得系统的路径
     *
     * @return
     */
    public static String getLocalDCIMDir() {
        String result = null;
        File videoDirFile = null;
        boolean error = false;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            result = getExternalStorageDirectory();
            result += File.separator + AppConstants.DCIM_CAMERA_PATH;
            videoDirFile = new File(result);
            if (!videoDirFile.exists() && !videoDirFile.mkdirs()) {
                error = true;
            }
        }
        if (error)
            return null;
        return result;
    }

    /**
     * 获得系统的路径
     *
     * @return
     */
    public static String getLocalTempDir() {
        String result = null;
        File videoDirFile = null;
        boolean error = false;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            result = getExternalStorageDirectory();
            result += File.separator + AppConstants.LOCAL_FILE_TEMP_PATH;
            videoDirFile = new File(result);
            if (!videoDirFile.exists() && !videoDirFile.mkdirs()) {
                error = true;
            }
        }
        if (error)
            return null;
        return result;
    }

    /**
     * 获得用户头像路径
     *
     * @return
     */
    public static String getLocalUserHeaderTempPath() {
        return getLocalTempDir() + AppConstants.USER_HEADER_TEMP_FILE;
    }

}
