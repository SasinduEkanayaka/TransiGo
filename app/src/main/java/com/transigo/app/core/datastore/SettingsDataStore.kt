package com.transigo.app.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "transigo_settings"

val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

object SettingsKeys {
    val ONBOARDING_COMPLETED: Preferences.Key<Boolean> = booleanPreferencesKey("onboarding_completed")
}

class SettingsDataStore(private val context: Context) {
    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[SettingsKeys.ONBOARDING_COMPLETED] ?: false
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.ONBOARDING_COMPLETED] = completed
        }
    }
}
