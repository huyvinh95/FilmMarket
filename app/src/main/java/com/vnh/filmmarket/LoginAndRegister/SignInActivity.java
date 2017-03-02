package com.vnh.filmmarket.LoginAndRegister;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.vnh.filmmarket.DetailMovies;
import com.vnh.filmmarket.MainActivity;
import com.vnh.filmmarket.R;
import com.vnh.filmmarket.model.Users;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {
    @BindView(R.id.emailSignIn)
    EditText emailSignIn;
    @BindView(R.id.passwordSignIn)
    EditText passwordSignIn;
    @BindView(R.id.btn_login)
    Button btnSignIn;
    @Nullable
    @BindView(R.id.btn_reset_password)
    Button btnResest;
    @BindView(R.id.btn_signup)
    Button btnSignUp;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.textinput_email)
    TextInputLayout input_email;
    @BindView(R.id.textinput_pass)
    TextInputLayout input_pass;

    DatabaseReference databaseReference;
    FirebaseAuth auth;
    List<Users> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

//        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users users = dataSnapshot1.getValue(Users.class);
                    usersList.add(users);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = emailSignIn.getText().toString();
                String pass = passwordSignIn.getText().toString();
                if (!name.isEmpty() && !pass.isEmpty()) {
                    for (int i = 0; i < usersList.size(); i++) {
                        if (name.equals(usersList.get(i).getName())) {
                            if (pass.equals(usersList.get(i).getPass())) {
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                break;
                            } else {
                                Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "Enter your email and password", Toast.LENGTH_SHORT).show();

                }


            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SignUpActivity.class));
            }
        });
    }

    public void signInEmail() {
        String email = emailSignIn.getText().toString().trim();
        final String pass = passwordSignIn.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getBaseContext(), "Enter your email !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getBaseContext(), "Enter your password !", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()) {
                    if (pass.length() < 6) {
                        passwordSignIn.setError(getString(R.string.minimum_password));
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }
}
