package io.hackathon.santaclaus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.adapter.MessageAdapter;
import io.hackathon.santaclaus.model.Message;
import io.hackathon.santaclaus.model.Result;
import io.hackathon.santaclaus.model.User;
import io.hackathon.santaclaus.util.Constants;
import io.hackathon.santaclaus.task.CreateMessageTask;
import io.hackathon.santaclaus.task.GetRequestTask;
import io.hackathon.santaclaus.util.Utils;

public class MessageActivity extends AppCompatActivity {

    String parentId = "";
    String childId = "";
    String url = "";
    String parentName = "";
    String childName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Get params
        Intent intent = getIntent();

        if (null != intent) {
            parentId = intent.getStringExtra("parentId");
            childId = intent.getStringExtra("childId");
            parentName = intent.getStringExtra("parentName");
            childName = intent.getStringExtra("childName");
        }

        // Call API
        if (null != parentId) {
            url = Constants.GET_MESSAGE_LIST_URL + childId + "/" + parentId;
        } else {
            url = Constants.GET_MESSAGE_LIST_URL + childId;
        }
        setListView();
    }

    /**
     * Set list view
     */
    private void setListView() {
        String result_str = "";
        try {
            result_str = new GetRequestTask().execute(url).get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
        Gson gson = new Gson();
        Type resultType = new TypeToken<Result>() {
        }.getType();
        Result resultObject = gson.fromJson(result_str, resultType);
        Type messageType = new TypeToken<List<Message>>() {
        }.getType();
        final List<Message> messageList = gson.fromJson(resultObject.getReturnObject().toString(), messageType);

        // Init listView
        for (int i = 0; i < messageList.size(); i++) {
            Message msg = messageList.get(i);
            if (msg.getIsChild() == Constants.IS_PARENT) { // Parent
                User user = new User();
                user.setName(parentName);
                msg.setUser(user);
            } else if (msg.getIsChild() == Constants.IS_CHILD) { // Child
                User user = new User();
                user.setName(childName);
                msg.setUser(user);
            }
        }
        ListView listView = (ListView) findViewById(R.id.messageList);
        MessageAdapter messageAdapter = new MessageAdapter(this, messageList);
//        messageAdapter.notifyDataSetChanged();
        ((BaseAdapter) messageAdapter).notifyDataSetChanged();
        listView.setAdapter(messageAdapter);
        listView.invalidateViews();
    }

    // Send button
    public void createMessage(View v) {
        EditText messageView = (EditText) findViewById(R.id.message);
        String message = messageView.getText().toString();
        if (null == message) {
            return;
        }
        Message msg = new Message();
        if (null == parentId) {
            // Call API
            String url = Constants.GET_PARENT_URL + childId;
            String result_str = "";
            try {
                result_str = new GetRequestTask().execute(url).get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
            Gson gson = new Gson();
            Type resultType = new TypeToken<Result>() {}.getType();
            Result resultObject = gson.fromJson(result_str, resultType);
            Type userType = new TypeToken<User>() {}.getType();
            final User resultUser = gson.fromJson(resultObject.getReturnObject().toString(), userType);
            msg.setParentId(resultUser.getId());
        }
        msg.setChildId(Utils.getIntegerValue(childId));
        msg.setContent(messageView.getText().toString());
        if (null != parentId) {
            msg.setIsChild(Constants.IS_PARENT);
        } else {
            msg.setIsChild(Constants.IS_CHILD);
        }
        final Gson gson = new Gson();
        final String message_string = gson.toJson(msg);

        // Call API
        String result_string = "";
        try {
            result_string = new CreateMessageTask().execute(message_string).get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
        Type resultType = new TypeToken<Result>() {}.getType();
        Result result = gson.fromJson(result_string, resultType);
        if (Constants.INSERT_RESULT_CODE_SUCCESS != result.getResultCode()) {
            return;
        }
        // Success
        Toast.makeText(getApplicationContext(), getString(R.string.message_save_msg),
                Toast.LENGTH_LONG).show();
        messageView.setText("");
        setListView();
    }
}
