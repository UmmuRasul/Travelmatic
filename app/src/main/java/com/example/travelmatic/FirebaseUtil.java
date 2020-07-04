package com.example.travelmatic;

import android.app.Activity;
import android.content.ContextWrapper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AuthUI.IdpConfig;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil firebaseUtil;
    public static FirebaseAuth mFirebaseAuth;
    private static final int RC_SIGN_IN = 123;
    private static Activity caller;



    public  static FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            if(firebaseAuth.getCurrentUser() == null)
            {
                FirebaseUtil.signIn();
            }else{
                Toast.makeText(callerActivity.getBaseContext(), "Welcome back",Toast.LENGTH_LONG).show();
            }

        }
    };
    public static ArrayList<TravelDeal> mDeals;
    private static ContextWrapper callerActivity;

    private FirebaseUtil(){};
    public  static  void openFbReference(String ref, Activity callerActivity){
        if (firebaseUtil == null){
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            caller = callerActivity;

        }
        mDeals = new ArrayList<TravelDeal>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);

    }
    private static void signIn() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        caller.startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN);
    }

    public static  void attachListener()
    {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }
    public  static  void detachListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }

    public static void openFbReference(String traveldeals) {

    }
}
