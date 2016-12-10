package io.hackathon.santaclaus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.model.Message;

/**
 * Created by trinhnt on 2016/12/10.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private final Context context;
    private final List<Message> messageList;

    public MessageAdapter(Context context, List<Message> messageList) {
        super(context, R.layout.message_list, messageList);
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.message_list, parent, false);
        Message message = messageList.get(position);
        ImageView avatarView = (ImageView) rowView.findViewById(R.id.avatar);
        // TODO: message.getUser().getAvatar_path()
        avatarView.setImageResource(R.drawable.user_avatar);
        TextView nameView = (TextView) rowView.findViewById(R.id.name);
        nameView.setText(message.getUser().getName());
        TextView contentView = (TextView) rowView.findViewById(R.id.content);
        contentView.setText(message.getContent());
        return rowView;
    }
}
