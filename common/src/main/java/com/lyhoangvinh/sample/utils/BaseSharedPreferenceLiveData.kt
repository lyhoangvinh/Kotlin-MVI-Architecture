package com.lyhoangvinh.sample.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class BaseSharedPreferenceLiveData<T>(val sharedPrefs: SharedPreferences?, val key: String, private val defValue: T) : LiveData<T>() {

    abstract fun getValueFromPreferences(key: String, defValue: T): T

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener {
        _, key ->
        if (this.key == key) {
            value = getValueFromPreferences(key, defValue)
        }
    }
    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs?.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs?.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}
