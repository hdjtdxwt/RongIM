package com.xrtz.rongim.util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 在后台还没有收集错误消息时，先将错误保存到sd卡以便开发获取错误信息（有时候错误会一闪而过就不在logcat里显示了）
 */
public class FileUtils {
    public static void writeFile2SDCard(String crashFilePath, String fileName, String result){
        File file = new File(crashFilePath, fileName);
        if(!file.getParentFile().exists()){
            boolean flag = file.getParentFile().mkdirs();
            Log.e("FileUtils","createParent---"+flag);
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("FileUtils","FileUtils----error--111--"+e.getMessage());
            }
        }
        Log.e("FileUtils", "FileUtils------");
        FileOutputStream fos=null;
        try {
            fos= new FileOutputStream(file);
            fos.write(result.getBytes());
            Log.e("FileUtils", "FileUtils------write");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FileUtils", "FileUtils------error---"+e.getMessage());
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}