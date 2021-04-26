package com.suifeng.kotlin.baseproject.vm

import androidx.databinding.ObservableArrayList
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.baseproject.bean.DataBean

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */

class RecyclerViewModel: BaseViewModel() {

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