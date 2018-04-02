package com.wfl.hotexec;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import dalvik.system.DexClassLoader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wfl on 2017/12/21.
 */

public class DexSocketReceiver {
    private static final String TAG = "DexSocketReceiver";
    
    Context mContext;
    String directory;
    String filePath;
    
    public DexSocketReceiver(Context context) {
        this.mContext = context;
        directory = context.getDir("dexes", MODE_PRIVATE).getAbsolutePath();
        
    }
    
    private Socket mSocket;
    
    public void start() {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    trySocket();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 0, 2000);
        
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        trySocket();
                    } catch (IOException e) {
                        Log.e(TAG, "socket error!!");
                        e.printStackTrace();
                    }
                }
            }).start();
    }
    
    private File getOutputFile() {
        String dexPath = "/mnt/sdcard/test.dex";
        File file = new File(dexPath);
        return file;
    }
    
    private void trySocket() throws IOException {
        boolean hasNew = false;
        mSocket = new Socket("127.0.0.1", 6666);
        
        File file = getOutputFile();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
        bw.write("do you have new dex?");
        bw.flush();
        
        DataInputStream din = new DataInputStream(mSocket.getInputStream());
        FileOutputStream fout = new FileOutputStream(file);
        
        while (true) {
            int read = 0;
            if (din != null) {
                int length = din.readInt();
                Log.d(TAG, "dataLength: " + length);
                int dataLen = length - 4;
                
                byte[] buffer = new byte[dataLen];
                din.readFully(buffer);
                fout.write(buffer);
                fout.flush();
                loadMyDex();
            }
//            if (read == -1) {
//                break;
//            } else {
//                hasNew = true;
//            }
//            fout.write(buffer, 0, read);
        }
        
//        mSocket.close();
        
    }

    public void loadMyDex() {
        File file = getOutputFile();
        if (!file.exists()) {
            return;
        }
        String dexPath = file.getAbsolutePath();
        String libraryPath = null;

        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, directory, libraryPath, mContext.getClassLoader());
        try {
            Class c = dexClassLoader.loadClass("com.wfl.kits.dex.hotexec.Command");
            Method runMethod = c.getMethod("run");
            runMethod.setAccessible(true);
            runMethod.invoke(c.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
