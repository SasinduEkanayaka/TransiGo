package com.transigo.app.admin;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0007J\u0006\u0010\u0015\u001a\u00020\u000fJ\b\u0010\u0016\u001a\u00020\u000fH\u0002J\b\u0010\u0017\u001a\u00020\u000fH\u0002J\u0006\u0010\u0018\u001a\u00020\u000fJ\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001d"}, d2 = {"Lcom/transigo/app/admin/DriverViewModel;", "Landroidx/lifecycle/ViewModel;", "driverRepository", "Lcom/transigo/app/data/repository/DriverRepository;", "(Lcom/transigo/app/data/repository/DriverRepository;)V", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_uiState", "Lcom/transigo/app/admin/DriverUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addDriver", "", "driver", "Lcom/transigo/app/data/model/Driver;", "clearError", "deleteDriver", "driverId", "hideAddEditDialog", "observeDrivers", "observeSearchQuery", "showAddDriverDialog", "showEditDriverDialog", "updateDriver", "updateSearchQuery", "query", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class DriverViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.transigo.app.data.repository.DriverRepository driverRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.transigo.app.admin.DriverUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.transigo.app.admin.DriverUiState> uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    
    @javax.inject.Inject
    public DriverViewModel(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.repository.DriverRepository driverRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.transigo.app.admin.DriverUiState> getUiState() {
        return null;
    }
    
    private final void observeDrivers() {
    }
    
    private final void observeSearchQuery() {
    }
    
    public final void updateSearchQuery(@org.jetbrains.annotations.NotNull
    java.lang.String query) {
    }
    
    public final void showAddDriverDialog() {
    }
    
    public final void showEditDriverDialog(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Driver driver) {
    }
    
    public final void hideAddEditDialog() {
    }
    
    public final void addDriver(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Driver driver) {
    }
    
    public final void updateDriver(@org.jetbrains.annotations.NotNull
    java.lang.String driverId, @org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Driver driver) {
    }
    
    public final void deleteDriver(@org.jetbrains.annotations.NotNull
    java.lang.String driverId) {
    }
    
    public final void clearError() {
    }
}