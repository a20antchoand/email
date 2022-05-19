package es.lumsoft.email;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import es.lumsoft.email.databinding.ActivityLoginBinding;
import es.lumsoft.email.databinding.ActivityPaginaPrincipalBinding;

public class PaginaPrincipal extends AppCompatActivity {

    private ActivityPaginaPrincipalBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaginaPrincipalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setup();

    }

    private void setup() {

        binding.floatinBtnSend.setOnClickListener(l -> startActivity(new Intent(PaginaPrincipal.this, enviarCorreu.class)));

    }
}