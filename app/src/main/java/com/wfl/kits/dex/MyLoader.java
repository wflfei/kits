package com.wfl.kits.dex;

import dalvik.system.DexClassLoader;

/**
 * Created by wfl on 2017/12/10.
 */

public class MyLoader extends DexClassLoader {
    
    public MyLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(className, resolve);
        
    }
}
