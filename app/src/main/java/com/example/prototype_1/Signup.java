package com.example.prototype_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText name,id,number,password,password2;
    Spinner s1;
    Button b1;
    TextView userLogin;
    DatabaseReference databasepartner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        databasepartner= FirebaseDatabase.getInstance().getReference("Partner");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:1058913539887:android:eeb8e831e7595d47de33da") // Required for Analytics.
                .setApiKey("AIzaSyAkh0JWnGYUhhAGZVDraBW2BbBMsNoMpKQ") // Required for Auth.
                .setDatabaseUrl("https://customerprototype-29375-fbcfa.firebaseio.com/") // Required for RTDB.
                .build();

        // as now we have access to both the db we can look at the customer profile as well...


        //
        name=(EditText)findViewById(R.id.nam);
        id=(EditText)findViewById(R.id.email);
        number=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        password2=(EditText)findViewById(R.id.editText3);
        userLogin=(TextView)findViewById(R.id.tvUserLogin);

        s1=(Spinner)findViewById(R.id.spinner);
        b1=(Button)findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            // Upon hitting the button it must verify the email address and then redirect to login page...
            public void onClick(View v) {
               /*


                */

               /*
               if (validate()){
                   verifynumber();
               }

                */
              // verifynumber();

                // right now not validating phone number and only focusing on saving the data in the db... (as of 14/03)

               addata();
            }
        });

        userLogin=(TextView)findViewById(R.id.tvUserLogin);
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,Login.class));
            }
        });
    }

    public void addata(){
        String uname=name.getText().toString();
        String em= id.getText().toString();
        String pno=number.getText().toString();
        String p1=(String)password.getText().toString();
        String genre=s1.getSelectedItem().toString();


       String id= databasepartner.push().getKey();

       profile p= new profile(uname,em,pno,p1,genre);

       databasepartner.child(id).setValue(p);

        Toast.makeText(getApplicationContext(), "User Created!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Signup.this,Login.class));
    }
    private boolean validate(){

        boolean result=false;

        String uname=name.getText().toString();
        String em= id.getText().toString();
        String pno=number.getText().toString();
        String p1=(String)password.getText().toString();
        String p2=(String)password2.getText().toString();

        if(uname.isEmpty() && em.isEmpty() && pno.isEmpty() &&p1.isEmpty() && p2.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter all the details",Toast.LENGTH_LONG).show();
        }

        else{
            boolean check=checkpass(p1,p2);

            if (check==true) {
                result=true;
            }
            else{
                Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_LONG).show();
            }
        }
        return  result;

    }
    public boolean checkpass(String p1,String p2){
        boolean ans= p1.matches(p2);

        return  ans;
    }
    public void verifynumber(){


        number =  findViewById(R.id.editText);
        String num = number.getText().toString().trim();

        if (num.isEmpty() || num.length()<10) {
            Toast.makeText(getApplicationContext(), "Enter Valid Phone Number", Toast.LENGTH_LONG).show();
        }

        else {
            num="+91"+num;
            Intent i = new Intent(Signup.this, verifyphone.class);
            i.putExtra("p1", num);
          //  i.putExtra("username",name.getText().toString().trim());
           // i.putExtra("email",id.getText().toString().trim());
           // i.putExtra("password",password2.getText().toString().trim());
            // do it for category as well...
            startActivity(i);
        }
    }
}
