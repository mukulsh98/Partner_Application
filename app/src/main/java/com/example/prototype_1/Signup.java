package com.example.prototype_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    EditText name,id,number,password,password2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //getdata();
        b1=(Button)findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            // Upon hitting the button it must verify the email address and then redirect to login page...
            public void onClick(View v) {
                number = (EditText) findViewById(R.id.editText);
                String num = number.getText().toString();

                if (num.isEmpty() || num.length()<10) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Phone Number", Toast.LENGTH_LONG).show();
                }

                else {
                    num="+91"+num;
                   Intent i = new Intent(Signup.this, verifyphone.class);
                   i.putExtra("p1", num);
                   startActivity(i);
                }
            }
        });
    }
    public void getdata(){
        name=(EditText)findViewById(R.id.nam);
        id=(EditText)findViewById(R.id.email);
        number=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        password2=(EditText)findViewById(R.id.editText3);


        String p1=(String)password.getText().toString();
        String p2=(String)password2.getText().toString();

        boolean check=checkpass(p1,p2);

        if(check==false){
            Toast.makeText(getApplicationContext(), "Password does not match ReEnter", Toast.LENGTH_LONG);
        }
        else{
            verifynumber();

        }
    }
    public boolean checkpass(String p1,String p2){
        boolean ans= p1.matches(p2);

        return  ans;
    }
    public void verifynumber(){


    }
}
