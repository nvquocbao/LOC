package io.hackathon.santaclaus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.model.User;
import io.hackathon.santaclaus.util.Constants;
import io.hackathon.santaclaus.util.CheckLoginTask;
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
                intent.putExtra("parentId", user.getId()+"");
                intent.putExtra("parentName", user.getName());
                startActivity(intent);
            }
        } else {
            TextView errorMsg = (TextView) findViewById(R.id.error_msg);
            errorMsg.setText("ユーザ名、パスワードが間違っています。あるいはまだ登録されていない。");
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
        password = Utils.cryptWithMD5(password);
        final User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        final Gson gson = new Gson();
        final String json = gson.toJson(user);
        // Call API
        String result_str = "";
        try {
            result_str = new CheckLoginTask().execute(json).get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
        Type userType = new TypeToken<User>() {}.getType();
        User resultObject = gson.fromJson(result_str, userType);
        user.setId(resultObject.getId());
        user.setType(resultObject.getType());
        user.setName(resultObject.getName());
        return user;
    }

}
