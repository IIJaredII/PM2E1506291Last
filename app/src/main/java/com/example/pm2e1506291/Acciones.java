package com.example.pm2e1506291;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm2e1506291.Funciones.imageUtils;
import com.example.pm2e1506291.Models.PaisesModel;
import com.example.pm2e1506291.Repository.PaisesRepository;

import java.util.ArrayList;

public class Acciones extends AppCompatActivity {
    private int idpaisSeleccionado, idContacto;
    private Button camara, galeria, compartir, actualizar, eliminar, llamar;
    private EditText nombre, numero, nota;
    private ImageView imagenContacto;
    private Spinner spinner;
    private ArrayList<PaisesModel> paisesList;
    private PaisesRepository paisesRepository;

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
        llamar=(Button) findViewById(R.id.button7);

        nombre=(EditText) findViewById(R.id.editTextText3);
        numero=(EditText) findViewById(R.id.editTextPhone2);
        nota=(EditText) findViewById(R.id.editTextTextMultiLine2);

        Intent intent = getIntent();
        if (intent != null) {
            idContacto = intent.getIntExtra("id", -1);
            nombre.setText(intent.getStringExtra("nombre"));
            numero.setText(intent.getStringExtra("numero"));
            nota.setText(intent.getStringExtra("nota"));
            idpaisSeleccionado = intent.getIntExtra("idpais", -1);

            // Decodificar imagen
            String imagenBase64 = intent.getStringExtra("imagen");
            if (imagenBase64 != null && !imagenBase64.isEmpty()) {
                Bitmap bitmap = imageUtils.decodeFromBase64(imagenBase64);
                imagenContacto.setImageBitmap(bitmap);
            }
        }

        // Cargar países en el Spinner
        ArrayList<String> nombresPaises = new ArrayList<>();
        for (PaisesModel pais : paisesList) {
            nombresPaises.add(pais.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresPaises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Seleccionar el país correcto
        for (int i = 0; i < paisesList.size(); i++) {
            if (paisesList.get(i).getId() == idpaisSeleccionado) {
                spinner.setSelection(i);
                break;
            }
        }

        // Manejar selección de país
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                idpaisSeleccionado = paisesList.get(position).getId();
                Toast.makeText(getApplicationContext(), "País seleccionado: " + paisesList.get(position).getNombre(), Toast.LENGTH_SHORT).show();
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