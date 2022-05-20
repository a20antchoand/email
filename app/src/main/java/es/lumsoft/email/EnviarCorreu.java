package es.lumsoft.email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.lumsoft.email.clases.EnviarMails;
import es.lumsoft.email.databinding.ActivityEnviarCorreuBinding;

public class EnviarCorreu extends AppCompatActivity {

    private ActivityEnviarCorreuBinding binding;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEnviarCorreuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        setContentView(view);

        setup();

    }

    private void setup() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.txtViewFrom.setText(user.getEmail());

        binding.imageView3.setOnClickListener(l -> {
            if (!binding.editTextTo.getText().toString().equals("")) {
                new EnviarMails(this, binding.editTextTo.getText().toString(), binding.editTextTitol.getText().toString(), binding.editTextMissatge.getText().toString());
                new SweetAlertDialog(EnviarCorreu.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Correu enviat")
                        .setContentText("S'ha enviat correctament!")
                        .show();
                startActivity(new Intent(EnviarCorreu.this, PaginaPrincipal.class));

            }else {
                new SweetAlertDialog(EnviarCorreu.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("No has introduit destinatari!")
                        .show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(EnviarCorreu.this, PaginaPrincipal.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EnviarCorreu.this, PaginaPrincipal.class));
    }
}
