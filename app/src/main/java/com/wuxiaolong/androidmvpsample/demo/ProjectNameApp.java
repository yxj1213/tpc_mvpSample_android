package com.wuxiaolong.androidmvpsample.demo;

import android.app.Application;
import android.app.Notification;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;

import com.tpc.androidmvpsample.common.mvp.view.BaseActivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 类: ProjectNameApp
 * 功能描述:
 * 创建人: 邓奋武
 * 创建日期: 2016/12/14 09:14
 * 开发环境: JDK1.8
 */
public class ProjectNameApp extends Application
{
    private static ProjectNameApp ourInstance = new ProjectNameApp();
    public static Handler mHandler;
    public SharedPreferences trackConf = null;
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    /**
     * 轨迹服务ID
     */
    public static long serviceId = 152798;

    /**
     * Entity标识
     */
    public static String entityName = "myTrace";
    public Notification notification = null;
    public boolean isRegisterReceiver = false;
    /**
     * 服务是否开启标识
     */
    public boolean isTraceStarted = false;
    /**
     * 采集是否开启标识
     */
    public boolean isGatherStarted = false;

    public static ProjectNameApp getInstance()
    {
        return ourInstance;
    }

    //    public Intent intent2;
    @Override
    public void onCreate()
    {
        super.onCreate();

        ourInstance = this;

        mHandler = new Handler();
        BaseActivity.url="http://www.baidu.com.cn/";

//        //流量监控统计
//
//        intent2 = new Intent(getApplicationContext(), TrafficService.class);
//        startService(intent2);
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid)
    {
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName))
            {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable)
        {
            throwable.printStackTrace();
        } finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            } catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static void initSmallVideo()
    {
        // 初始化拍摄
        // JianXiCamera.initialize(false, Environment.getExternalStorageDirectory()+"/zz.mp4");
    }

    public static int timeOut = 30000;

    //去掉Android烦人的默认闪退Dialog
    class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler
    {

        @Override
        public void uncaughtException(Thread thread, Throwable ex)
        {
            ex.printStackTrace();
            // do some work here

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 获取请求标识
     *
     * @return
     */
    public int getTag()
    {
        return mSequenceGenerator.incrementAndGet();
    }
}
