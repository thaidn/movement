package io.movement;

import java.util.Map;

import io.movement.message.ClientMessagingManagerInterface;
import io.movement.message.ClientMessagingManagerInterface.MessageSentCallback;
import io.movement.message.GCMClientMessagingManager;
import io.movement.message.MixMessageProtos.MixMessage;
import io.movement.message.MixMessageStorageManagerImpl;
import io.movement.message.MixMessageStorageManagerInterface;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.FileNotFoundException;

/**
 * Main UI for the demo app.
 */
public class MainActivity extends FragmentActivity {

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	static final String NOTIFY_NEW_MESSAGE = "notify-new-message";
	static final String MESSAGE_CONTENT = "message-content";
	static final String TAG = "Movement MainActivity";

	TextView mDisplay;
    EditText mChatBox;
	ClientMessagingManagerInterface messageManager;
	Context context;
    MessageNotificationFragment newMessageFragment;
    MixMessageStorageManagerInterface messageStorageManager;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();
			Log.i(TAG, extras.toString());
			maybeCreateNotificationFragment();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDisplay = (TextView) findViewById(R.id.display);
        mChatBox = (EditText) findViewById(R.id.chatBox);
		context = getApplicationContext();
		checkPlayServices();

		messageManager = new GCMClientMessagingManager(context);
        try {
            messageStorageManager = new MixMessageStorageManagerImpl(getApplicationContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            finish();
        }
        // Add a broadcast receive that displays new message notification.
		LocalBroadcastManager bManager = LocalBroadcastManager
				.getInstance(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(NOTIFY_NEW_MESSAGE);
		bManager.registerReceiver(receiver, filter);
	}

	@Override
	protected void onResume() {
		super.onResume();
        messageStorageManager.open();
        if (messageStorageManager.hasMessages()) {
            maybeCreateNotificationFragment();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        messageStorageManager.close();
    }

    /**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private void checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
		}
	}

	private void maybeCreateNotificationFragment() {
        if (newMessageFragment != null) {
            return;
        }
        newMessageFragment = new MessageNotificationFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.main_layout, newMessageFragment);
		ft.commit();
	}

	void showNewMessage() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove(newMessageFragment).commit();
        newMessageFragment = null;
        Map<Long, MixMessage> messages = messageStorageManager.loadAllMessages();
        for (Map.Entry<Long, MixMessage> entry : messages.entrySet()) {
            Long messageId = entry.getKey();
            MixMessage message = entry.getValue();
            mDisplay.append(message.getPayload().toStringUtf8());
            messageStorageManager.deleteMessage(messageId);
        }
	}

	// Send an upstream message.
	public void onClick(final View view) {
		if (view == findViewById(R.id.ping)) {
            messageManager.sendMessage(mChatBox.getText().toString(), new MessageSentCallback () {
                public void onMessageSent(String msg) {}
            });
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LocalBroadcastManager bManager = LocalBroadcastManager
				.getInstance(this);
		bManager.unregisterReceiver(receiver);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// No call for super(). Bug on API Level > 11.
	}
}
