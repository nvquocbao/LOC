package io.hackathon.santaclaus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
        // Call isLogined
        TextView emailView = (TextView) findViewById(R.id.email);
        TextView passwordView = (TextView) findViewById(R.id.password);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        boolean flag = isLogined(email, password);
        if (flag) {
            // Go to Message
            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Check login
     *
     * @param email
     * @param password
     * @return
     */
    private boolean isLogined(String email, String password) {
        return true;
    }

}
