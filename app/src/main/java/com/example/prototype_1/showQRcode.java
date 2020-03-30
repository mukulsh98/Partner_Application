package com.example.prototype_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class showQRcode extends AppCompatActivity {

    String email;
    private ImageView qrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_q_rcode);

        qrcode=(ImageView)findViewById(R.id.qrcode);

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
}
