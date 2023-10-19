package com.example.alarmapp.dbAlarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alarmapp.R;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUserName, edtPassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAlarmHelper db = new DBAlarmHelper(LoginActivity.this, "mydb", null, 1);
                User newUser = new User(1, "John", "password123");
                db.addUser(newUser);
                String username = edtUserName.getText().toString().trim();
                String password = edtPassWord.getText().toString().trim();
                if (db.checkLogin(username, password)){
                    Intent intent = new Intent(LoginActivity.this, SuccessLoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}