package ru.otus.basicarchitecture.info

import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.DataCache
import javax.inject.Inject

class InfoFragmentViewModel @Inject constructor(
    val dataCache: DataCache
) : ViewModel(){

    fun getAddressInfo():String {
        val addressResult = StringBuilder()
        if (dataCache.address != "") addressResult.append(dataCache.address)
        return addressResult.toString()
    }
}