package io.movement.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.movement.R;

/**
 * Created by nednguyen on 1/31/15.
 */
public class MessageGroupDisplayAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;

    public MessageGroupDisplayAdapter(Context context, List<String> texts) {
        super(context, 0, texts);
        mInflater = LayoutInflater.from(getContext());
    }

    private class ViewHolder {
        public TextView textview;
        public boolean newViewAnimationExecuted = false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        // Get text this position
        String text = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.text_messages_group, null);
            holder.textview = (TextView) convertView.findViewById(R.id.message_context);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textview.setText(text);
        if (!holder.newViewAnimationExecuted) {
            Animation animation = AnimationUtils.loadAnimation(
                    getContext(), R.anim.slide_left_to_right);
            animation.setDuration(200);
            convertView.startAnimation(animation);
            animation = null;
            holder.newViewAnimationExecuted = true;
        }
        return convertView;
    }
}
