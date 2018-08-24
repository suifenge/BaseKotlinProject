package com.suifeng.kotlin.base.mvvm.livedata;

import com.suifeng.kotlin.base.mvvm.livedata.ErrorLiveData.Error;

import org.jetbrains.annotations.NotNull;

public interface ObserverError {
    Boolean onChangedNeedIntercept(@NotNull Error error);
}
