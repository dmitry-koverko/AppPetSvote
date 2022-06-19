package com.petsvote.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.petsvote.domain.repository.IPreferencesRepository
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val context: Context
): IPreferencesRepository {

    private val preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = preferences.edit()

    companion object{
        var PREFERENCES = "PREFERENCES_PETSVOTE"
        var SETTINGS_NOTIFY = "SETTINGS_NOTIFY"
        var SETTINGS_ADD_PHOTOS = "SETTINGS_ADD_PHOTOS"
    }

    fun put(key: String, value: Any) = editor.apply {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, default: T): T = when (default) {
        is String -> preferences.getString(key, default) as T
        is Int -> preferences.getInt(key, default) as T
        is Boolean -> preferences.getBoolean(key, default) as T
        is Long -> preferences.getLong(key, default) as T
        is Float -> preferences.getFloat(key, default) as T
        else -> error("Only primitive types can be stored in SharedPreferences")
    }

    fun clear(key: String) = preferences.edit().remove(key).apply()
    fun clear() = preferences.edit().clear().apply()

    override suspend fun getSettingsNotify(): Boolean {
        return get<Boolean>(SETTINGS_NOTIFY, false)
    }

    override suspend fun setSettingsNotify(b: Boolean) {
        put(SETTINGS_NOTIFY, b)
    }

    override suspend fun setAddPhotosUseCase() {
        put(SETTINGS_ADD_PHOTOS, false)
    }

    override suspend fun getAddPhotosSettings(): Boolean {
        return get<Boolean>(SETTINGS_ADD_PHOTOS, true)
    }

}