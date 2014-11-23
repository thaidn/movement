package io.movement.message;

import io.movement.MixMessageProtos.MixMessage;

import java.util.Map;

public interface MixMessageStorageManagerInterface {
	/**
	 * 
	 */
	public void open();

	/**
	 * Close
	 */
	public void close();

	/**
	 * Add MixMessage to storage.
	 * 
	 * @param m
	 *            a MixMessage instance
	 * @return messageId if m is inserted successfully. This id can be used to
	 *         later delete the message m. If the transaction fails -1 is
	 *         returned.
	 */
	Long addMessage(MixMessage m);

	/**
	 * Delete a message with messageId.
	 * 
	 * @param messageId
	 */
	void deleteMessage(Long messageId);

	/**
	 * 
	 * @return a map from messageIds -> messages
	 */
	Map<Long, MixMessage> loadAllMessages();
}
