package com.example.pm2e1506291;

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
import com.example.pm2e1506291.Models.ContactosModel;
import com.example.pm2e1506291.Models.PaisesModel;
import com.example.pm2e1506291.Repository.ContactosRepository;
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

        // Inicializar vistas
        camara = findViewById(R.id.button6);
        galeria = findViewById(R.id.button8);
        compartir = findViewById(R.id.button5);
        actualizar = findViewById(R.id.button9);
        eliminar = findViewById(R.id.button10);
        llamar = findViewById(R.id.button7);
        nombre = findViewById(R.id.editTextText3);
        numero = findViewById(R.id.editTextPhone2);
        nota = findViewById(R.id.editTextTextMultiLine2);
        imagenContacto = findViewById(R.id.ivContactoLista2);
        spinner = findViewById(R.id.spinner2); // Asegúrate de que el ID es correcto en tu XML

        // Obtener el ID del contacto desde el Intent
        int contactoId = getIntent().getIntExtra("contacto_id", -1);

        if (contactoId != -1) {
            // Buscar el contacto en la base de datos
            ContactosRepository contactosRepository = new ContactosRepository(this);
            ContactosModel contacto = contactosRepository.obtenerContactoPorId(contactoId);

            if (contacto != null) {
                nombre.setText(contacto.getNombre());
                numero.setText(contacto.getNumero());
                nota.setText(contacto.getNota());

                // Si tienes una imagen en Base64, la conviertes a Bitmap y la muestras en un ImageView
                if (contacto.getImagen() != null) {
                    imagenContacto.setImageBitmap(imageUtils.decodeFromBase64(contacto.getImagen()));
                }
            }
        } else {
            Toast.makeText(this, "Error al cargar contacto", Toast.LENGTH_SHORT).show();
        }

        // Inicializar la lista de países
        paisesRepository = new PaisesRepository(this);
        paisesList = paisesRepository.ObtenerPaises();

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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idpaisSeleccionado = paisesList.get(position).getId();
                Toast.makeText(getApplicationContext(), "País seleccionado: " + paisesList.get(position).getNombre(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        // Ajustar insets de la UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
