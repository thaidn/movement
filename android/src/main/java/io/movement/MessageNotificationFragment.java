package io.movement;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.protobuf.InvalidProtocolBufferException;

import io.movement.message.MixMessageProtos;

public class MessageNotificationFragment extends Fragment {
	Button b;
    MixMessageProtos.MixMessage message;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.message_notification_fragment,
				container, false);
		Button notification = (Button) view.findViewById(R.id.view_message);
        final MessageNotificationFragment thisFragment = this;
		notification.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity container = (MainActivity) getActivity();
				container.showNewMessage();
			}
		});
		return view;
	}
}