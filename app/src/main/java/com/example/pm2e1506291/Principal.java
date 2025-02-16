package com.example.pm2e1506291;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}