package com.suifeng.kotlin.baseproject.vm

import android.arch.lifecycle.MutableLiveData
import com.suifeng.kotlin.base.ui.vm.BaseViewModel
import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.bean.DataBean
import javax.inject.Inject

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */

class RecyclerViewModel @Inject constructor(application: CustomApplication): BaseViewModel(application) {

    val dataSource =  MutableLiveData<ArrayList<DataBean>>()

    init {
        setDataSource()
    }

    private fun setDataSource() {
        val dataBeans = ArrayList<DataBean>()
        for (i in 0.. 30) {
            val dataBean = DataBean("http://www.qqzhi.com/uploadpic/2014-09-16/111313757.jpg", "Android---$i")
            dataBeans.add(dataBean)
        }
        dataSource.value = dataBeans
    }
}