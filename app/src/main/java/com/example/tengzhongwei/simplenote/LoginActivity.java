package com.example.tengzhongwei.simplenote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText editUsr;
    private EditText editPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.btn_login);
        editUsr = findViewById(R.id.edit_usr);
        editPwd = findViewById(R.id.edit_pwd);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check the input when debugging
                Log.i("my usr input:", editUsr.getText().toString());
                Log.i("My pwd input:", editPwd.getText().toString());
                onButtonClick();
            }
        });

    }

    public void onButtonClick(){
        //Validate Input
        if(validate()){
            // Login
            login(editUsr.getText().toString(), editPwd.getText().toString());
            finish();
        }
    }

    public void login(String usrname, String pwd){
        // Send usrname&password to Server
    }

    public boolean validate(){
        if(checkError(editUsr)){
            editUsr.setError("Please Enter your username");
            return false;
        }
        else if(checkError(editPwd)){
            editPwd.setError("Please Enter your password");
            return false;
        }
        return true;
    }

    public boolean checkError(EditText editText){
        return editText.getText().toString() == null|| editText.getText().toString().length()==0;
    }


}
