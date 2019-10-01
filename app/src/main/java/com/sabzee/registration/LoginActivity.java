package com.sabzee.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String URL = "http://sabzishoppee.in/Vendor/index.php/";

    private EditText login;
    private EditText password;
    private Button btnlogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.et_login_email);
        password = (EditText) findViewById(R.id.et_login_password);
        btnlogin = (Button) findViewById(R.id.btnContinue);

       /* Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);*/

    }
    @Override
    public void onClick(View v) {



    }
}
