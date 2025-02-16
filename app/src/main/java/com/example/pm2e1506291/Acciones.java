package com.example.pm2e1506291;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm2e1506291.Models.PaisesModel;
import com.example.pm2e1506291.Repository.PaisesRepository;

import java.util.ArrayList;

public class Acciones extends AppCompatActivity {
    private int idpais;
    private Button camara,galeria, compartir, actualizar, eliminar, llamar;
    private EditText nombre, numero, nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acciones);
        camara=(Button) findViewById(R.id.button6);
        galeria=(Button) findViewById(R.id.button8);
        compartir=(Button) findViewById(R.id.button5);
        actualizar=(Button) findViewById(R.id.button9);
        eliminar=(Button) findViewById(R.id.button10);
        llamar=(Button) findViewById(R.id.button10);

        nombre=(EditText) findViewById(R.id.editTextText3);
        numero=(EditText) findViewById(R.id.editTextPhone2);
        nota=(EditText) findViewById(R.id.editTextTextMultiLine2);


        PaisesRepository paisesRepository = new PaisesRepository(this);
        final int[] idPais = {-1}; // Inicializar el ID del país seleccionado
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})

        Spinner spinner = findViewById(R.id.spinner2);

        ArrayList<PaisesModel> paises = paisesRepository.ObtenerPaises();
        ArrayList<String> nombresPaises = new ArrayList<>();

        for (PaisesModel pais : paises) {
            nombresPaises.add(pais.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresPaises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idPais[0] = paises.get(position).getId();
                Toast.makeText(getApplicationContext(), "País seleccionado: " + paises.get(position).getNombre() + ", ID: " + idPais[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}