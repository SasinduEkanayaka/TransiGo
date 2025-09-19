package com.transigo.app;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u000eH\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/transigo/app/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "profileRepository", "Lcom/transigo/app/data/repository/ProfileRepository;", "getProfileRepository", "()Lcom/transigo/app/data/repository/ProfileRepository;", "setProfileRepository", "(Lcom/transigo/app/data/repository/ProfileRepository;)V", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "askNotificationPermission", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "registerFcmToken", "app_release"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject
    public com.transigo.app.data.repository.ProfileRepository profileRepository;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> requestPermissionLauncher = null;
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.transigo.app.data.repository.ProfileRepository getProfileRepository() {
        return null;
    }
    
    public final void setProfileRepository(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.repository.ProfileRepository p0) {
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void askNotificationPermission() {
    }
    
    private final void registerFcmToken() {
    }
}