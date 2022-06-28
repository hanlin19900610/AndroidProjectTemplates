package com.mufeng.baselibrary.net

data class Result<T>(
    val msg: String,
    val code: Int,
    val data: T
)
