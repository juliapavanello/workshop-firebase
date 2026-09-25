package com.example.login_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button btnChamarFuncao, btnSair;
    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnChamarFuncao = findViewById(R.id.btnChamarFuncao);
        btnSair = findViewById(R.id.btnSair);
        txtResultado = findViewById(R.id.txtResultado);

        btnChamarFuncao.setOnClickListener(v -> chamarCloudFunction());
        btnSair.setOnClickListener(v -> sair());
    }

    private void chamarCloudFunction() {
        Toast.makeText(this, "TODO: chamar Cloud Function aqui", Toast.LENGTH_SHORT).show();
        txtResultado.setText("Resultado aparecerá aqui");
    }

    private void sair() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}