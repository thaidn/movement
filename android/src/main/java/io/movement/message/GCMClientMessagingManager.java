package io.movement.message;

import java.io.IOException;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

public final class GCMClientMessagingManager implements ClientMessagingManagerInterface {
	/**
	 * Substitute you own sender ID here. This is the project number you got
	 * from the API Console, as described in "Getting Started."
	 */
	private static final String SENDER_ID = "391574524304";

	/**
	 * Obtain this from the API Console.
	 */
	private static final String API_KEY = "AIzaSyCSnVtU2bPS-CEig0Wv7SDcX5p51G1OPHk";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	public static final String PROPERTY_REG_ID = "registration_id";
	public static final String APP_ID_FILE  = "gcm_app_id_file";
	private String clientId;
	private SharedPreferences sharedPreferences;
	private int currentAppVersion;
	private GoogleCloudMessaging gcm;

	public GCMClientMessagingManager(Context applicationContext) {
		this.sharedPreferences = applicationContext.getSharedPreferences(APP_ID_FILE, Context.MODE_PRIVATE);
		this.gcm = GoogleCloudMessaging.getInstance(applicationContext);
		this.currentAppVersion = getAppVersion(applicationContext);
		this.clientId = "";
		readClientIdFromPreferenceAndReregisterIfNeeded();
	}

	@Override
	public String getClientId() {
		if (clientId == null || clientId.isEmpty()) {
			readClientIdFromPreferenceAndReregisterIfNeeded();
		}
		return clientId;
	}

	@Override
	public void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					clientId = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + clientId;

					// You should send the registration ID to your server over
					// HTTP, so it can use GCM/HTTP or CCS to send messages to 
					// your app.
					sendRegistrationIdToBackend();

					// For this demo: we don't need to send it because the
					// device will send
					// upstream messages to a server that echo back the message
					// using the
					// 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(clientId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}

		}.execute(null, null, null);		
	}
	
	private void storeRegistrationId(String regId) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION,currentAppVersion);
		editor.commit();
	}
	
	private void readClientIdFromPreferenceAndReregisterIfNeeded() {
		int registeredVersion = sharedPreferences.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		clientId = sharedPreferences.getString(PROPERTY_REG_ID, "");
		if (registeredVersion != currentAppVersion || "".equals(clientId)) {
			registerInBackground();
		}
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use
	 * GCM/HTTP or CCS to send messages to your app. Not needed for this demo
	 * since the device sends upstream messages to a server that echoes back the
	 * message using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
		// Your implementation here.
	}

	@Override
	public void sendMessage(final String targetClientId, final Message message, final MessageSentCallback callback) {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					Sender sender = new Sender(API_KEY);
					Result result = sender.send(message, targetClientId, 5);
					if (result.getMessageId() != null) {
						String canonicalRegId = result
								.getCanonicalRegistrationId();
						if (canonicalRegId != null) {
							// same device has more than on registration ID:
							// update database
						}
					} else {
						String error = result.getErrorCodeName();
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
							// application has been removed from device -
							// unregister database
						}
						msg = error;
					}
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				callback.onMessageSent(msg);
			}
		}.execute(null, null, null);
	}
	
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
	
}
