package com.example.admin.runmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login, register;
    private EditText username, password;
    private UserDBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.btnLogin);
        register=(Button)findViewById(R.id.btnReg);
        username=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.textLoginPass);
        username.requestFocus();
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        db=new UserDBHelper(this);
    }

    @Override
    public void onClick(View v) {
        int num=v.getId();
        if (R.id.btnLogin==num){
            //Query Database and welcome user
            String user=username.getText().toString();
            String pass=password.getText().toString();

            if(db.CheckUser(user,pass)==true){
                Toast.makeText(this, "Welcome back, "+user, Toast.LENGTH_LONG).show(); //Welcome message
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Unsuccessful Login: Try Again ", Toast.LENGTH_LONG).show();
            }


        }
        else if (R.id.btnReg==num){
            //Go to registration
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
}
