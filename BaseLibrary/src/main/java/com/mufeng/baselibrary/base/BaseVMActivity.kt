package com.mufeng.baselibrary.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.mufeng.baselibrary.R
import com.mufeng.baselibrary.utils.getVmClazz
import com.mufeng.baselibrary.utils.inflateBindingWithGeneric

abstract class BaseVMActivity<VM : BaseViewModel, VB : ViewBinding>: BaseActivity() {

    protected lateinit var vm: VM
        private set

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        vm = createViewModel()
        doCreateView(savedInstanceState)
    }

    private fun doCreateView(savedInstanceState: Bundle?) {
        initImmersionBar()
        initIntent()
        initView(savedInstanceState)
        startObserve()
        initData()
    }

    open fun initImmersionBar() {
        immersionBar {
            statusBarDarkFont(true)
            statusBarColor(R.color.c_fb)
            titleBarMarginTop(binding.root)
        }
    }

    open fun initIntent() {}
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
    open fun startObserve() {}

    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }
}