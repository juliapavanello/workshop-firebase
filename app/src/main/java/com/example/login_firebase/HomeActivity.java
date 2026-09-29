package com.example.login_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private EditText edtFrase;
    private Button btnSalvar, btnSair;
    private TextView txtResultado;

    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        edtFrase = findViewById(R.id.edtFrase);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSair = findViewById(R.id.btnSair);
        txtResultado = findViewById(R.id.txtResultado);

        btnSalvar.setOnClickListener(v -> salvarFrase());
        btnSair.setOnClickListener(v -> sair());

        carregarFrase();
    }

    private void salvarFrase() {
        String frase = edtFrase.getText().toString().trim();

        if (frase.isEmpty()) {
            Toast.makeText(this, "Escreva algo antes de salvar", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> dados = new HashMap<>();
        dados.put("frase", frase);

        db.collection("diario").document(uid)
                .set(dados)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Salvo no Firestore!", Toast.LENGTH_SHORT).show();
                    txtResultado.setText(frase);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private void carregarFrase() {
        db.collection("diario").document(uid)
                .get()
                .addOnSuccessListener(this::mostrarFraseSalva)
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erro ao carregar: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private void mostrarFraseSalva(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.exists()) {
            String frase = documentSnapshot.getString("frase");
            txtResultado.setText(frase);
        }
    }

    private void sair() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}