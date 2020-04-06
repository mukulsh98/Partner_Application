package com.example.prototype_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.google.android.gms.wallet.PaymentsClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class my_order_fragment extends Fragment {

    private Button generate, scan;
    private ImageView qrcode;
    // QR code will be generated for the specific email address or we can do it for phone number as well as each user will have its unique phone number...
    private String email;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;



    Button send;
    final int UPI_PAYMENT = 0;
    private PaymentsClient paymentsClient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_my_order,container,false);

        send= (Button)v.findViewById(R.id.button8);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi();
            }
        });

        generate = (Button) v.findViewById(R.id.generate);
        scan = (Button) v.findViewById(R.id.scan);
        qrcode=(ImageView)v.findViewById(R.id.qrcode);


        mAuth= FirebaseAuth.getInstance();
        mCurrentUser= mAuth.getCurrentUser();

        String uname=mCurrentUser.getDisplayName();
        Toast.makeText(getActivity(), uname,Toast.LENGTH_LONG).show();


        email="sharma.mukul938@gmail.com";
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=email.trim();
                if(text!=null && !(text.isEmpty())){
                    try {
                        // generating qr code...
                        MultiFormatWriter multi= new MultiFormatWriter();
                        BitMatrix bit= multi.encode(text, BarcodeFormat.QR_CODE,500,500);
                        BarcodeEncoder bar=new BarcodeEncoder();
                        Bitmap bitmap= bar.createBitmap(bit);
                        qrcode.setImageBitmap(bitmap);

                    }
                    catch ( WriterException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator in= IntentIntegrator.forSupportFragment(my_order_fragment.this);
                in.setDesiredBarcodeFormats(in.QR_CODE_TYPES);
                in.setCameraId(0);
                in.setOrientationLocked(false);
                in.setPrompt("Scanning");
                in.setBeepEnabled(true);
                in.setBarcodeImageEnabled(true);
                in.initiateScan();
            }
        });

        return v;
    }

    public void payUsingUpi(){

        String name="";
        String upiId="";
        String note="";
        String amount="";

        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);

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
        if(null != chooser.resolveActivity(getActivity().getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(getActivity(),"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }


    }
    /*
    protected  void onActivityForResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response " + resultCode);
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    */

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(getActivity())) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(getActivity(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(getActivity(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
            }
            else {
                Toast.makeText(getActivity(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);
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

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


         send=(Button) findViewById(R.id.button6);
        //WalletOptions walletOptions=
                new Wallet.WalletOptions.Builder().setEnvironment(WalletConstants.ENVIRONMENT_TEST).build();

        //paymentsClient= Wallet.getPaymentsClient(my_order_fragment.this , walletOptions);
    }

     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult res= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(res!=null && res.getContents()!=null){
            // the result from the scanned image is saved in this string...
            String result=res.getContents();
            Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
