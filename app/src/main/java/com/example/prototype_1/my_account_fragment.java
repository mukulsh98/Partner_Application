package com.example.prototype_1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class my_account_fragment extends Fragment {

    private ImageView location, payment, discount, qrcode, wallletBtn, inv;
    private Switch profile_status;
    final int UPI_PAYMENT = 0;
    private PaymentsClient paymentsClient;
    FirebaseAuth Auth;
    FirebaseUser user;
    DatabaseReference reference;

    private static final String TAG = "my_account_fragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_account, container, false);

        location = (ImageView) v.findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));

            }
        });
        wallletBtn = v.findViewById(R.id.walletBtn);
        wallletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),Wallet.class);
                startActivity(i);
            }
        });
        payment = (ImageView) v.findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi();
            }
        });

        discount = (ImageView) v.findViewById(R.id.discout);
        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), discount.class));
            }
        });

        qrcode = (ImageView) v.findViewById(R.id.qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),showQRcode.class));
            }
        });

        inv=(ImageView)v.findViewById(R.id.inven);
        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // test code...




            }
        });

        Auth= FirebaseAuth.getInstance();
        user= Auth.getCurrentUser();

        reference= FirebaseDatabase.getInstance("https://customerprototype-29375-fbcfa.firebaseio.com/").getReference().child(user.getUid());

        profile_status = (Switch) v.findViewById(R.id.profile);
        profile_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String uname = dataSnapshot.child("name").toString();
                            String em = dataSnapshot.child("email").toString();
                            String mobile = dataSnapshot.child("number").toString();
                            String genre = dataSnapshot.child("genre").toString();
                            String user_name = dataSnapshot.child("id").toString();
                            String status = "on";
                            String shopName = dataSnapshot.child("shopName").toString();
                            String address = dataSnapshot.child("address").toString();
                            String latitude = dataSnapshot.child("latitude").toString();
                            String longitude = dataSnapshot.child("longitude").toString();
                            String rating = dataSnapshot.child("rating").toString();
                            String product01 = dataSnapshot.child("product01").toString();
                            String product02 = dataSnapshot.child("product02").toString();
                            String feedback01 = dataSnapshot.child("feedback01").toString();
                            String feedback02 = dataSnapshot.child("feedback02").toString();
                            String total_no_of_products = dataSnapshot.child("total_no_of_products").toString();
                            String offer = dataSnapshot.child("offer").toString();


                            profile p = new profile(uname, em, mobile, genre, user_name, status, shopName, address);
                            p.setLat(latitude);
                            p.setLongitude(longitude);
                            p.setProduct01(product01);
                            p.setProduct02(product02);
                            p.setFeedback01(feedback01);
                            p.setFeedback02(feedback02);
                            p.setTotal_no_of_products(total_no_of_products);
                            p.setOffer(offer);
                            p.setRating(rating);
                            reference.setValue(p);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(),"Error: "+databaseError.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
                        else {
                    // Change the status of partner to off...

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String uname=dataSnapshot.child("name").toString();
                            String em= dataSnapshot.child("email").toString();
                            String mobile= dataSnapshot.child("number").toString();
                            String genre= dataSnapshot.child("genre").toString();
                            String user_name= dataSnapshot.child("id").toString();
                            String status="off";
                            String shopName= dataSnapshot.child("shopName").toString();
                            String address= dataSnapshot.child("address").toString();
                            String latitude=dataSnapshot.child("latitude").toString();
                            String longitude=dataSnapshot.child("longitude").toString();
                            String rating=dataSnapshot.child("rating").toString();
                            String product01=dataSnapshot.child("product01").toString();
                            String product02=dataSnapshot.child("product02").toString();
                            String feedback01=dataSnapshot.child("feedback01").toString();
                            String feedback02=dataSnapshot.child("feedback02").toString();
                            String total_no_of_products=dataSnapshot.child("total_no_of_products").toString();
                            String offer = dataSnapshot.child("offer").toString();


                                profile p= new profile(uname,em,mobile,genre,user_name, status,shopName,address);
                                p.setLat(latitude);
                                p.setLongitude(longitude);
                                p.setProduct01(product01);
                                p.setProduct02(product02);
                                p.setFeedback01(feedback01);
                                p.setFeedback02(feedback02);
                                p.setTotal_no_of_products(total_no_of_products);
                                p.setOffer(offer);
                                p.setRating(rating);
                                reference.setValue(p);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(),"Error: "+databaseError.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                    Toast.makeText(getActivity(), "Status Unactive", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }



    public void payUsingUpi() {

        String name = "";
        String upiId = "";
        String note = "";
        String amount = "";

        Log.e("main ", "name " + name + "--up--" + upiId + "--" + note + "--" + amount);

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        // check if intent resolves
        if (null != chooser.resolveActivity(getActivity().getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(getActivity(), "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }


    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(getActivity())) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(getActivity(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(getActivity(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: " + approvalRefNo);
            } else {
                Toast.makeText(getActivity(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: " + approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(getActivity(), "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnectionAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult res = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (res != null && res.getContents() != null) {
            // the result from the scanned image is saved in this string...
            String result = res.getContents();
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    class insert{
        String nam;
        Number partb=0;
    }
}
