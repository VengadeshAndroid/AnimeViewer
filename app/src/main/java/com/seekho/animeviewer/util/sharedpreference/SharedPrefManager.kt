package com.seekho.animeviewer.util.sharedpreference

import com.seekho.animeviewer.util.Constants.SharedKey.TOKEN
import javax.inject.Inject


class SharedPrefManager @Inject constructor(private val sharedPref: SharedPref) {

    fun clearData() {
        sharedPref.clear()
    }

    var preference: String
        get() = sharedPref.getStringValue(TOKEN)
        set(preference) = sharedPref.setSharedValue(TOKEN, preference)




}