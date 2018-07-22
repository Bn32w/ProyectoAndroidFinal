package com.example.grupo.proyectofinal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo.proyectofinal.DataRepos.MysqlImpl.MysqlUserImpl;
import com.example.grupo.proyectofinal.R;

public class RegisterScreenActivity extends AppCompatActivity {

    private EditText username;
    private EditText name;
    private EditText surname;
    private EditText password;
    private EditText confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        username = findViewById(R.id.register_username);
        name = findViewById(R.id.register_name);
        surname = findViewById(R.id.register_surname);
        password = findViewById(R.id.register_password);
        confirm = findViewById(R.id.register_confirm);


        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    username.setText("");
                }
                else {
                    if(username.getText().toString().equals("")){
                        username.setText(R.string.username);
                    }
                }
            }
        });


        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    name.setText("");
                }
                else {
                    if(name.getText().toString().equals("")){
                        name.setText(R.string.name);
                    }
                }
            }
        });


        surname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    surname.setText("");
                }
                else {
                    if(surname.getText().toString().equals("")){
                        surname.setText(R.string.surname);
                    }
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    password.setText("");
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }else{
                    if(password.getText().toString().equals("")){
                        password.setTransformationMethod(new SingleLineTransformationMethod());
                        password.setText(R.string.password);
                    }
                }
            }
        });

        confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    confirm.setText("");
                    confirm.setTransformationMethod(new PasswordTransformationMethod());
                }else{
                    if(confirm.getText().toString().equals("")){
                        confirm.setTransformationMethod(new SingleLineTransformationMethod());
                        confirm.setText(R.string.repeat_password);
                    }
                }
            }
        });
    }

    public void register(View view){


        if(username !=null && !username.getText().toString().isEmpty() && !username.getText().toString().equals("")){
            if(name !=null && !name.getText().toString().isEmpty() && !name.getText().toString().equals("")){
                if(surname !=null && !name.getText().toString().isEmpty() && !name.getText().toString().equals("")){
                    if(password !=null && !password.getText().toString().isEmpty() && !password.getText().toString().equals("")){
                        if(confirm !=null && !confirm.getText().toString().isEmpty() && !confirm.getText().toString().equals("") && password.getText().toString().equals(confirm.getText().toString())){
                            MysqlUserImpl mysqlUser = new MysqlUserImpl(this);
                            if(mysqlUser.createUser(username.getText().toString(), name.getText().toString(), surname.getText().toString(), password.getText().toString())){
                                //HORRAY
                            }
                            else{
                                Toast.makeText(this, R.string.errorCreatingUser,Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(this,R.string.password_mismatch,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this,R.string.not_valid_password,Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this,R.string.not_valid_surname,Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this,R.string.not_valid_name,Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,R.string.not_valid_username,Toast.LENGTH_SHORT).show();
        }
    }
}
