package com.suifeng.kotlin.baseproject.activity

import android.content.Intent
import android.view.View
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.databinding.ActivityDemoBinding

/**
 * @author ljc
 * @data 2018/8/7
 * @describe
 */
class DemoActivity: BaseActivity<ActivityDemoBinding>(
        R.layout.activity_demo,
        R.id.btn_recycler_view, R.id.btn_net, R.id.btn_fragment
) {

    override fun init() {
    }

    override fun initStatusBar() {

    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_recycler_view -> startActivity(Intent(this, RecyclerActivity::class.java))
            R.id.btn_net -> startActivity(Intent(this, NetActivity::class.java))
            R.id.btn_fragment -> startActivity(Intent(this, FragmentActivity::class.java))
        }
    }

}