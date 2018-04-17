package com.yadong.bugly;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @packInfo:com.yadong.bugly
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2017/12/18
 * Time: 10:25
 */

public class DongAppLicationLike extends DefaultApplicationLike {

    public DongAppLicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application,
                tinkerFlags,
                tinkerLoadVerifyFlag,
                applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buglyInit();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        //安装tinker
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }



    /**
     * bugly初始化
     */
    private void buglyInit() {
        Context context = getApplication();
//         获取当前包名
        String packageName = context.getPackageName();
//         获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
//         设置是否为上报进程
        BuglyStrategy strategy = new BuglyStrategy();

        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//         初始化Bugly
//         第三个参数为SDK调试模式开关，调试模式的行为特性如下：
//         输出详细的Bugly SDK的Log；
//         每一条Crash都会被立即上报；
//         自定义日志将会在Logcat中输出。
//         建议在测试阶段建议设置成true，发布时设置为false。
//         错误上报初始化
        Bugly.init(context,"ae8deb769a",true,strategy);
    }


    /**
     * 获取进程号对应的进程名
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
