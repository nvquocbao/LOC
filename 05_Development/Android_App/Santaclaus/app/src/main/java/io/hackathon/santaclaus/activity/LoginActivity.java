package io.hackathon.santaclaus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.model.Result;
import io.hackathon.santaclaus.model.User;
import io.hackathon.santaclaus.util.Constants;
import io.hackathon.santaclaus.util.Utils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * SignUp
     *
     * @param v
     */
    public void signup(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Login
     *
     * @param v
     */
    public void login(View v) {
        TextView emailView = (TextView) findViewById(R.id.email);
        TextView passwordView = (TextView) findViewById(R.id.password);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        User user = isLogined(email, password);
        if (user.getId() != 0) {
            if (Constants.USER_TYPE_CHILD == user.getType()) {
                // Go to Message
                Intent intent = new Intent(this, MessageActivity.class);
                startActivity(intent);
            } else if (Constants.USER_TYPE_PARENT == user.getType()) {
                // Go to Child
                Intent intent = new Intent(this, ChildActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * Check login
     *
     * @param email
     * @param password
     * @return
     */
    private User isLogined(String email, String password) {
        final boolean flag = true;
        try {
       //     password = Utils.encrypt(password);
        } catch (Exception e) {
        }
        final User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        final Gson gson = new Gson();
        final String json = gson.toJson(user);
        // Call API
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    String result_string = Utils.makePOSTRequest(Constants.CHECK_LOGIN_URL, json);
                    Type resultType = new TypeToken<Result>() {}.getType();
                    Result result = gson.fromJson(result_string, resultType);
                    if (Constants.INSERT_RESULT_CODE_SUCCESS != result.getResultCode()) {
                        user.setId(0);
                        return;
                    }
                    LinkedTreeMap<String, String> yourMap = (LinkedTreeMap<String, String>) result.getReturnObject();
                    JsonObject jsonObject = gson.toJsonTree(yourMap).getAsJsonObject();
                    Type userType = new TypeToken<User>() {}.getType();
                    User resultObject = gson.fromJson(jsonObject.toString(), userType);
                    user.setId(resultObject.getId());
                    user.setType(resultObject.getType());
                } catch (Exception e) {
                }
            }
        });
        thread.start();
        return user;
    }

}
