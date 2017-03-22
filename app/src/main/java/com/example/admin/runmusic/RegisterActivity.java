package com.example.admin.runmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, email, password1, password2;
    private Button back, register;
    private UserDBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=(EditText)findViewById(R.id.txtUser);
        email=(EditText)findViewById(R.id.textEmail);
        password1=(EditText)findViewById(R.id.textPass1);
        password2=(EditText)findViewById(R.id.textPass2);
        back=(Button)findViewById(R.id.btnBack);
        register=(Button)findViewById(R.id.buttontnReg);
        back.setOnClickListener(this);
        register.setOnClickListener(this);
        db=new UserDBHelper(this);

    }
    public void getDetails() {
        String user = username.getText().toString();
        String password = password1.getText().toString();
        String Email = email.getText().toString();

        try{
            db.AddUser(Email,user,password);
        }
        catch (Exception e){
            Toast.makeText(RegisterActivity.this,
                    "Check input values are valid", Toast.LENGTH_LONG).show();
        }

        //Take all user details from view objects
        //Try to add details to database and have appropriate error handling

    }

    @Override
    public void onClick(View v) {
        int num =v.getId();
        if(num==R.id.btnBack){
            //Bring user back to login
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
        else if (num==R.id.buttontnReg){
            //Register User then send back to login

            //Check if password = confrim password
            if (password1.getText().toString().equals(password2.getText().toString())==false) {
                password1.setError("Passwords do not match");
                password1.requestFocus();
            }
            //Check if password meets 7 character with 1 number requirement
            else if(password1.getText().toString().length()<=6 ||
                    password1.getText().toString().matches(".*\\d+.*")==false){
                password1.setError("Passwords must consist of minimum 7 characters and" +
                        " contain 1 number.");
                password1.requestFocus();
            }
            //Check if field is populated
            else if(password1.getText().toString().length()==0){
                password1.setError("This a required field");
                password1.requestFocus();
            }
            //Check if field is populated
            else if(password2.getText().toString().length()==0){
                password2.setError("This a required field");
                password2.requestFocus();
            }
            //Check if field is populated
            else if(username.getText().toString().length()==0){
                username.setError("This a required field");
                username.requestFocus();
            }
            //Check if field is populated
            else if(email.getText().toString().length()==0){
                email.setError("This a required field");
                email.requestFocus();
            }
            else if(db.usernameExists(username.getText().toString())){
                username.setError("This username already exists");
            }
            else if(emailFormat(email.getText().toString())==false){
                email.setError("Please enter a valid email address");
                email.requestFocus();
            }
            else
            {
                //Else means no validation conditions have been breached so therfore calls getDetails method to..
                //Place new user details in database
                getDetails();
                //Notify user and proceed to login screen
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        }
    }

    //Method used to verify email address is in the correct format returns TRUE or FALSE
    public boolean emailFormat(String email){
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher=VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();

    }
}
