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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pm2e1506291.Funciones.imageUtils;
import com.example.pm2e1506291.Models.PaisesModel;
import com.example.pm2e1506291.Repository.PaisesRepository;

import java.io.IOException;
import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    private ImageView imagen;
    private String imagenBit;

    private ActivityResultLauncher<Intent> galeriaARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
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
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}