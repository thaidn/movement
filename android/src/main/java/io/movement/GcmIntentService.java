package io.movement;

import java.io.FileNotFoundException;

import io.movement.message.ClientMessagingManagerInterface;
import io.movement.message.ClientMessagingManagerInterface.MessageSentCallback;
import io.movement.message.GCMClientMessagingManager;
import io.movement.message.MixMessageProtos;
import io.movement.message.MixMessageStorageManagerImpl;
import io.movement.message.MixMessageStorageManagerInterface;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gcm.server.Message;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	ClientMessagingManagerInterface messageManager;
	MixMessageStorageManagerImpl messageStorageManager;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMIntentService";

	@Override
	public void onCreate() {
		super.onCreate();
		messageManager = new GCMClientMessagingManager(getApplicationContext());
		try {
			messageStorageManager = new MixMessageStorageManagerImpl(
					getApplicationContext());
            messageStorageManager.open();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Error("Database file not found");
		}
	}

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (messageStorageManager != null) {
            messageStorageManager.close();
        }
    }

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                handleNewMessage(extras.getString("From"));
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

    private void handleNewMessage(String msg) {
        sendNotification("Received: " + msg);
        messageStorageManager.addStringMessage(msg);
        Intent alertNewMessage = new Intent(MainActivity.NOTIFY_NEW_MESSAGE);
        alertNewMessage.putExtra(MainActivity.MESSAGE_CONTENT, msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(
                alertNewMessage);
        Log.i(TAG, "Received: " + msg);
        //forwardMessageToOrange(m);
    }

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(String msg) {
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("GCM Notification")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
}