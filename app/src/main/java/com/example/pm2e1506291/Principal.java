package com.example.pm2e1506291;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm2e1506291.Funciones.imageUtils;
import com.example.pm2e1506291.Repository.ContactosRepository;
import com.example.pm2e1506291.Models.PaisesModel;
import com.example.pm2e1506291.Repository.PaisesRepository;

import java.io.IOException;
import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    private ImageView imagen;
    private Button btnGaleria, btnCamara, btnIngresar, btnLista;
    private String imagenBit;

    private EditText nombre, numero, notas;
    private int idpais; //No se ha ingresado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ContactosRepository contactosRepository = new ContactosRepository(this);
        btnCamara = findViewById(R.id.button3);
        btnGaleria = findViewById(R.id.button4);
        btnIngresar = findViewById(R.id.button2);
        nombre = findViewById(R.id.editTextText);
        numero = findViewById(R.id.editTextPhone);
        notas = findViewById(R.id.editTextTextMultiLine);
        imagen = findViewById(R.id.ivContactoLista);

        // Spinner de países
        PaisesRepository paisesRepository = new PaisesRepository(this);
        final int[] idPais = {-1}; // Inicializar el ID del país seleccionado
        Spinner spinner = findViewById(R.id.spinner);

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

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.vacionombre), Toast.LENGTH_SHORT).show();
                } else if (numero.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.vacionumero), Toast.LENGTH_SHORT).show();
                } else {
                    contactosRepository.AddContact(nombre.getText().toString(), numero.getText().toString(), notas.getText().toString(), idPais[0], imagenBit);
                }
            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirGaleria();
            }
        });

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Lista.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void AbrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        galeriaARL.launch(intent);
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    private ActivityResultLauncher<Intent> galeriaARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData(); // Obtener la URI de la imagen seleccionada
                    if (uri != null) {
                        try {
                            // Convertir la URI en un Bitmap
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imagen.setImageBitmap(bitmap);
                            imagenBit = imageUtils.encodeToBase64(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(Principal.this, R.string.imagenerror, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Principal.this, R.string.imagenerror, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Principal.this, R.string.imagenusuario, Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(bitmap);
            imagenBit = imageUtils.encodeToBase64(bitmap);
        }
    }
}
