package com.example.livro;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTitulo;
    EditText editAutor;
    EditText Ano;
    Spinner Genero;
    Spinner Status;
    CheckBox Favorito;
    CheckBox checkFiltrarFavoritos;

    Button btnSalvar;

    ListView ListLivros;

    ArrayList<livros> listaLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        editTitulo = findViewById(R.id.editTitulo);
        editAutor = findViewById(R.id.editAutor);
        Ano = findViewById(R.id.editAno);
        Genero = findViewById(R.id.Genero);
        Status = findViewById(R.id.Status);
        Favorito = findViewById(R.id.Favorito);
        checkFiltrarFavoritos = findViewById(R.id.checkFiltrarFavoritos);
        btnSalvar = findViewById(R.id.btnSalvar);
        ListLivros = findViewById(R.id.listLivros);
        listaLivros = new ArrayList<>();


        String[] generos = {"Romance","Fantasia", "Terror","Ficção Científica","Biografia"};

        ArrayAdapter<String>adapterGenero = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generos);

        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Genero.setAdapter(adapterGenero);

        String[] status2 = {"quero ler", "Lendo", "Concluido"};

        ArrayAdapter<String>adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, status2);

        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Status.setAdapter(adapterStatus);

        checkFiltrarFavoritos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            atualizarLista();
        });

        btnSalvar.setOnClickListener(view -> {
            try {
                String titulo = editTitulo.getText().toString();
                String autor = editAutor.getText().toString();
                int ano = Integer.parseInt(Ano.getText().toString());
                String genero = Genero.getSelectedItem().toString();
                String status = Status.getSelectedItem().toString();
                boolean favorito = Favorito.isChecked();

                livros livroNovo = new livros(titulo, autor, genero, ano, status, favorito);
                listaLivros.add(livroNovo);

                atualizarLista();


                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> livro = new HashMap<>();
                livro.put("Titulo", titulo);
                livro.put("Autor", autor);
                livro.put("Genero", genero);
                livro.put("Ano", ano);
                livro.put("Status", status);
                livro.put("Favorito", favorito);

                db.collection("Livros")
                        .add(livro)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Livro salvo!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
                        });

            } catch (Exception e) {
                Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void atualizarLista() {
        ArrayList<livros> listaExibicao;

        if (checkFiltrarFavoritos.isChecked()) {
            listaExibicao = new ArrayList<>();
            for (livros l : listaLivros) {
                if (l.getFavorito()) {
                    listaExibicao.add(l);
                }
            }
        } else {
            listaExibicao = listaLivros;
        }

        ArrayAdapter<livros> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaExibicao);
        ListLivros.setAdapter(adapter);
    }
}