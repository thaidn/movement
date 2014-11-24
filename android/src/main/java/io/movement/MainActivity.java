package io.movement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.movement.backend.registration.Registration;
import io.movement.backend.registration.model.RegistrationRecord;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gcm.server.Message;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.protobuf.ByteString;

import java.io.FileNotFoundException;

/**
 * Main UI for the demo app.
 */
public class MainActivity extends FragmentActivity {

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	static final String NOTIFY_NEW_MESSAGE = "notify-new-message";
	static final String MESSAGE_CONTENT = "message-content";

	/**
	 * Alice's RegId.
	 */
	static String WHITE_DEVICE_ID = "APA91bFqdTtlRPbE8QfbJAV9PUFN9gv0HbkidaZeXyldcAFxtq6BBhyTErnDJSulMbzzPUyQLimHT5ZKg0-jM_A-4_0qnx950kcg_Yz2f1kdW6zMA37wVIQaGmuMYgfVq2HD-yJra3UdwG49roOziERmx6xpSOj9wg";

    /**
	 * Bob's RegId.
	 */
	static String BLACK_DEVICE_ID = "APA91bGmAXPyOnp0lz7t3d2dTJ4WhF1JVI0Ybijc9mezyULeUhTvowIc-jn_XDQt2jHpPAQG85RZr3Jlv4pK0hE6hgwPB876Wf4n75HeP-3OLChZqLnca0Gar00KqfuO9lyWx11wknQ8MewJWsIVuMxHfuyStD18vQ";

	/**
	 * Orange's RegId.
	 */
	static String ORANGE_DEVICE_ID = "APA91bEg-_qwPrToZCtk7sEhRYCjGF-yqbifI4Z3IJwdtAC367VgNIViEEp2ofo2NIKWBunkiz7vongcIQl0h4uuEPbN2NX7H9hJUlbVXEYlBcxZB4jq7SX-h6rXq-AC0RmuQ-fY_1dgp5gbetCqTYaKBc6FH2YHmw";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "Movement MainActivity";

	TextView mDisplay;
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
            MixMessage mixMsg = MixMessage.newBuilder()
                .setPayload(ByteString.copyFromUtf8("Here I go"))
                .build();
            Message message = new Message.Builder()
                .addData("From", new String(mixMsg.toByteArray()))
                .build();
            Log.i(TAG, "Ping " + BLACK_DEVICE_ID);
            messageManager.sendMessage(BLACK_DEVICE_ID, message, new MessageSentCallback () {
                public void onMessageSent(String msg) {}
            });
		} else if (view == findViewById(R.id.clear)) {
			mDisplay.setText("");
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
