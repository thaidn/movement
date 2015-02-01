package io.movement.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import io.movement.R;

/**
 * Created by nednguyen on 1/31/15.
 */
public class MessageGroupDisplayAdapter extends ArrayAdapter<String> {


    public MessageGroupDisplayAdapter(Context context, List<String> texts) {
        super(context, 0, texts);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get text this position
        String text = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.text_messages_group, parent, false);
        }
        // Lookup view for data population
        TextView messageContent = (TextView) convertView.findViewById(R.id.message_context);
        messageContent.setText(text);
        return convertView;
    }
}
