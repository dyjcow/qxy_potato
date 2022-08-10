//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.qxy.potato.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.os.Bundle;

import java.util.Stack;

/**
 * Description : ActivityUtil
 *
 * @author XuCanyou666, DYJ
 * @date 2020/4/27
 */

@TargetApi(14)
public class ActivityUtil {
    private static Stack<Activity> activityStack = new Stack<>();
    private static final MyActivityLifecycleCallbacks instance = new MyActivityLifecycleCallbacks();

    public ActivityUtil() {
    }

    /**
     * @return 返回生命周期回调类
     */
    public static ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return instance;
    }

    /**
     * 结结束当前Activity
     *
     * @param activity 当前Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }

    }


    /**
     * 不用 finish 当前 Activity 时直接调用此方法
     *
     * @param classes
     */
    public static void startActivity(Class classes) {
        startActivity(classes, false);
    }


    /**
     * 需要 finish 当前 Activity 时调用此方法，布尔值参数传入 true
     *
     * @param classes  需要打开的 activity
     * @param isFinish 是否 finish 当前 activity
     */
    public static void startActivity(Class classes, boolean isFinish) {
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(currentActivity, classes);
        currentActivity.startActivity(intent);
        if (isFinish) {
            finishActivity(currentActivity);
        }
    }




    /**
     * 关闭所有 Activity
     */
    public static void closeAllActivity() {
        while (true) {
            Activity activity = getCurrentActivity();
            if (null == activity) {
                return;
            }
            finishActivity(activity);
        }
    }

    /**
     * 得到当前的 Activity
     *
     * @return 当前 Activity
     */
    public static Activity getCurrentActivity() {
        Activity activity = null;
        if (!activityStack.isEmpty()) {
            activity = activityStack.peek();
        }
        return activity;
    }

    /**
     * 携带一个数据跳转
     *
     * @param classes 需要跳转过去的Activity
     * @param data 传过去的数据
     */
    public static void actionStart(Class classes, String data){
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(currentActivity, classes);
        intent.putExtra("param1",data);
        currentActivity.startActivity(intent);
    }


    /**
     * 携带两个数据跳转
     *
     * @param classes 需要跳转过去的Activity
     * @param data1 传过去的数据1
     * @param data2 传过去的数据2
     */
    public static void actionSecondStart(Class classes, String data1, String data2){
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(currentActivity, classes);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        currentActivity.startActivity(intent);
    }

    public static void actionThirdStart(Class classes, String data1, String data2, int path){
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(currentActivity, classes);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        intent.putExtra("param3",path);
        currentActivity.startActivity(intent);
    }

    /**
     * 获取数据
     *
     * @return 返回接收到的数据
     */
    public static String getIntentData(){
        Intent intent = getCurrentActivity().getIntent();
        return intent.getStringExtra("param1");
    }

    /**
     * 获取第二个数据
     *
     * @return 返回接收到的第二个数据
     */
    public static String getIntentSecondData(){
        Intent intent = getCurrentActivity().getIntent();
        return intent.getStringExtra("param2");
    }

    public static int getIntentThirdData(){
        Intent intent = getCurrentActivity().getIntent();
        return intent.getIntExtra("param3",0);
    }

    private static class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
        private MyActivityLifecycleCallbacks() {
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            ActivityUtil.activityStack.remove(activity);
            ActivityUtil.activityStack.push(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityUtil.activityStack.remove(activity);
        }
    }
}
