package com.suifeng.kotlin.baseproject.vm

import android.databinding.ObservableArrayList
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.net.ex.convert
import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.data.NetRepository
import com.suifeng.kotlin.baseproject.ex.responseError
import javax.inject.Inject

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
class FragDemoViewModel @Inject constructor(application: CustomApplication, private val netRepository: NetRepository): BaseViewModel(application) {

    val pictureData = ObservableArrayList<PictureBean.Data>()

    fun getPictures() {
        netRepository.getPicture(1)
                .convert(success = {
                    pictureData.addAll(it.data)
                }, error = {
                    responseError(it)
                })
    }
}