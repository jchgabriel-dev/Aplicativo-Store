package com.example.example

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreClass(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("SETTINGS")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_SWITCH_KEY = booleanPreferencesKey("user_switch")
    }

    val getUser: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

    val getSwitch: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[USER_SWITCH_KEY] ?: false
        }

    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    suspend fun saveSwitch(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USER_SWITCH_KEY] = value
        }
    }
}