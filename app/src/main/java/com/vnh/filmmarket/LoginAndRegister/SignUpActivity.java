package com.vnh.filmmarket.LoginAndRegister;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vnh.filmmarket.R;
import com.vnh.filmmarket.model.Users;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity{
    @BindView(R.id.email)
    EditText edEmail;
    @BindView(R.id.password)
    EditText edPassword;
    @BindView(R.id.confirmPassword)
    EditText edConfirm;
    @BindView(R.id.sign_up_button)
    Button btnSignUp;
    @BindView(R.id.sign_in_button)
    Button btnSignIn;
    @Nullable
    @BindView(R.id.btn_reset_password)
    Button btnResest;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.input_emailRe)
    TextInputLayout input_email;
    @BindView(R.id.input_passRE)
    TextInputLayout input_pass;
    @BindView(R.id.input_confirmPassRE)
    TextInputLayout input_confirm;


    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    String userId;
    String name,pass,confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("users");

        database.getReference("app_title").setValue("Realtime Database");
        database.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edEmail.getText().toString();
                pass = edPassword.getText().toString();
                confirm = edConfirm.getText().toString();
                createUser(name, pass);

            }
        });
    }



    public void createUser(String name, String pass) {
        if (TextUtils.isEmpty(userId)) {
            userId = databaseReference.push().getKey();
        }
        Users users = new Users(name, pass);
        databaseReference.child(userId).setValue(users);
    }

//    public void addUserChange() {
//        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Users users = dataSnapshot.getValue(Users.class);
//                if (users == null) {
//                    Log.e("Tag", "Database is null");
//                    return;
//                }
//                Log.e("Database", "Database is change");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }


    public void signUpWithEmail() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getBaseContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getBaseContext(), "Enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getBaseContext(), "Password too short! enter minimum 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getBaseContext(), "Sign Up Successful: " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()) {
                    Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());
                    Toast.makeText(SignUpActivity.this, "Sign Up can not complete", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getBaseContext(), SignUpActivity.class));
                    finish();
                }
            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
