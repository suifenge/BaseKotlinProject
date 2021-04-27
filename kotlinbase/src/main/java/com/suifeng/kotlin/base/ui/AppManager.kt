package com.suifeng.kotlin.base.ui

import android.app.Activity
import java.util.*
import kotlin.system.exitProcess

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
class AppManager private constructor(){

    companion object {
        private var instance: AppManager? = null
        get() {
            if(field == null) {
                field = AppManager()
            }
            return field
        }

        @Synchronized
        fun get(): AppManager {
            return instance!!
        }
    }

    private val mActivities by lazy {
        LinkedList<Activity>()
    }

    fun size(): Int {
        return mActivities.size
    }

    @Synchronized
    fun getForwardActivity(): Activity? {
        return if(size() > 0) mActivities[size() - 1] else null
    }

    @Synchronized
    fun addActivity(activity: Activity) {
        mActivities.add(activity)
    }

    @Synchronized
    fun removeActivity(activity: Activity) {
        if(mActivities.contains(activity)) {
            mActivities.remove(activity)
        }
    }

    @Synchronized
    fun finishActivity(activity: Activity?) {
        if(activity != null && mActivities.contains(activity)) {
            mActivities.remove(activity)
            activity.finish()
        }
    }

    fun clear() {
        mActivities.forEach {
            removeActivity(it)
            it.finish()
        }
    }

    fun exitApp(killProgress: Boolean) {
        clear()
        if(killProgress) {
            exitProcess(0)
        }
    }

    @Synchronized
    fun clearToTop() {
        for (i in (mActivities.size - 2) downTo 0) {
            val activity = mActivities[i]
            removeActivity(activity)
            activity.finish()
        }
    }

}