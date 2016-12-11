package io.hackathon.santaclaus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.adapter.MessageAdapter;
import io.hackathon.santaclaus.model.Message;
import io.hackathon.santaclaus.model.User;
import io.hackathon.santaclaus.util.Constants;
import io.hackathon.santaclaus.util.GetRequestTask;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        String parentId = "";
        if (null != intent) {
            parentId = intent.getStringExtra("parentId");
        }

        // Call API
//        String url = Constants.GET_MESSAGE_LIST_URL + parentId + childId;
        String url = Constants.GET_MESSAGE_LIST_URL + "";
        String result_str = "";
        try {
            result_str = new GetRequestTask().execute(url).get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }

        // Init listView
        // TODO: get message list by child_id, parent_id
        List<Message> messageList = new ArrayList<Message>();
        for (int i=1; i<10; i++) {
            Message msg = new Message();
            User user = new User();
            user.setName("Name" +i);
            msg.setChildId(i);
            msg.setContent("Content "+i);
            msg.setUser(user);
            messageList.add(msg);
        }
        ListView listView = (ListView) findViewById(R.id.messageList);
        MessageAdapter messageAdapter = new MessageAdapter(this, messageList);
        listView.setAdapter(messageAdapter);
    }
}
