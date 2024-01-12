package com.qxy.potatos.util.AI.tracker

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import org.json.JSONArray
import java.lang.Integer.max
import java.lang.Integer.min

class MotionEventTracker(var context: Context) {
    companion object {
        const val TAG = "ClientAI#tracker"
    }

    interface ITrackDataReadyListener {
        fun onTrackDataReady(dataList: JSONArray)
    }

    private var width = 0
    private var height = 0
    private var density = 1f
    private var listener: ITrackDataReadyListener? = null

    fun checkAndInit(listener: ITrackDataReadyListener) {
        this.listener = listener
        val metric = context.resources.displayMetrics
        width = min(metric.widthPixels, metric.heightPixels)
        height = max(metric.widthPixels, metric.heightPixels)
        density = metric.density
    }

    private var currentEvents: JSONArray? = null
    private var currentDownTime = 0L

    fun recordMotionEvent(ev: MotionEvent) {
        if (ev.pointerCount > 1) {
            currentEvents = null
            return
        }
        if (ev.action == MotionEvent.ACTION_DOWN) {
            currentEvents = JSONArray()
            currentDownTime = ev.eventTime
        }
        if (currentEvents != null) {
            if (ev.historySize > 0) {
                for (i in 0 until ev.historySize) {
                    currentEvents?.put(buildPoint(ev.getHistoricalX(i), ev.getHistoricalY(i), ev.getHistoricalEventTime(i)))
                }
            }
            currentEvents?.put(buildPoint(ev.x, ev.y, ev.eventTime))
        }
        if (ev.action == MotionEvent.ACTION_UP) {
            currentEvents?.let {
                if (it.length() >= 6) {
                    listener?.onTrackDataReady(it) // 触发预测
                    Log.i(TAG, "cache events, eventCount=${it.length()}, data=$it")
                } else {
                    // 过滤点击和误触轨迹
                    Log.i(TAG, "skipped short events, eventCount=${it.length()}, data=$it")
                }
            }
            currentEvents = null
        }
    }

    private fun buildPoint(x: Float, y: Float, timestamp: Long): JSONArray {
        val point = JSONArray()
        point.put(x)
        point.put(y)
        point.put(width)
        point.put(height)
        point.put(density)
        point.put(timestamp - currentDownTime)
        return point
    }


}