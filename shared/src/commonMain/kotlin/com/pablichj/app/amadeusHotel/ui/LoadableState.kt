package com.pablichj.app.amadeusHotel.ui

sealed class LoadableState<out T> {
    class Success<T>(val value: T) : LoadableState<T>()
    object Loading : LoadableState<Nothing>()
    object Error : LoadableState<Nothing>()
}