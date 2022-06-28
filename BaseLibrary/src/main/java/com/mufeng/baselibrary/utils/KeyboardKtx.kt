package com.mufeng.baselibrary.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.KeyboardUtils

fun Activity.hideSoftInput(){
    KeyboardUtils.hideSoftInput(this)
}

fun Fragment.hideSoftInput(){
    KeyboardUtils.hideSoftInput(activity)
}