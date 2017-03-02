package com.wurq.dex.hotfix;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.wurq.dex.fixlibrary.Hotfix;
import com.wurq.dex.fixlibrary.SignVerifyPatchFailedException;

/**
 * Created by wurongqiu on 17/3/2.
 */
public class HotfixApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Hotfix.install(this);
        try{
            Hotfix.loadPatch(this, Environment.getExternalStorageDirectory().getAbsolutePath().concat("/patch.apk"), false);
        }catch (SignVerifyPatchFailedException e){
            Toast.makeText(this,"exception",Toast.LENGTH_LONG);
            Log.e("hotfix","sign is not right");
        }
    }
}
