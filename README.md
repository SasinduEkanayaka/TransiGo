# TransiGo - Transfer Booking Android App

A modern Android application for booking airport and hotel transfers, built with Kotlin, Jetpack Compose, and Firebase.

## 🚀 Features

- **Modern UI**: Built with Jetpack Compose and Material Design 3
- **MVVM Architecture**: Clean architecture with ViewModels and repositories
- **Firebase Integration**: Authentication and Firestore database
- **Navigation**: Jetpack Navigation Compose for seamless navigation
- **Multi-Role Support**: Customer, Driver, and Admin interfaces

## 📱 Screens

- **Home Screen**: Service selection (Airport Transfer / Hotel Transfer)
- **Authentication**: Login and registration
- **Booking Form**: Transfer booking with details
- **Booking History**: View past bookings
- **Profile**: User profile management
- **Admin Dashboard**: Admin controls and overview
- **Admin Bookings**: Booking management for admins
- **Admin Drivers**: Driver management for admins

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM
- **Navigation**: Navigation Compose
- **Backend**: Firebase (Auth, Firestore)
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34
- **Coroutines**: For asynchronous operations

## 📦 Dependencies

- Firebase BOM
- Firebase Authentication
- Firebase Firestore
- Navigation Compose
- Lifecycle ViewModel
- Kotlin Coroutines
- Material Design 3

## 🔧 Setup Instructions

1. **Clone the project** or open the existing project in Android Studio

2. **Add Firebase Configuration**:
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project or use an existing one
   - Add an Android app with package name: `com.transigo.app`
   - Download the `google-services.json` file
   - **IMPORTANT**: Place the `google-services.json` file in the `app/` directory

3. **Firebase Setup**:
   - Enable Authentication with Email/Password
   - Create a Firestore database
   - Set up security rules as needed

4. **Deploy Firestore Security Rules**:
   - Install Firebase CLI: `npm install -g firebase-tools`
   - Login to Firebase: `firebase login`
   - Initialize Firebase in your project: `firebase init firestore`
   - Deploy rules: `firebase deploy --only firestore:rules`
   - The `firestore.rules` file contains secure access rules for users, bookings, and drivers

5. **Build and Run**:
   - Sync the project with Gradle files
   - Build and run the app on an emulator or device

## 📁 Project Structure

```
app/src/main/java/com/transigo/app/
├── MainActivity.kt
├── HomeScreen.kt
├── core/
│   ├── navigation/
│   │   ├── NavGraph.kt
│   │   └── NavigationRoutes.kt
│   └── ui/theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── auth/
│   └── AuthScreen.kt
├── booking/
│   ├── BookingFormScreen.kt
│   └── BookingHistoryScreen.kt
├── admin/
│   ├── AdminDashboardScreen.kt
│   ├── AdminBookingsScreen.kt
│   └── AdminDriversScreen.kt
├── driver/
│   └── DriverScreen.kt
├── profile/
│   └── ProfileScreen.kt
└── data/
    ├── model/
    │   ├── User.kt
    │   ├── Booking.kt
    │   └── Driver.kt
    └── repository/
        ├── UserRepository.kt
        └── BookingRepository.kt
```

## 🎨 Design Notes

- Uses Material Design 3 with custom TransiGo branding colors
- Placeholder for banner image (TODO: Add Freepik banner to drawable)
- Responsive design for different screen sizes
- Clean and intuitive user interface

## 🚧 Development Status

This is the initial project structure. The following features are ready for implementation:
- Basic navigation between screens
- Firebase configuration setup
- MVVM architecture foundation
- Data models and repositories
- UI theme and styling

## 📋 Next Steps

1. Implement authentication logic
2. Add form validation and data binding
3. Implement booking creation and management
4. Add real-time updates with Firestore listeners
5. Implement driver assignment logic
6. Add push notifications
7. Implement payment integration
8. Add maps integration for location picking
9. Implement rating and review system
10. Add comprehensive error handling

## ⚠️ Important Notes

- **Google Services Configuration**: Make sure to add `google-services.json` to the `app/` directory before building
- **Firebase Rules**: Configure Firestore security rules according to your requirements
- **API Keys**: Ensure all API keys are properly configured in your Firebase project

## 📄 License

This project is part of the TransiGo application development.
