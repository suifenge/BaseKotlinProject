package com.suifeng.kotlin.baseproject.vm

import android.databinding.ObservableArrayList
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.bean.DataBean
import javax.inject.Inject

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */

class RecyclerViewModel @Inject constructor(application: CustomApplication): BaseViewModel(application) {

    val dataList = ObservableArrayList<DataBean>()

    init {
        setDataSource()
    }

    private fun setDataSource() {
        for (i in 0.. 30) {
            val dataBean = DataBean("http://www.qqzhi.com/uploadpic/2014-09-16/111313757.jpg", "Android---$i")
            dataList.add(dataBean)
        }
    }
}