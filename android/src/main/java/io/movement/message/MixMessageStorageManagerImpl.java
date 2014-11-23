package io.movement.message;

import io.movement.MixMessageProtos.MixMessage;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.protobuf.InvalidProtocolBufferException;

public class MixMessageStorageManagerImpl implements
		MixMessageStorageManagerInterface {

	static private class MessageDatabaseHelper extends SQLiteOpenHelper {

		// WARNING: IF YOU UPDATE THE DATABASE_VERSION, YOU HAVE TO ADD
		// MIGRATION LOGIC IN
		// onUpgrade() method.
		private static final int DATABASE_VERSION = 2;

		private static final String DATABASE_MESSAGES = "MESSAGES_DATABASE";
		private static final String TABLE_MESSAGES = "messages_table";
		private static final String COLUMN_ID = "id";
		private static final String COLUMN_MESSAGE = "string_message";
		private static final int COLUMN_ID_INDEX = 0;
		private static final int COLUMN_MESSAGE_INDEX = 1;
		private static final String DATABASE_CREATE = "create table "
				+ TABLE_MESSAGES + "(" + COLUMN_ID
				+ " integer primary key autoincrement, " + COLUMN_MESSAGE
				+ " text not null);";

		MessageDatabaseHelper(Context context) {
			super(context, DATABASE_MESSAGES, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO: Implement the upgrade logic if we ever update the
			// DATABASE_VERSION
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
		}
	}

	public static final String MESSAGES_DATA_FILE = "messages_data_file";
	public static final String MESSAGES_KEY = "messages-key";
	private MessageDatabaseHelper dbHelper;
	private SQLiteDatabase database;
	private String[] allColumns = { MessageDatabaseHelper.COLUMN_ID,
			MessageDatabaseHelper.COLUMN_MESSAGE };

	public MixMessageStorageManagerImpl(Context applicationContext)
			throws FileNotFoundException {
		this.dbHelper = new MessageDatabaseHelper(applicationContext);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	@Override
	public Long addMessage(MixMessage m) {
		return this.addStringMessage(m.toString());
	}

	public Long addStringMessage(String m) {
		ContentValues values = new ContentValues();
		values.put(MessageDatabaseHelper.COLUMN_MESSAGE, m);
		return database.insert(MessageDatabaseHelper.TABLE_MESSAGES, null,
				values);
	}

	private MixMessage getMessageFromCursor(Cursor cursor) {
		try {
			return MixMessage.parseFrom(cursor.getString(
					MessageDatabaseHelper.COLUMN_MESSAGE_INDEX).getBytes());
		} catch (InvalidProtocolBufferException e) {
			return null;
		}
	}

	private Long getRowIdFromCursor(Cursor cursor) {
		return cursor.getLong(MessageDatabaseHelper.COLUMN_ID_INDEX);
	}

	@SuppressLint("UseSparseArrays")
	@Override
	public Map<Long, MixMessage> loadAllMessages() {
		Map<Long, MixMessage> messages_map = new HashMap<Long, MixMessage>();

		Cursor cursor = database.query(MessageDatabaseHelper.TABLE_MESSAGES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Long rowId = getRowIdFromCursor(cursor);
			MixMessage message = getMessageFromCursor(cursor);
			assert message != null;
			messages_map.put(rowId, message);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return messages_map;
	}

	public void deleteMessage(Long messageId) {
		assert messageId >= 0;
		database.delete(MessageDatabaseHelper.TABLE_MESSAGES,
				MessageDatabaseHelper.COLUMN_ID + " = " + messageId, null);
	}
}
