package com.forbitbd.employeeman.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.forbitbd.androidutils.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;

public class FileUtils {

    public static String saveFile(String directory, String filename, ResponseBody responseBody){

        File dir = new File(directory);

        if(!dir.exists()){
            dir.mkdirs();
        }

        File myFile = new File(directory, filename);

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            byte[] fileReader = new byte[4096];

            long fileSize = responseBody.contentLength();


            inputStream = responseBody.byteStream();
            outputStream = new FileOutputStream(myFile);

            while (true) {
                int read = inputStream.read(fileReader);

                if (read == -1) {
                    break;
                }

                outputStream.write(fileReader, 0, read);

            }

            outputStream.flush();

            return myFile.getPath();
        } catch (IOException e) {
            Log.d("HHHHHH",e.getMessage());
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static void openFile(Context context ,String path) {
        File file = new File(path);

        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Uri uri = FileProvider.getUriForFile(context,
                context.getPackageName()+".fileprovider",file);

        target.setDataAndType(uri,"application/vnd.ms-excel");

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            PackageManager pm = context.getPackageManager();
            if (intent.resolveActivity(pm) != null) {
                context.startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
            //context.showToast("Please Download a Excel Reader");
        }

    }

    public static String getDirectory(String appName, String projectName, String dirName){

        String file = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator+appName
                + File.separator+projectName
                + File.separator+dirName;

        return file;
    }

    public static void test(){
        File file = Environment.getDownloadCacheDirectory();

        Log.d("HHHHHH",file.getAbsolutePath());

       File[] fileList = file.listFiles();

       for (File x : fileList){
           Log.d("HHHHHH",x.getAbsolutePath());
       }
    }
}
