package com.dx168.tmsdk.sample;

import android.app.Application;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.dx168.tmsdk.TinkerManager;
import com.dx168.tmsdk.TinkerManagerApplicationLike;
import com.dx168.tmsdk.TinkerManagerListener;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by jianjun.lin on 2016/10/31.
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.dx168.tmsdk.sample.MyApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class MyApplicationLike extends TinkerManagerApplicationLike {

    private OriginalApplication originalApplication;

    public MyApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent, Resources[] resources, ClassLoader[] classLoader, AssetManager[] assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent, resources, classLoader, assetManager);
        originalApplication = new OriginalApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TinkerManager.getInstance().init(getApplication(), "your appId", "your appSecret");
        TinkerManager.getInstance().setTag("your tag");
        TinkerManager.getInstance().queryAndApplyPatch(new TinkerManagerListener() {
            @Override
            public void onQuerySuccess(String response) {
                Log.d("TEST", "onQuerySuccess response=" + response);
            }

            @Override
            public void onQueryFailure(Throwable e) {
                Log.d("TEST", "onQueryFailure e=" + e.getMessage());
            }

            @Override
            public void onDownloadSuccess(String path) {
                Log.d("TEST", "onDownloadSuccess path=" + path);
            }

            @Override
            public void onDownloadFailure(Throwable e) {
                Log.d("TEST", "onDownloadFailure e=" + e.getMessage());
            }

            @Override
            public void onApplySuccess() {
                Log.d("TEST", "onApplySuccess");
            }

            @Override
            public void onApplyFailure(String msg) {
                Log.d("TEST", "onApplyFailure msg=" + msg);
            }

            @Override
            public void onCompleted() {
                Log.d("TEST", "onCompleted");
            }
        });
        originalApplication.onCreate();
    }

}
