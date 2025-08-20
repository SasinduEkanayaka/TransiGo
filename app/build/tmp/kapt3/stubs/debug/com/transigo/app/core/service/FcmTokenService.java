package com.transigo.app.core.service;

/**
 * Service for managing Firebase Cloud Messaging tokens.
 */
@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\r"}, d2 = {"Lcom/transigo/app/core/service/FcmTokenService;", "", "firebaseMessaging", "Lcom/google/firebase/messaging/FirebaseMessaging;", "(Lcom/google/firebase/messaging/FirebaseMessaging;)V", "getFcmToken", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "subscribeToTopic", "", "topic", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unsubscribeFromTopic", "app_debug"})
public final class FcmTokenService {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.messaging.FirebaseMessaging firebaseMessaging = null;
    
    @javax.inject.Inject
    public FcmTokenService(@org.jetbrains.annotations.NotNull
    com.google.firebase.messaging.FirebaseMessaging firebaseMessaging) {
        super();
    }
    
    /**
     * Gets the current FCM token.
     * @return FCM token or null if unable to retrieve
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getFcmToken(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Subscribes to a topic for push notifications.
     * @param topic The topic name to subscribe to
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object subscribeToTopic(@org.jetbrains.annotations.NotNull
    java.lang.String topic, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Unsubscribes from a topic.
     * @param topic The topic name to unsubscribe from
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object unsubscribeFromTopic(@org.jetbrains.annotations.NotNull
    java.lang.String topic, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}