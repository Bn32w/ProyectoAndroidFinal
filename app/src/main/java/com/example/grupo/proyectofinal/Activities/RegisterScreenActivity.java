package com.example.grupo.proyectofinal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo.proyectofinal.DataRepos.MysqlImpl.MysqlUserImpl;
import com.example.grupo.proyectofinal.R;

public class RegisterScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
    }

    public void register(View view){
        EditText username = findViewById(R.id.register_username);
        EditText name = findViewById(R.id.register_name);
        EditText surname = findViewById(R.id.register_surname);
        EditText password = findViewById(R.id.register_password);
        EditText confirm = findViewById(R.id.register_confirm);

        if(username!=null && !username.getText().toString().isEmpty() && !username.getText().toString().equals("")){
            if(name!=null && !name.getText().toString().isEmpty() && !name.getText().toString().equals("")){
                if(surname!=null && !name.getText().toString().isEmpty() && !name.getText().toString().equals("")){
                    if(password!=null && !password.getText().toString().isEmpty() && !password.getText().toString().equals("")){
                        if(confirm!=null && !confirm.getText().toString().isEmpty() && !confirm.getText().toString().equals("") && password.getText().toString().equals(confirm.getText().toString())){
                            MysqlUserImpl mysqlUser = new MysqlUserImpl(this);
                            if(mysqlUser.createUser(username.getText().toString(),name.getText().toString(),surname.getText().toString(),password.getText().toString())){
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
