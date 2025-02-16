package com.example.pm2e1506291;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.pm2e1506291.Repository.ContactosRepository;

import java.io.IOException;

public class Principal extends AppCompatActivity {

    private ImageView imagen;
    private Button btnGaleria, btnCamara, btnIngresar;
    private String imagenBit;


    private EditText nombre, numero, notas;
    private int idpais; //No se ha ingresado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        btnCamara = (Button) findViewById(R.id.button3);
        btnGaleria = (Button) findViewById(R.id.button4);
        btnIngresar = (Button) findViewById(R.id.button2);
        nombre = (EditText) findViewById(R.id.editTextText);
        numero = (EditText) findViewById(R.id.editTextPhone);
        notas = (EditText) findViewById(R.id.editTextTextMultiLine);
        imagen = (ImageView) findViewById(R.id.ivContactoLista);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombre.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),getString(R.string.vacionombre),Toast.LENGTH_SHORT).show();
                } else if (numero.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(),getString(R.string.vacionumero),Toast.LENGTH_SHORT).show();
                }else {
                    ContactosRepository.AddContact(nombre,numero,notas,idpais,imagenBit);
                }
            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,1);
        }
    }

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(bitmap);
            imagenBit = imageUtils.encodeToBase64(bitmap);
        }
    }

}