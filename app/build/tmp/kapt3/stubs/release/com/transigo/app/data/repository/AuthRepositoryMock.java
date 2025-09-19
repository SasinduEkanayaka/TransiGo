package com.transigo.app.data.repository;

/**
 * Mock AuthRepository for demonstration purposes.
 * This will be replaced with Firebase implementation later.
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\nJ\u0006\u0010\u000b\u001a\u00020\fJ2\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J<\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001aJ*\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006\u001e"}, d2 = {"Lcom/transigo/app/data/repository/AuthRepositoryMock;", "", "()V", "_currentUser", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/transigo/app/data/model/User;", "users", "", "", "currentUserFlow", "Lkotlinx/coroutines/flow/Flow;", "isUserAuthenticated", "", "login", "Lkotlin/Result;", "email", "password", "login-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "", "logout-d1pmJ48", "()Ljava/lang/Object;", "register", "fullName", "register-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetPassword", "resetPassword-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class AuthRepositoryMock {
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.String, com.transigo.app.data.model.User> users = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.transigo.app.data.model.User> _currentUser = null;
    
    public AuthRepositoryMock() {
        super();
    }
    
    /**
     * Mock current user flow
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.transigo.app.data.model.User> currentUserFlow() {
        return null;
    }
    
    /**
     * Mock authentication check
     */
    public final boolean isUserAuthenticated() {
        return false;
    }
}