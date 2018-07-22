package com.example.grupo.proyectofinal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo.proyectofinal.DataRepos.MysqlImpl.MysqlUserImpl;
import com.example.grupo.proyectofinal.R;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUsername;
    private EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.register_admin);

        loginUsername = findViewById(R.id.login_activity_username);
        loginPassword = findViewById(R.id.login_activity_password);

        loginUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    loginUsername.setText("");
                }
                else {
                    if(loginUsername.getText().toString().equals("")){
                        loginUsername.setText(R.string.login_name);
                    }
                }
            }
        });

        loginPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    loginPassword.setText("");
                    loginPassword.setTransformationMethod(new PasswordTransformationMethod());
                }else{
                    if(loginPassword.getText().toString().equals("")){
                        loginPassword.setTransformationMethod(new SingleLineTransformationMethod());
                        loginPassword.setText(R.string.login_pass);
                    }
                }
            }
        });
    }

    public void doLogin(View view){
        MysqlUserImpl impl = new MysqlUserImpl(this);
        if(impl.isUser(loginUsername.getText().toString(),loginPassword.getText().toString())){
            //TODO to admin page
        }
        else{
            Toast.makeText(this,R.string.error_login,Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view){
        //TODO go to registration page

    }
}
