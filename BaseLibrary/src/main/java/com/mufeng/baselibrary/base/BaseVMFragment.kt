package com.mufeng.baselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.mufeng.baselibrary.utils.getVmClazz
import com.mufeng.baselibrary.utils.inflateBindingWithGeneric

abstract class BaseVMFragment<VM : BaseViewModel, VB : ViewBinding>: BaseFragment() {


    protected lateinit var vm: VM
        private set

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initManageStartActivity()
        initArgument()
        vm = createViewModel()
        initView(savedInstanceState)
        startObserve()
        initData()
    }

    open fun initArgument() {}
    abstract fun initView(savedInstanceState: Bundle?)
    open fun startObserve() {}

    /**
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}

}