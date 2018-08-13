package com.suifeng.kotlin.baseproject.vm

import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.suifeng.kotlin.base.net.ex.convert
import com.suifeng.kotlin.base.ui.vm.BaseViewModel
import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.data.NetRepository
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import es.dmoral.toasty.Toasty
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
@Singleton
class NetViewModel @Inject constructor(application: CustomApplication): BaseViewModel(application) {

    @Inject lateinit var mNetRepository: NetRepository

    val cityName = MutableLiveData<String>()
    val weather = MutableLiveData<String>()

    fun getWeather(mContext: RxAppCompatActivity) {
        if(TextUtils.isEmpty(cityName.value)) {
            Toasty.error(getApplication(), "请输入城市名称").show()
            return
        }
        mNetRepository.getWeather(cityName.value!!)
                .convert(mContext, success = {
                    val text = "城市：${it.data.city}\n日期：${it.data.forecast[0].date}\n" +
                            "温度：${it.data.forecast[0].high} ~ ${it.data.forecast[0].low}\n风力：${it.data.forecast[0].fengli}\n" +
                            "风向：${it.data.forecast[0].fengxiang}\n天气状况：${it.data.forecast[0].type}\n感冒：${it.data.ganmao}\n" +
                            "当前温度：${it.data.wendu}"
                    weather.value = text
                }, error = {
                    weather.value = it.message
                })
    }

}