package io.movement.message;

import com.google.android.gcm.server.Message;


public interface ClientMessagingManagerInterface {
	
	interface MessageSentCallback {
		void onMessageSent(String msg);
	}
	
	/**
	 * Gets the current registration ID for application if there is one.
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	public String getRegId();
	
	/**
	 * Registers the application asynchronously.
	 * Stores the registration ID and the app versionCode in the application's
	 * shared preferences.
	 */
	void registerInBackground();
	
	void sendMessage(String message, MessageSentCallback callback);
}
