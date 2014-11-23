package io.movement;

import io.movement.message.ClientMessagingManagerInterface;
import io.movement.message.ClientMessagingManagerInterface.MessageSentCallback;
import io.movement.message.GCMClientMessagingManager;
import io.movement.message.MixMessageProtos.MixMessage;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gcm.server.Message;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Main UI for the demo app.
 */
public class MainActivity extends FragmentActivity {

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	static final String NOTIFY_NEW_MESSAGE = "notify-new-message";
	static final String MESSAGE_CONTENT = "message-content";

	/**
	 * Alice's RegId.
	 */
	static String WHITE_DEVICE_ID = "APA91bE_rksNUQ99HRcGHn8VqEarsckBmbTtL8Gg0fujTFI4bmghPeLMBCIUnYpAd5pESXF7sPDq0vNGvboJxAS0HSQYtQ2xVjdVp3BLztx4ugwJGi1_LQYuMwxoG6fq3kjxgNxQQH1Lc1IguPg3T8KMWJFoRLK6nA";
	/**
	 * Bob's RegId.
	 */
	static String BLACK_DEVICE_ID = "APA91bFFNVhDgGwj6f2_BCW2-WSbMwG8fLbBG0hdcpJ_QCO_8FXvdMqgKw3JWaaLQIehodt8KyyRLD3B90AbVE5dChJ2rr60Qyae0On4x6BHDbtHH9tnmvz7I6hyFwWRI14GqwHEvUBTVFev-7_g-m4WCNpcH9Brfg";

	/**
	 * Orange's RegId.
	 */
	static String ORANGE_DEVICE_ID = "APA91bEg-_qwPrToZCtk7sEhRYCjGF-yqbifI4Z3IJwdtAC367VgNIViEEp2ofo2NIKWBunkiz7vongcIQl0h4uuEPbN2NX7H9hJUlbVXEYlBcxZB4jq7SX-h6rXq-AC0RmuQ-fY_1dgp5gbetCqTYaKBc6FH2YHmw";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "GCM Demo";

	TextView mDisplay;
	ClientMessagingManagerInterface messageManager;
	Context context;
	Fragment newMessageFragment;
	MixMessage newMessage;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();
			Log.i(TAG, extras.toString());
			try {
				newMessage = MixMessage.parseFrom(intent.getStringExtra(
						MESSAGE_CONTENT).getBytes());
				displayNewMessageNotification();
			} catch (InvalidProtocolBufferException e) {
				;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mDisplay = (TextView) findViewById(R.id.display);
		context = getApplicationContext();
		// Check device for Play Services APK. If check succeeds, proceed with
		// GCM registration.
		if (checkPlayServices()) {
			messageManager = new GCMClientMessagingManager(context);
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
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
		// Check device for Play Services APK.
		checkPlayServices();
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
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
			return false;
		}
		return true;
	}

	private void displayNewMessageNotification() {
		newMessageFragment = new MessageNotificationFragment();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.main_layout, newMessageFragment);
		ft.commit();
	}

	void showNewMessage() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove(newMessageFragment).commit();
		mDisplay.append("\n\nNew message:\n"
				+ newMessage.getPayload().toStringUtf8());
	}

	// Send an upstream message.
	public void onClick(final View view) {

		if (view == findViewById(R.id.ping)) {
			while (messageManager.getClientId().equals("")) {
			}
			String myClientId = messageManager.getClientId();
			Log.i(TAG, "My client ID is " + myClientId);
			Message.Builder messageBuilder = new Message.Builder();
			String targetClientId = BLACK_DEVICE_ID;
			if (!myClientId.equals(WHITE_DEVICE_ID)) {
				targetClientId = WHITE_DEVICE_ID;
			}
			MixMessage mixMsg = MixMessage.newBuilder()
				.setPayload(ByteString.copyFromUtf8("Here I go"))
				.build();
			messageBuilder.addData("From", mixMsg.toString());
			messageManager.sendMessage(targetClientId, messageBuilder.build(), new MessageSentCallback () {
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
