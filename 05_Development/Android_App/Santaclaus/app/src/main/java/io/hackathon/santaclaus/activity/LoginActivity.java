package io.hackathon.santaclaus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.model.User;
import io.hackathon.santaclaus.util.Constants;

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
        if (null != user) {
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
        User user = new User();
//        user.setId(1);
        user.setEmail("trinhnt@gmail.com");
        user.setName("Trinh");
        user.setBirthday("1983/05/12");
        user.setAvatarPath("trinhnt.jpg");
        user.setAddress("Tokyo-shi Mitato-ku");
        user.setType(Constants.USER_TYPE_PARENT);
        Date date = new Date();
        String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
        user.setCreateDate(str);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        return user;
    }

}
