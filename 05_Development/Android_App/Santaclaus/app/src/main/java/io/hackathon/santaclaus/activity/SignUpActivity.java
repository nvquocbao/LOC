package io.hackathon.santaclaus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.hackathon.santaclaus.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /**
     * Sign Up
     *
     * @param v
     */
    public void signup(View v) {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
}
