package es.lumsoft.email;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import es.lumsoft.email.clases.EnviarMails;
import es.lumsoft.email.databinding.ActivityEnviarCorreuBinding;
import es.lumsoft.email.databinding.ActivityPaginaPrincipalBinding;

public class enviarCorreu extends AppCompatActivity {

    private ActivityEnviarCorreuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEnviarCorreuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setup();

    }

    private void setup() {

        binding.btnEnviar.setOnClickListener(l -> {
            EnviarMails.enviarCorreu(binding.editTextTitol.getText().toString(), binding.editTextMissatge.getText().toString());
        });

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(enviarCorreu.this, PaginaPrincipal.class));
    }
}