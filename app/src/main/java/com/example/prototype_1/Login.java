package com.example.prototype_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button log,signin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText un,pass;
        private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String myString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        un=(EditText)findViewById(R.id.user_name);
        pass=(EditText)findViewById(R.id.pass);
        log=(Button) findViewById(R.id.button);

        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        FirebaseUser user= firebaseAuth.getCurrentUser();// checking if the current user has already login

        if(user!=null){
            finish();
            startActivity(new Intent(Login.this,HomeScreen.class));
        }
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = un.getText().toString();
                String p = pass.getText().toString();
                //Toast t= Toast.makeText(getApplicationContext(),"username:"+uname+"  password:"+p+" ",Toast.LENGTH_LONG);
                //t.show();

                //database validation of username and password

                //if failed take again input

                //else and checking if its a valid email address
                if (un.getText().toString().isEmpty()) {
                    // empty email address...
                    Toast.makeText(getApplicationContext(), "enter email address", Toast.LENGTH_SHORT).show();
                } else {
                    if (un.getText().toString().trim().matches(emailPattern)) {
                        // valid email address...
                        validate(uname,p);

                        //open2(uname);

                    } else {
                        //invalid email address
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    }




                }
            }
        });
        signin=(Button) findViewById(R.id.button2);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // testing...
                Intent intent=new Intent(Login.this, Signup.class);
                Toast.makeText(getApplicationContext(),"Now switching ",Toast.LENGTH_LONG).show();
                startActivity(intent);



            }
        });
    }
    public void open2(String uname){
        // to pass the name at the home screen... left with password verification part...
        Intent i=new Intent(this,HomeScreen.class);
        myString=uname;
        i.putExtra("name",uname);
        startActivity(i);

    }

    private void validate(String userName,String userPassword){

        progressDialog.setMessage("Verifying");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Login.this,HomeScreen.class));
                }
                else{
                    Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();


                    progressDialog.dismiss();

                }
            }
        });
    }

    public String getMyData() {
        return myString;
    }
}
