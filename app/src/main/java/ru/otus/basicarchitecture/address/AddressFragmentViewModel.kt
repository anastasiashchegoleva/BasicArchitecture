package ru.otus.basicarchitecture.address

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.DataCache
import ru.otus.basicarchitecture.net.AddressService
import ru.otus.basicarchitecture.net.AddressValue
import javax.inject.Inject

class AddressFragmentViewModel @Inject constructor(
    private val dataCache: DataCache
) : ViewModel(){

    @Inject lateinit var service : AddressService
    private var activeJob: Job? = null
    private val resultAddressLive = MutableLiveData<List<AddressValue>?>()

    fun save (address: String) {
        dataCache.address = address
    }

    fun search(query: String) {
        activeJob = viewModelScope.launch {
            try {
                service.request(query) { response ->
                    resultAddressLive.value = response?.suggestions
                }
            } catch (e: Exception) {
                resultAddressLive.value = emptyList()
                Log.e("Error: ", e.message.toString())
            }
        }
    }

    fun cancel() {
        activeJob?.cancel()
        activeJob = null
    }
    fun getResultAddressLive(): LiveData<List<AddressValue>?> {
        return resultAddressLive
    }
}