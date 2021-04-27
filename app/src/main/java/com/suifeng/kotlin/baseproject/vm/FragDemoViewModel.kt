package com.suifeng.kotlin.baseproject.vm

import androidx.databinding.ObservableArrayList
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.utils.log.KLog
import com.suifeng.kotlin.baseproject.bean.PictureBeanItem
import com.suifeng.kotlin.baseproject.data.NetRepository
import com.suifeng.kotlin.baseproject.data.RetrofitFactory
import com.suifeng.kotlin.baseproject.event.RefreshLiveData
import com.suifeng.kotlin.baseproject.ex.responseError

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
class FragDemoViewModel: BaseViewModel() {

    val pictureData = ObservableArrayList<PictureBeanItem>()
    val refresh = RefreshLiveData()
    var page = 1

    private val mNetRepository by lazy {
        NetRepository(RetrofitFactory.picApi)
    }

    fun getPictures(page: Int = 1) {
        launchOnUI({
            val pictureBean = mNetRepository.getPicture(page)
            if(page == 1) {
                pictureData.clear()
                refresh.result(RefreshLiveData.REFRESH_SUCCESS)
            } else {
                refresh.result(RefreshLiveData.LOAD_MORE_SUCCESS)
            }
            pictureData.addAll(pictureBean)
        }, {
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