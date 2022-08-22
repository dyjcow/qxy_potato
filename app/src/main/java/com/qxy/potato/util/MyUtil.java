package com.qxy.potato.util;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.interfaces.IPickerViewData;
import com.qxy.potato.R;
import com.qxy.potato.base.BaseEvent;
import com.qxy.potato.bean.VideoVersion;
import com.qxy.potato.common.EventCode;
import com.tamsiree.rxui.view.dialog.RxDialogLoading;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author ：Dyj
 * @date ：Created in 2022/4/12 22:08
 * @description：汇聚各种其他功能
 * @modified By：
 * @version: 1.0
 */
public class MyUtil {

    /**
     * 全局context
     */
    private static Application mApplicationContext;

    private static RxDialogLoading rxDialogLoading;


    /**
     * @param app 初始化全局context
     */
    public static void initialize(Application app) {
        mApplicationContext = app;
    }


    /**
     * 获得全局context
     *
     * @return 当前的全局context
     */
    public static Application getApplication() {
        return mApplicationContext;
    }


    /**
     * 关闭键盘
     */
    public static void closeSoftKeyboard() {
        InputMethodManager inputManger = (InputMethodManager) ActivityUtil.getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManger != null) {
            inputManger.hideSoftInputFromWindow(ActivityUtil.getCurrentActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static String getString(int id) {
        return getApplication().getResources().getString(id);
    }

    /**
     * 展示dialog
     *
     * @param context 传入当前Acitivity
     */
    public static void showLoading(Context context){
        rxDialogLoading = new RxDialogLoading(context);
        rxDialogLoading.setCanceledOnTouchOutside(false);
        rxDialogLoading.show();
    }

    /**
     * 成功隐藏dialog，显示成功
     */
    public static void dismissSuccessLoading(){
        rxDialogLoading.cancel(RxDialogLoading.RxCancelType.success,getString(R.string.load_success));
    }


    /**
     * 失败隐藏dialog，显示失败
     */
    public static void dismissFailedLoading(){
        rxDialogLoading.cancel(RxDialogLoading.RxCancelType.error,getString(R.string.load_error));
    }

    public static void showOneOptionPicker(List<?> list, String title){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(ActivityUtil.getCurrentActivity(), new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                BaseEvent<?> event = new BaseEvent<>(EventCode.SELECT_VERSION, list.get(options1));
                EventBusUtil.sendEvent(event);

            }
        })
                .setTitleText(title)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(16)
//                .setLayoutRes(R.layout.rank, new CustomListener() {
//                    @Override
//                    public void customLayout(View v) {
//
//                    }
//                })
                .build();

        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
    }

    /**
     * Application 层面下调用颜色资源
     *
     * @param id 颜色的资源 id
     * @return 对应的颜色值
     */
    public static int AppGetColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getApplication().getResources().getColor(id, getApplication().getTheme());
        } else {
            //noinspection deprecation
            return getApplication().getResources().getColor(id);
        }

    }

    /**
     * View 层面下调用资源 id
     *
     * @param context View 的 context 值
     * @param id 颜色的资源 id
     * @return 对应的颜色值
     */
    public static int ViewGetColor(Context context,int id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(id, context.getTheme());
        } else {
            //noinspection deprecation
            return context.getResources().getColor(id);
        }
    }

    /**
     * 获取当前天气状况的图标
     *
     * @param context 对应的 context 值
     * @param icon 传入的icon 字段
     * @return 返回拼接后查询到的资源 id
     */
    public static int getWeatherIcon(Context context, String icon){
        return context.getResources().getIdentifier("icon_"+icon,"drawable",context.getPackageName());
    }


    /**
     * 将图片压缩到指定大小
     *
     * @param w
     * @param h
     * @return
     */
    public static Bitmap compressBySize(Context context, int resourceId, float w, float h) {
        BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(resourceId,context.getTheme());
        Matrix matrix = new Matrix();
        Bitmap src = bd.getBitmap();
        matrix.postScale(w / src.getWidth(), h / src.getHeight());
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public static String split(String time){
        return time.split("T|\\+")[1];
    }

    public static int getNowHour(){
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }


    /**
     * HH:mm
     */
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("HH:mm", Locale.US);
        // 获取当前时间
        Date date = new  Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getNowLanguage( ) {
        Locale locale = getApplication().getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

}
