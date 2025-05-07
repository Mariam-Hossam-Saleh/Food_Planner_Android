package com.example.food_planner;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText edtPassword;
    private EditText edtEmail;
    Button btnSkip;
    Button btnRegister;
    Button btnSignIn;
    private FirebaseAuth mAuth;
    private MealLocalDataSourceImp localDataSource;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btnSkip = findViewById(R.id.btnSkip);
        btnRegister = findViewById(R.id.btnReg);
        btnSignIn = findViewById(R.id.btnSignIn);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        mAuth = FirebaseAuth.getInstance();
        localDataSource = MealLocalDataSourceImp.getInstance(this);

        edtPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (edtPassword.getRight()
                        - edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    int selection = edtPassword.getSelectionEnd();

                    if ((edtPassword.getInputType() & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                            == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.key, 0, R.drawable.invisibile, 0);
                    } else {
                        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.key, 0, R.drawable.visibile, 0);
                    }

                    edtPassword.setSelection(selection);
                    return true;
                }
            }
            return false;
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Email or password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser user = null;
                                if (task.isSuccessful()) {
                                    user = getFirebaseUser();
                                    Log.d("FireBase", "signInWithEmail:success");
                                    Toast.makeText(SignInActivity.this, "Welcome " + user.getDisplayName(),
                                            Toast.LENGTH_SHORT).show();
                                    // Sync with Firestore
                                    localDataSource.syncWithFirestore();
                                } else {
                                    Exception exception = task.getException();
                                    if (exception instanceof FirebaseAuthInvalidUserException) {
                                        Toast.makeText(SignInActivity.this, "No account with this email, please register",
                                                Toast.LENGTH_SHORT).show();
                                        Log.e("FireBase", "No account with this email!");
                                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(SignInActivity.this, "Email or Password is incorrect!",
                                                Toast.LENGTH_SHORT).show();
                                        Log.e("FireBase", "Invalid credentials.");
                                    } else {
                                        Log.e("FireBase", "Sign-in failed: " + exception.getMessage());
                                        Toast.makeText(SignInActivity.this, "Something went wrong, please try again!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                updateUI(user);
                            }

                            @Nullable
                            private FirebaseUser getFirebaseUser() {
                                FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    String name = user.getDisplayName();
                                    String email = user.getEmail();
                                    boolean emailVerified = user.isEmailVerified();
                                    String uid = user.getUid();
                                    Log.i("FireBase", email);
                                }
                                return user;
                            }
                        });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            localDataSource.syncWithFirestore();
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            edtEmail.setText("");
            edtPassword.setText("");
        }
    }

    private void reload() {
    }
}