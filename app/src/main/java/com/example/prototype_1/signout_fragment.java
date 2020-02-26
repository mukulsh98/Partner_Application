package com.example.prototype_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class signout_fragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity(new Intent(getContext(),Login.class));

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
