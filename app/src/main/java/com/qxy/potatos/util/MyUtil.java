package com.qxy.potatos.util;


import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.view.WheelView;
import com.qxy.potatos.R;
import com.qxy.potatos.base.BaseEvent;
import com.qxy.potatos.common.EventCode;
import com.qxy.potatos.util.AI.tflite.OperatingHandClassifier;
import com.tamsiree.rxui.view.dialog.RxDialogLoading;

import java.util.List;

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

    private static OptionsPickerView pvOptions;


    /**
     * @param app 初始化全局context
     */
    public static void initialize(Application app) {
        mApplicationContext = app;
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

    /**
     * 展示dialog
     *
     * @param context 传入当前Acitivity
     */
    public static void showLoading(Context context) {
        rxDialogLoading = new RxDialogLoading(context);
        rxDialogLoading.setCanceledOnTouchOutside(false);
        rxDialogLoading.show();
    }

    /**
     * 成功隐藏dialog，显示成功
     */
    public static void dismissSuccessLoading() {
        rxDialogLoading.cancel(RxDialogLoading.RxCancelType.success, getString(R.string.load_success));
    }

    public static String getString(int id) {
        return getApplication().getResources().getString(id);
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
     * 失败隐藏dialog，显示失败
     */
    public static void dismissFailedLoading() {
        rxDialogLoading.cancel(RxDialogLoading.RxCancelType.error, getString(R.string.load_error));
    }

    public static void showOneOptionPicker(List<?> list, int handLabel) {

        OptionsPickerBuilder builder = new OptionsPickerBuilder(ActivityUtil.getCurrentActivity(),
                (options1, options2, options3, v) -> {
                    //返回的分别是三个级别的选中位置
                    BaseEvent<?> event = new BaseEvent<>(EventCode.SELECT_VERSION, list.get(options1));
                    EventBusUtil.sendEvent(event);

                });
        pvOptions = builder
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(19)
                .setDividerColor(Color.GRAY)
                .setDividerType(WheelView.DividerType.WRAP)
                .isAlphaGradient(true)
                .setLayoutRes(R.layout.layout_pickview_dialog, v -> {
                    TextView textView;
                    if (handLabel == OperatingHandClassifier.labelRight){
                        textView = v.findViewById(R.id.btnSubmitRight);
                    }else {
                        textView = v.findViewById(R.id.btnSubmitLeft);
                    }
                    textView.setVisibility(View.VISIBLE);
                    textView.setOnClickListener(v1 -> {
                        pvOptions.returnData();
                        pvOptions.dismiss();
                    });
                })
                .build();
        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
    }

}
