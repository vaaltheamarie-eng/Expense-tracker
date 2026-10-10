# Expense Tracker - Android App

A modern Android expense tracking application with secure authentication and two-factor verification.

## Features

✅ **Splash Screen** - 2-second logo display on app launch
✅ **Authentication** - Email/password login and account creation
✅ **Two-Factor Authentication (2FA)** - 6-digit OTP sent via email
✅ **Session Persistence** - User stays logged in after app restart
✅ **Material Design 3** - Modern, responsive UI
✅ **Firebase Integration** - Secure backend with Firestore
✅ **Coroutines** - Asynchronous operations
✅ **Encrypted Storage** - EncryptedSharedPreferences for secure data

## Project Structure

```
ExpenseTracker/
├── app/
│   ├── src/main/
│   │   ├── java/com/expense_tracker/app/
│   │   │   ├── ExpenseTrackerApp.kt
│   │   │   ├── data/
│   │   │   │   └── AuthManager.kt
│   │   │   └── ui/
│   │   │       ├── splash/
│   │   │       ├── auth/
│   │   │       └── dashboard/
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   └── drawable/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── README.md
```

## Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Java 17
- Firebase project with authentication enabled

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/vaaltheamarie-eng/Expense-tracker.git
   cd Expense-tracker
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Click "Open an Existing Project"
   - Select the repository folder
   - Wait for Gradle to sync

3. **Configure Firebase**
   - Go to [Firebase Console](https://console.firebase.google.com)
   - Create a new project or select existing
   - Add Android app with package name: `com.expense_tracker.app`
   - Download `google-services.json`
   - Place it in `app/google-services.json`

4. **Enable Firebase Authentication**
   - In Firebase Console → Authentication
   - Enable "Email/Password" sign-in method

5. **Create Firestore Database**
   - In Firebase Console → Firestore Database
   - Create database in production mode
   - Set security rules to allow authenticated users

6. **Deploy Cloud Functions (Optional but Recommended)**
   - Install Firebase CLI: `npm install -g firebase-tools`
   - Login: `firebase login`
   - Deploy functions for email OTP sending

7. **Run the App**
   - Connect Android device or start emulator
   - Click "Run" or press Shift+F10

## App Flow

### First Launch
1. Splash screen displays for 2 seconds
2. App checks authentication status
3. If logged in → Dashboard
4. If not → Login/Sign Up screen

### Login Flow
1. Enter email and password
2. Credentials verified via Firebase Auth
3. 2FA code sent to email
4. Enter 6-digit OTP
5. Access Dashboard

### Signup Flow
1. Enter email, password, confirm password
2. Account created via Firebase Auth
3. User profile stored in Firestore
4. 2FA code sent to email
5. Enter 6-digit OTP
6. Access Dashboard

### Session Persistence
- Firebase Auth automatically maintains user session
- User credentials encrypted and stored securely
- User stays logged in until explicitly logging out
- No re-authentication required on app restart

## Technologies Used

- **Kotlin** - Modern Android development language
- **Firebase Authentication** - Secure email/password auth
- **Firebase Firestore** - Cloud database for OTP storage
- **Firebase Cloud Functions** - Backend for sending emails
- **Material Design 3** - UI components and theming
- **Coroutines** - Async/await operations
- **ViewBinding** - Type-safe view binding
- **Encrypted SharedPreferences** - Secure local storage

## Security Features

✅ Email/password authentication via Firebase
✅ Two-factor authentication with email OTP
✅ Encrypted credential storage
✅ 10-minute OTP expiration
✅ Password validation (minimum 8 characters)
✅ Email format validation
✅ Secure session management
✅ No hardcoded credentials

## Next Steps

After authentication is working:
1. Build expense dashboard with list of transactions
2. Add expense creation form
3. Implement expense categories
4. Add budget tracking
5. Create expense analytics/reports
6. Implement data export (CSV/PDF)
7. Add push notifications for budget alerts

## Troubleshooting

### Gradle Build Fails
- Ensure you're using Java 17
- Run `./gradlew clean` then `./gradlew build`
- Check that `google-services.json` is in `app/` directory

### Firebase Connection Error
- Verify internet connection
- Check Firebase project configuration
- Ensure Firebase services are enabled
- Confirm `google-services.json` is correct

### OTP Not Sending
- Check Firestore rules allow document creation
- Verify Cloud Function is deployed
- Check email configuration in Cloud Function
- Monitor Cloud Function logs in Firebase Console

## License

Private repository - All rights reserved

## Support

For issues or questions:
- Check Firebase documentation: https://firebase.google.com/docs
- Android documentation: https://developer.android.com
- Kotlin documentation: https://kotlinlang.org/docs
