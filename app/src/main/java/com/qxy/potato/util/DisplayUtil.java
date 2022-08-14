package com.qxy.potato.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @author ：Dyj
 * @date ：Created in 2022/5/30 19:47
 * @description：cpoy by wsj ,this can chang dp to px
 * @modified By：
 * @version: 1.0
 */
public class DisplayUtil {
    private static float sNonCompatDensity;
    private static float sNonCompatScaleDensity;

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    @Deprecated
    public static int dip2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    @Deprecated
    public static float px2dip(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxVal / scale + 0.5f);
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int dp2px(float dp) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxVal
     * @return
     */
    public static float px2dp(float pxVal) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxVal / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param sp
     * @return
     */
    public static int sp2px(float sp) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (sp * scale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(float pxVal) {
        return (pxVal / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * sp转px
     *
     * @param context
     * @param sp
     * @return
     */
//    @Deprecated
    public static int sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    @Deprecated
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getApplicationContext().getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static Point getScreenRealSize(Activity activity) {
        Point outSize = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealSize(outSize);
        } else {
            outSize.x = getScreenWidth();
            outSize.y = getScreenHeight();
        }
        return outSize;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * 状态栏透明
     */
    protected void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    @Deprecated
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = context.getApplicationContext().getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    public static int getStatusBarHeight2(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }


    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap captureWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap captureWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 判断手机底部是否支持导航栏显示
     * @param context
     * @return
     */
    public static boolean isNavigationBarShowing(Context context) {
        boolean haveNavigationBar = checkDeviceHasNavigationBar(context);
        if (haveNavigationBar) {
            if (Build.VERSION.SDK_INT >= 17) {
                String brand = Build.BRAND;
                String mDeviceInfo;
                if (brand.equalsIgnoreCase("HUAWEI")) {
                    mDeviceInfo = "navigationbar_is_min";
                } else if (brand.equalsIgnoreCase("XIAOMI")) {
                    mDeviceInfo = "force_fsg_nav_bar";
                } else if (brand.equalsIgnoreCase("VIVO")) {
                    mDeviceInfo = "navigation_gesture_on";
                } else if (brand.equalsIgnoreCase("OPPO")) {
                    mDeviceInfo = "navigation_gesture_on";
                } else {
                    mDeviceInfo = "navigationbar_is_min";
                }

                if (Settings.Global.getInt(context.getContentResolver(), mDeviceInfo, 0) == 0) {
                    return true;
                }
            }
        }
        return false;
    }


    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    public static void setCustomDensity( Activity activity ){
        final Application application = MyUtil.getApplication();

        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNonCompatDensity == 0){
            sNonCompatDensity = appDisplayMetrics.density;
            sNonCompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig.fontScale > 0){
                        sNonCompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity =  ((float) appDisplayMetrics.widthPixels) / 360;
        final float targetScaleDensity = targetDensity * (sNonCompatScaleDensity/sNonCompatDensity);
        final int targetDensityDpi = (int) (160*targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
