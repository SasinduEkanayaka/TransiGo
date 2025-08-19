package com.transigo.app.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transigo.app.data.repository.AdminDashboardRepository
import com.transigo.app.data.repository.DashboardStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val adminDashboardRepository: AdminDashboardRepository
) : ViewModel() {

    private val _dashboardStats = MutableStateFlow(DashboardStats())
    val dashboardStats: StateFlow<DashboardStats> = _dashboardStats.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadDashboardStats()
    }

    /**
     * Load dashboard statistics
     */
    fun loadDashboardStats() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            adminDashboardRepository.getDashboardStats().collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { stats ->
                        _dashboardStats.value = stats
                    },
                    onFailure = { exception ->
                        _errorMessage.value = exception.message
                    }
                )
            }
        }
    }

    /**
     * Refresh dashboard statistics
     */
    fun refreshStats() {
        loadDashboardStats()
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _errorMessage.value = null
    }
}
