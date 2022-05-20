package es.lumsoft.email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import es.lumsoft.email.clases.Email;
import es.lumsoft.email.clases.RebreMails;
import es.lumsoft.email.databinding.ActivityPaginaPrincipalBinding;
import es.lumsoft.email.recyclerView.ListAdapterEmails;

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

        new Handler(Looper.getMainLooper()).post(() -> {
            RebreMails rebreMails = new RebreMails();

            ListAdapterEmails listAdapter = new ListAdapterEmails(rebreMails.getEmails(), this, this::obrirCorreu);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerView.setAdapter(listAdapter);

            binding.progressBar.setVisibility(View.GONE);
        });


        binding.floatinBtnSend.setOnClickListener(l -> startActivity(new Intent(PaginaPrincipal.this, EnviarCorreu.class)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reply:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PaginaPrincipal.this, LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void obrirCorreu(Email email) {

        Intent intent = new Intent(this, LecturaCorreu.class);
        intent.putExtra("titol", email.getSubject());
        intent.putExtra("from", email.getFrom());
        intent.putExtra("time", email.getDate());
        intent.putExtra("to", email.getTo());
        intent.putExtra("content", email.getContent());

        startActivity(intent);

    }
}