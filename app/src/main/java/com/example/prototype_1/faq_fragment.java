package com.example.prototype_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class faq_fragment extends Fragment {

    private Button raise_request, chat, call;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_faq,container,false);

        raise_request=(Button)v.findViewById(R.id.b1);
        raise_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        chat=(Button)v.findViewById(R.id.b2);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number="+919999809305"; // number to whatsapp
                Uri uri = Uri.parse("smsto:" + number);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(i, ""));

            }
        });

        call=(Button)v.findViewById(R.id.b3);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contact_no="+919999809305";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact_no));
                startActivity(intent);
            }
        });
        return v;
    }
}
