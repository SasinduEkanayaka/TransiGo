// Firebase Cloud Function for sending push notifications when booking status changes
// This function listens to Firestore changes and sends push notifications to users

import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

// Initialize Firebase Admin SDK
// TODO: Add your service account key file to your project
// Place the service account JSON file in your functions folder and uncomment the next line:
// const serviceAccount = require('./path-to-your-service-account-key.json');

admin.initializeApp({
  // TODO: Uncomment and configure with your service account
  // credential: admin.credential.cert(serviceAccount),
  // If you're deploying to Firebase, you can use the default credential instead:
  credential: admin.credential.applicationDefault(),
});

const db = admin.firestore();
const messaging = admin.messaging();

/**
 * Cloud Function that triggers when a booking document is updated
 * Sends a push notification to the user when booking status changes
 */
export const sendBookingNotification = functions.firestore
  .document('bookings/{bookingId}')
  .onUpdate(async (change, context) => {
    try {
      const bookingId = context.params.bookingId;
      const beforeData = change.before.data();
      const afterData = change.after.data();
      
      // Check if status actually changed
      if (beforeData.status === afterData.status) {
        console.log('Booking status unchanged, skipping notification');
        return null;
      }
      
      const userId = afterData.userId;
      const newStatus = afterData.status;
      
      if (!userId || !newStatus) {
        console.log('Missing userId or status, skipping notification');
        return null;
      }
      
      // Get user document to check notification preferences and FCM token
      const userDoc = await db.collection('users').doc(userId).get();
      
      if (!userDoc.exists) {
        console.log(`User ${userId} not found`);
        return null;
      }
      
      const userData = userDoc.data();
      
      // Check if user has notifications enabled (default to true if not set)
      if (userData?.notificationsEnabled === false) {
        console.log(`Notifications disabled for user ${userId}`);
        return null;
      }
      
      // Check if user has an FCM token
      const fcmToken = userData?.fcmToken;
      if (!fcmToken) {
        console.log(`No FCM token for user ${userId}`);
        return null;
      }
      
      // Prepare notification content
      const notification = getNotificationContent(newStatus);
      const message = {
        notification: {
          title: notification.title,
          body: notification.body,
        },
        data: {
          bookingId: bookingId,
          status: newStatus,
          type: 'booking_update'
        },
        token: fcmToken,
        android: {
          notification: {
            channelId: 'booking_notifications',
            priority: 'high' as const,
            defaultSound: true,
            defaultVibrateTimings: true,
          }
        },
        apns: {
          payload: {
            aps: {
              sound: 'default',
              badge: 1,
            }
          }
        }
      };
      
      // Send the notification
      const response = await messaging.send(message);
      console.log('Successfully sent notification:', response);
      
      // Log the notification in Firestore for debugging/history
      await db.collection('notifications').add({
        userId,
        bookingId,
        status: newStatus,
        title: notification.title,
        body: notification.body,
        sentAt: admin.firestore.FieldValue.serverTimestamp(),
        fcmResponse: response,
      });
      
      return response;
      
    } catch (error) {
      console.error('Error sending notification:', error);
      
      // Log the error in Firestore for debugging
      await db.collection('notification_errors').add({
        bookingId: context.params.bookingId,
        error: error.message,
        timestamp: admin.firestore.FieldValue.serverTimestamp(),
      });
      
      throw error;
    }
  });

/**
 * Helper function to get notification title and body based on booking status
 */
function getNotificationContent(status: string): { title: string; body: string } {
  switch (status.toUpperCase()) {
    case 'APPROVED':
      return {
        title: 'Booking Approved! 🎉',
        body: 'Your booking has been approved. Get ready for your trip!'
      };
    case 'REJECTED':
      return {
        title: 'Booking Update',
        body: 'Your booking has been rejected. Please contact support if you have questions.'
      };
    case 'COMPLETED':
      return {
        title: 'Trip Completed',
        body: 'Your trip has been completed. Thank you for using TransiGo!'
      };
    case 'CANCELLED':
      return {
        title: 'Booking Cancelled',
        body: 'Your booking has been cancelled.'
      };
    case 'CONFIRMED':
      return {
        title: 'Booking Confirmed',
        body: 'Your booking has been confirmed. We\'ll notify you when a driver is assigned.'
      };
    case 'IN_PROGRESS':
      return {
        title: 'Trip Started',
        body: 'Your trip is now in progress. Have a safe journey!'
      };
    default:
      return {
        title: 'Booking Update',
        body: `Your booking status has been updated to ${status}.`
      };
  }
}

/**
 * Optional: Function to handle FCM token updates
 * Call this when a user's FCM token changes
 */
export const updateFcmToken = functions.https.onCall(async (data, context) => {
  // Verify user is authenticated
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'User must be authenticated');
  }
  
  const { token } = data;
  const userId = context.auth.uid;
  
  if (!token) {
    throw new functions.https.HttpsError('invalid-argument', 'FCM token is required');
  }
  
  try {
    await db.collection('users').doc(userId).update({
      fcmToken: token,
      fcmTokenUpdatedAt: admin.firestore.FieldValue.serverTimestamp(),
    });
    
    return { success: true };
  } catch (error) {
    console.error('Error updating FCM token:', error);
    throw new functions.https.HttpsError('internal', 'Failed to update FCM token');
  }
});

/*
DEPLOYMENT INSTRUCTIONS:

1. Install Firebase CLI if you haven't already:
   npm install -g firebase-tools

2. Initialize Firebase Functions in your project root:
   firebase init functions
   
3. Choose your Firebase project
   
4. Install dependencies:
   cd functions
   npm install firebase-functions firebase-admin

5. Replace the generated index.ts with this code

6. Configure service account (IMPORTANT):
   Option A: Download service account key from Firebase Console > Project Settings > Service Accounts
   - Save the JSON file to your functions folder
   - Update the serviceAccount import path above
   
   Option B: Use default credentials (recommended for production):
   - Keep the admin.credential.applicationDefault() line
   - Ensure your deployment environment has proper permissions

7. Deploy the functions:
   firebase deploy --only functions
   
8. Test the function:
   - Create a booking in your app
   - Update the booking status in Firebase Console
   - Check the function logs: firebase functions:log

9. Monitor function performance:
   - Firebase Console > Functions tab
   - Check invocation count, errors, and execution time

SECURITY RULES:
Make sure your Firestore security rules allow the Cloud Function to read user data:

rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read: if request.auth != null;
    }
    match /bookings/{bookingId} {
      allow read, write: if request.auth != null;
    }
  }
}

DEBUGGING:
- Check function logs: firebase functions:log
- Monitor function execution in Firebase Console
- Check notification_errors collection for failed notifications
- Test with Firebase Console's Cloud Messaging composer
*/
