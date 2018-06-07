package com.example.tengzhongwei.simplenote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {
    private Button cancel_button;
    private Button update_button;
    private EditText pwd_edit_text;
    private EditText email_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        cancel_button = findViewById(R.id.profile_cancel_button);
        update_button = findViewById(R.id.profile_update_button);
        pwd_edit_text = findViewById(R.id.profile_password_edit);
        email_edit_text = findViewById(R.id.profile_email_edit);



        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
                finish();
            }
        });
    }





    private void updateProfile(){

    }

}
