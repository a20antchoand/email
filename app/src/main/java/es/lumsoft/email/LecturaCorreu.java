package es.lumsoft.email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.progressindicator.BaseProgressIndicator;

import es.lumsoft.email.databinding.ActivityLecturaCorreuBinding;
import es.lumsoft.email.databinding.ActivityPaginaPrincipalBinding;

public class LecturaCorreu extends AppCompatActivity {

    ActivityLecturaCorreuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLecturaCorreuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setup();

        setContentView(view);
    }

    private void setup() {

        Bundle info = getIntent().getExtras();


        binding.txtTtitolEmail.setText(info.getString("titol"));
        binding.txtFromEmail.setText(info.getString("from"));
        binding.txtTimeEmail.setText(info.getString("time"));
        binding.txtToEmail.setText(info.getString("to"));
        binding.txtContentEmail.setText(info.getString("content"));


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(LecturaCorreu.this, PaginaPrincipal.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}