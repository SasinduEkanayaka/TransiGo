package com.transigo.app.core.service;

/**
 * Firebase Cloud Messaging service to handle token refresh and incoming messages.
 */
@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\fH\u0016J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\"\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00132\b\u0010\u0017\u001a\u0004\u0018\u00010\u0013H\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/transigo/app/core/service/TransiGoFcmService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "profileRepository", "Lcom/transigo/app/data/repository/ProfileRepository;", "getProfileRepository", "()Lcom/transigo/app/data/repository/ProfileRepository;", "setProfileRepository", "(Lcom/transigo/app/data/repository/ProfileRepository;)V", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "createNotificationChannel", "", "onCreate", "onMessageReceived", "remoteMessage", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "", "showNotification", "title", "body", "bookingId", "Companion", "app_debug"})
public final class TransiGoFcmService extends com.google.firebase.messaging.FirebaseMessagingService {
    @javax.inject.Inject
    public com.transigo.app.data.repository.ProfileRepository profileRepository;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_ID = "booking_notifications";
    private static final int NOTIFICATION_ID = 1;
    @org.jetbrains.annotations.NotNull
    public static final com.transigo.app.core.service.TransiGoFcmService.Companion Companion = null;
    
    public TransiGoFcmService() {
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
    public void onCreate() {
    }
    
    /**
     * Called when a new FCM token is generated.
     * Updates the token in Firestore for the current user.
     */
    @java.lang.Override
    public void onNewToken(@org.jetbrains.annotations.NotNull
    java.lang.String token) {
    }
    
    /**
     * Called when a message is received while the app is in the foreground.
     */
    @java.lang.Override
    public void onMessageReceived(@org.jetbrains.annotations.NotNull
    com.google.firebase.messaging.RemoteMessage remoteMessage) {
    }
    
    /**
     * Creates notification channel for Android O and above
     */
    private final void createNotificationChannel() {
    }
    
    /**
     * Shows a notification with the given title and body
     */
    private final void showNotification(java.lang.String title, java.lang.String body, java.lang.String bookingId) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/transigo/app/core/service/TransiGoFcmService$Companion;", "", "()V", "CHANNEL_ID", "", "NOTIFICATION_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}