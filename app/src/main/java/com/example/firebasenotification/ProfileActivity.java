package com.example.firebasenotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebasenotification.databinding.ActivityProfileBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();

        if (firebaseUser!=null){
            Glide.with(ProfileActivity.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(binding.ivImage);
            binding.tvName.setText(firebaseUser.getDisplayName());

            googleSignInClient= GoogleSignIn.getClient(ProfileActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);
            binding.btLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    googleSignInClient.signOut().addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                firebaseAuth.signOut();
                                Toast.makeText(ProfileActivity.this, "Logout SuccessFul", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
            });
        }



    }
}