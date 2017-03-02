package com.wurq.dex.fixlibrary;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by wurongqiu on 17/3/2.
 */
public class FileHelper {
    public static void copyFile(ArrayList<String> paths, File dir) throws IOException {
        for (String path: paths) {
            File outFile = new File(dir, path.substring(path.lastIndexOf("/") + 1, path.length()));
            if (!outFile.exists()) {
                InputStream in = new FileInputStream(path);
                OutputStream out = new FileOutputStream(outFile);
                copyFile(in, out);
                in.close();
                out.close();
            }
        }
    }


    public static String copyAsset(Context context, String assetName, File dir) throws IOException {
        File outFile = new File(dir, assetName);
        if (!outFile.exists()) {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open(assetName);
            OutputStream out = new FileOutputStream(outFile);
            copyFile(in, out);
            in.close();
            out.close();
        }
        return outFile.getAbsolutePath();
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    /**
     *
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    public static void unZip(String unZipfileName, String desDir){
        try{
            ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(unZipfileName)));
            ZipEntry zipEntry;
            FileOutputStream fileOut;
            File file;

            byte[] buf = new byte[2048];
            int readByte;

            while((zipEntry = zipIn.getNextEntry()) != null){
                file = new File(desDir + zipEntry.getName());

                if(zipEntry.isDirectory()){
                    file.mkdirs();
                }
                else{
                    File parent = file.getParentFile();
                    if(!parent.exists()){
                        parent.mkdirs();
                    }

                    fileOut = new FileOutputStream(file);
                    while((readByte = zipIn.read(buf) ) > 0){
                        fileOut.write(buf, 0, readByte );
                    }
                    fileOut.close();
                }
                zipIn.closeEntry();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
