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

        if (getIntent().getExtras() != null) {

            Bundle reply = getIntent().getExtras();

            binding.editTextTo.setText(reply.getString("from"));
            binding.editTextTitol.setText("Re: " + reply.getString("titol"));

            binding.editTextMissatge.setText("\n\n\n\n\n\n\n-----------------------------------------------\n\n" + reply.getString("content"));

        }

        setup();

    }

    private void setup() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.txtViewFrom.setText(user.getEmail());

        binding.imageView3.setOnClickListener(l -> {
            if (!binding.editTextTo.getText().toString().equals("")) {
                new EnviarMails(this, binding.txtViewFrom.getText().toString(), binding.editTextTo.getText().toString(), binding.editTextTitol.getText().toString(), binding.editTextMissatge.getText().toString());


                SweetAlertDialog sDialog = new SweetAlertDialog(EnviarCorreu.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Correu enviat")
                        .setContentText("S'ha enviat correctament!");

                sDialog.setOnDismissListener(sweetAlertDialog -> {

                            startActivity(new Intent(EnviarCorreu.this, PaginaPrincipal.class));

                        });

                sDialog.show();
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
