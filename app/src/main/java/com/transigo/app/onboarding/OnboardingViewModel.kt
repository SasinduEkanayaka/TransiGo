package com.transigo.app.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transigo.app.core.datastore.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {
    private val settings = SettingsDataStore(context)

    val isCompleted = settings.isOnboardingCompleted
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun markCompleted() {
        viewModelScope.launch {
            settings.setOnboardingCompleted(true)
        }
    }
}
