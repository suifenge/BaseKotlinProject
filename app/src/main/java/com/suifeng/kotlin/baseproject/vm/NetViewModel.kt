package com.suifeng.kotlin.baseproject.vm

import android.databinding.ObservableField
import android.text.TextUtils
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.net.ex.convert
import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.data.NetRepository
import com.suifeng.kotlin.baseproject.ex.responseError
import es.dmoral.toasty.Toasty
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
@Singleton
class NetViewModel @Inject constructor(application: CustomApplication, private val mNetRepository: NetRepository): BaseViewModel(application) {

    val cityName = ObservableField<String>()
    val weather = ObservableField<String>()

    fun getWeather() {
        if(TextUtils.isEmpty(cityName.get())) {
            Toasty.error(getApplication(), "请输入城市名称").show()
            return
        }
        mNetRepository.getWeather(cityName.get()!!)
                .convert(success = {
                    val text = "城市：${it.data.city}\n日期：${it.data.forecast[0].date}\n" +
                            "温度：${it.data.forecast[0].high} ~ ${it.data.forecast[0].low}\n风力：${it.data.forecast[0].fengli}\n" +
                            "风向：${it.data.forecast[0].fengxiang}\n天气状况：${it.data.forecast[0].type}\n感冒：${it.data.ganmao}\n" +
                            "当前温度：${it.data.wendu}"
                    weather.set(text)
                }, error = {
                    weather.set(it.message)
                    responseError(it)
                })
    }

}