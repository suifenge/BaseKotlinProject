package com.suifeng.kotlin.baseproject.vm

import android.text.TextUtils
import androidx.databinding.ObservableField
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.net.ex.convert
import com.suifeng.kotlin.baseproject.data.NetRepository
import com.suifeng.kotlin.baseproject.data.RetrofitFactory
import com.suifeng.kotlin.baseproject.ex.responseError

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */

class NetViewModel: BaseViewModel() {

    val cityName = ObservableField<String>()
    val weather = ObservableField<String>()

    private val mNetRepository by lazy {
        NetRepository(RetrofitFactory.commonApi)
    }

    fun getWeather() {
        if(TextUtils.isEmpty(cityName.get())) {
            toast.show("请输入城市名称")
            return
        }
        mNetRepository.getWeather(cityName.get()!!)
                .convert(rx = this, success = {
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