//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.qxy.potatos.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Description : ActivityUtil
 *
 * @author XuCanyou666, DYJ
 * @date 2020/4/27
 */

@TargetApi(14)
public class ActivityUtil {
    private static final MyActivityLifecycleCallbacks instance = new MyActivityLifecycleCallbacks();
    private static Stack<Activity> activityStack = new Stack<>();

    public ActivityUtil() {
    }

    /**
     * @return 返回生命周期回调类
     */
    public static ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return instance;
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
     * 启动 activity， 带上参数
     *
     * @param classes 需要打开的 activity
     * @param hashMap 需要传递的参数
     */
    public static void startActivity(@SuppressWarnings("rawtypes") Class classes, HashMap<String, String> hashMap) {
        startActivity(classes, hashMap, false);
    }

    /**
     * 启动 activity， 可以设置是否关闭当前 activity
     *
     * @param classes  需要打开的 activity
     * @param hashMap  需要传递的参数
     * @param isFinish 是否关闭当前 activity
     */
    public static void startActivity(@SuppressWarnings("rawtypes") Class classes, HashMap<String, String> hashMap, boolean isFinish) {
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(currentActivity, classes);
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        currentActivity.startActivity(intent);
        if (isFinish) {
            finishActivity(currentActivity);
        }
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
