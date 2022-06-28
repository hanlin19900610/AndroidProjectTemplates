package com.mufeng.demo.ui.page.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mufeng.demo.ui.adapter.ViewPagerAdapter
import com.mufeng.demo.ui.page.explore.ExploreFragment
import com.lnkj.android.demo.ui.page.home.HomeFragment
import com.mufeng.demo.ui.page.message.MessageFragment
import com.mufeng.demo.ui.page.mine.MineFragment
import com.mufeng.baselibrary.base.BaseVMActivity
import com.mufeng.baselibrary.core.observeState
import com.mufeng.baselibrary.utils.clickWithTrigger
import com.mufeng.baselibrary.utils.onPageSelected
import com.mufeng.baselibrary.utils.toast
import com.mufeng.demo.databinding.ActivityMainBinding

class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>() {

    private val fragments = arrayListOf<Fragment>()
    private val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragments)

    override fun initView(savedInstanceState: Bundle?) {

        fragments.add(HomeFragment())
        fragments.add(ExploreFragment())
        fragments.add(MessageFragment())
        fragments.add(MineFragment())

        binding.viewPage.adapter = adapter

        binding.bbl.setOnItemSelectedListener { _, _, currentPosition ->
            vm.dispatch(MainUiAction.UpdateBottomBarLayoutPosition(currentPosition))
        }

        binding.viewPage.onPageSelected {
            vm.dispatch(MainUiAction.UpdateViewPagerPosition(it))
        }

        binding.bblPublish.clickWithTrigger {
            toast("点击发布")
        }

    }

    override fun initData() {
    }

    override fun startObserve() {
        vm.viewStates.let { states ->
            states.observeState(this, MainUiState::viewPagerPosition) {
                binding.viewPage.setCurrentItem(it, false)
            }

            states.observeState(this, MainUiState::bottomBarLayoutPosition) {
                binding.bbl.currentItem = it
            }
        }
    }
}