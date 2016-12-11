package io.hackathon.santaclaus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.adapter.ChildAdapter;
import io.hackathon.santaclaus.model.Result;
import io.hackathon.santaclaus.model.User;
import io.hackathon.santaclaus.util.Constants;
import io.hackathon.santaclaus.util.GetRequestTask;

public class ChildActivity extends AppCompatActivity {

    String parentId = "";
    String parentName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        // Get params
        Intent intent = getIntent();
        if (null != intent) {
            parentId = intent.getStringExtra("parentId");
            parentName = intent.getStringExtra("parentName");
        }

        // Call API
        String url = Constants.GET_CHILD_LIST_URL + "36";
        String result_str = "";
        try {
            result_str = new GetRequestTask().execute(url).get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
        Gson gson = new Gson();
        Type resultType = new TypeToken<Result>() {}.getType();
        Result resultObject = gson.fromJson(result_str, resultType);
        Type userType = new TypeToken<List<User>>() {}.getType();
        final List<User> userList = gson.fromJson(resultObject.getReturnObject().toString(), userType);

        // Init listView
//        final List<User> userList = new ArrayList<User>();
//        for (int i = 1; i < 3; i++) {
//            User user = new User();
//            user.setName("Name" + i);
//            userList.add(user);
//        }
        ListView listView = (ListView) findViewById(R.id.childList);
        ChildAdapter userAdapter = new ChildAdapter(this, userList);
        listView.setAdapter(userAdapter);

        // Select row
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = userList.get(position);
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                intent.putExtra("parentId", parentId);
                intent.putExtra("parentName", parentName);
                intent.putExtra("childId", selectedUser.getId() + "");
                intent.putExtra("childName", selectedUser.getName());
                startActivity(intent);
            }
        });
    }
}
