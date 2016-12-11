package io.hackathon.santaclaus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.adapter.ChildAdapter;
import io.hackathon.santaclaus.model.Result;
import io.hackathon.santaclaus.model.User;
import io.hackathon.santaclaus.util.Constants;
import io.hackathon.santaclaus.util.Utils;

public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);


        // Call API
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    String json = Utils.makeGETRequest(Constants.GET_CHILD_LIST_URL + "1");
                    String aaa = "1";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        // Init listView
        final List<User> userList = new ArrayList<User>();
        for (int i = 1; i < 3; i++) {
            User user = new User();
            user.setName("Name" + i);
            userList.add(user);
        }
        ListView listView = (ListView) findViewById(R.id.childList);
        ChildAdapter userAdapter = new ChildAdapter(this, userList);
        listView.setAdapter(userAdapter);

        // Select row
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = userList.get(position);
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                intent.putExtra("childId", selectedUser.getId() + "");
                startActivity(intent);
            }
        });
    }
}
