package com.example.pm2e1506291;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm2e1506291.Funciones.imageUtils;
import com.example.pm2e1506291.Models.PaisesModel;
import com.example.pm2e1506291.Repository.PaisesRepository;

import java.io.IOException;
import java.util.ArrayList;

public class Acciones extends AppCompatActivity {
    private int idpais;
    private ImageView imagen;
    private String imagenBit;
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

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirGaleria();
            }
        });

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = "98360340";
                if (ActivityCompat.checkSelfPermission(Acciones.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Acciones.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + numero));
                    startActivity(intent);
                }
            }
        });

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"nombre: "+"\nNumero:");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Compartir con "));
            }
        });


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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String numero = "98360340";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numero));
                startActivity(intent);
            } else {
                Toast.makeText(Acciones.this, "Permiso de llamada no concedido", Toast.LENGTH_SHORT).show();
            }
        }
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
                            Toast.makeText(this, R.string.imagenerror, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, R.string.imagenerror, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.imagenusuario, Toast.LENGTH_SHORT).show();
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