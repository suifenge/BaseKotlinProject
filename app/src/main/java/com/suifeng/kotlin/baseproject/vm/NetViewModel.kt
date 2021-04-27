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
        launchOnUI({
            val weatherBean = mNetRepository.getWeather(cityName.get()!!)
            val text = "城市：${weatherBean.result.today.city}\n今日天气：${weatherBean.result.today.weather}\n" +
                    "风向：${weatherBean.result.today.wind}\n穿衣建议：${weatherBean.result.today.dressing_advice}"
            weather.set(text)
        }, {
            weather.set(it.message)
            responseError(it)}
        )
    }

}