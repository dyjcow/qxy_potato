package com.qxy.potato.module.home.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.qxy.potato.base.BaseEvent;
import com.qxy.potato.common.EventCode;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.util.EventBusUtil;
import com.tencent.mmkv.MMKV;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/14 10:07
 * @description：用于取消当前连接状态值
 * @modified By：
 * @version: 1.0
 */
public class ClientCancelWork extends Worker {
    public ClientCancelWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * Override this method to do your actual background processing.  This method is called on a
     * background thread - you are required to <b>synchronously</b> do your work and return the
     * {@link Result} from this method.  Once you return from this
     * method, the Worker is considered to have finished what its doing and will be destroyed.  If
     * you need to do your work asynchronously on a thread of your own choice, see
     * {@link ListenableWorker}.
     * <p>
     * A Worker is given a maximum of ten minutes to finish its execution and return a
     * {@link Result}.  After this time has expired, the Worker will
     * be signalled to stop.
     *
     * @return The {@link Result} of the computation; note that
     * dependent work will not execute if you use
     * {@link Result#failure()} or
     * {@link Result#failure(Data)}
     */
    @NonNull
    @Override
    public Result doWork() {
        MMKV.defaultMMKV().encode(GlobalConstant.IS_CLIENT,false);
        BaseEvent<String> event = new BaseEvent<>(EventCode.CLIENT_AGAIN,"client");
        EventBusUtil.sendEvent(event);
        return Result.success();
    }
}
