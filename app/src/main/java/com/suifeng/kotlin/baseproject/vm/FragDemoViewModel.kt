package com.suifeng.kotlin.baseproject.vm

import android.arch.lifecycle.MutableLiveData
import com.suifeng.kotlin.base.net.ex.convert
import com.suifeng.kotlin.base.ui.vm.BaseViewModel
import com.suifeng.kotlin.base.utils.log.KLog
import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.data.NetRepository
import com.trello.rxlifecycle2.components.support.RxFragment
import javax.inject.Inject

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
class FragDemoViewModel @Inject constructor(application: CustomApplication): BaseViewModel(application) {

    @Inject
    lateinit var netRepository: NetRepository

    val pictureData = MutableLiveData<ArrayList<PictureBean.Data>>()

    fun getPictures(mContext: RxFragment) {
        netRepository.getPicture(1)
                .convert(mContext, success = {
                    pictureData.value = it.data
                }, error = {
                    KLog.i("wtf--->${it.message}")
                })
    }
}