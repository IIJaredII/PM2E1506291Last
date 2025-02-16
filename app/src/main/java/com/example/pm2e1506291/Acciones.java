package com.example.pm2e1506291;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.pm2e1506291.Models.ContactosModel;
import com.example.pm2e1506291.Models.PaisesModel;
import com.example.pm2e1506291.Repository.ContactosRepository;
import com.example.pm2e1506291.Repository.PaisesRepository;

import java.io.IOException;
import java.util.ArrayList;

public class Acciones extends AppCompatActivity {
    private int idpaisSeleccionado, idContacto;
    ContactosRepository contactosRepository = new ContactosRepository(this);
    private ImageView imagen;
    private String imagenBit;
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
        spinner = findViewById(R.id.spinner2);

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String numeros = numero.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(Acciones.this);
                builder.setMessage("Desea realizar la llamada al numero " + numeros + "?")
                        .setTitle("Confirmar llamada");

                builder.setPositiveButton("Llamar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (ActivityCompat.checkSelfPermission(Acciones.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Acciones.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + numeros));
                            startActivity(intent);
                        }
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nombre.getText().toString();
                String num = numero.getText().toString();
                String notas = nota.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"Nombre: "+nom+"\nNumero: "+num+"\nNotas: "+notas);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Compartir con "));
            }
        });

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
                    imagenBit = contacto.getImagen();
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
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Acciones.this);
                builder.setMessage("Desea ACTUALIZAR este contacto?")
                        .setTitle("Confirmar Modificacion");

                builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int contactoId = getIntent().getIntExtra("contacto_id", -1);
                        if (nombre.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.vacionombre), Toast.LENGTH_SHORT).show();
                        } else if (numero.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.vacionumero), Toast.LENGTH_SHORT).show();
                        } else {
                            contactosRepository.UpdateContact(contactoId, nombre.getText().toString(), numero.getText().toString(), nota.getText().toString(), idpaisSeleccionado, imagenBit);
                            Intent intent = new Intent(getApplicationContext(),Lista.class);
                            startActivity(intent);
                        }
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Acciones.this);
                builder.setMessage("Desea ELIMINAR este contacto?")
                        .setTitle("Confirmar Eliminacion");

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int contactoId = getIntent().getIntExtra("contacto_id", -1);
                        if (contactoId != -1) { // Verificar que el contacto es válido
                            contactosRepository.deleteContact(contactoId);
                            Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Lista.class);
                            startActivity(intent);
                            finish(); // Cerrar la actividad después de eliminar
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: No se puede eliminar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Ajustar insets de la UI
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
                            Toast.makeText(Acciones.this, R.string.imagenerror, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Acciones.this, R.string.imagenerror, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Acciones.this, R.string.imagenusuario, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String numeros = numero.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numeros));
                startActivity(intent);
            } else {
                Toast.makeText(Acciones.this, "Permiso de llamada no concedido", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
