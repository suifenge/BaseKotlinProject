package com.suifeng.kotlin.baseproject.vm

import android.databinding.ObservableArrayList
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.net.ex.convert
import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.data.NetRepository
import com.suifeng.kotlin.baseproject.event.RefreshLiveData
import com.suifeng.kotlin.baseproject.ex.responseError
import javax.inject.Inject

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
class FragDemoViewModel @Inject constructor(application: CustomApplication, private val netRepository: NetRepository): BaseViewModel(application) {

    val pictureData = ObservableArrayList<PictureBean.Data>()
    val refresh = RefreshLiveData()
    var page = 1

    fun getPictures(page: Int = 1) {
        netRepository.getPicture(page)
                .convert(success = {
                    if(page == 1) {
                        pictureData.clear()
                        refresh.result(RefreshLiveData.REFRESH_SUCCESS)
                    } else {
                        refresh.result(RefreshLiveData.LOAD_MORE_SUCCESS)
                    }
                    pictureData.addAll(it.data)
                }, error = {
                    if(page == 1) {
                        refresh.result(RefreshLiveData.REFRESH_FAILED)
                    } else {
                        refresh.result(RefreshLiveData.LOAD_MORE_FAILED)
                    }
                    responseError(it)
                })
    }

    fun refresh() {
        page = 1
        getPictures(page)
    }

    fun loadMore() {
        page ++
        getPictures(page)
    }
}