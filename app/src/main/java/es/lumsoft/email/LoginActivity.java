package es.lumsoft.email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.lumsoft.email.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, PaginaPrincipal.class));
        } else {
            setup();
        }
    }

    private void setup() {


        binding.btnLogin.setOnClickListener(view -> {

            String emailStr = binding.editTextEmail.getText().toString();
            String passStr = binding.editTextPass.getText().toString();

            System.out.println(emailStr + " " + passStr);

            mAuth.signInWithEmailAndPassword(emailStr, passStr)
                    .addOnSuccessListener(authResult -> startActivity(new Intent(LoginActivity.this, PaginaPrincipal.class)))
                    .addOnFailureListener(e -> {
                        Toast.makeText(LoginActivity.this, "Login incorrecte", Toast.LENGTH_SHORT).show();
                    });
        });


    }
}