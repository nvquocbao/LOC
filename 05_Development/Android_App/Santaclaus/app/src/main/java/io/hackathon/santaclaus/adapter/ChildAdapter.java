package io.hackathon.santaclaus.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.model.User;

/**
 * Created by trinhnt on 2016/12/10.
 */

public class ChildAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final List<User> userList;

    public ChildAdapter(Context context, List<User> userList) {
        super(context, R.layout.child_list, userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.child_list, parent, false);
        User user = userList.get(position);
        ImageView avatarView = (ImageView) rowView.findViewById(R.id.avatar);
        /*
        Uri uri = Uri.parse(user.getAvatarPath());
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            avatarView.setImageBitmap(bitmap);
        } catch (IOException e) {
        }
        */
        TextView nameView = (TextView) rowView.findViewById(R.id.name);
        nameView.setText(user.getName());
        return rowView;
    }
}
