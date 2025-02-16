package com.example.pm2e1506291.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pm2e1506291.Funciones.imageUtils;
import com.example.pm2e1506291.Models.ContactosModel;
import com.example.pm2e1506291.R;
import com.example.pm2e1506291.Repository.PaisesRepository;

import java.util.ArrayList;

public class listaContactosAdapter extends RecyclerView.Adapter<listaContactosAdapter.ViewHolder> {

    private Context context;
    private PaisesRepository paises;
    private ArrayList<ContactosModel> listaContactos;

    public listaContactosAdapter(Context context, ArrayList<ContactosModel> listaContactos) {
        this.context = context;
        this.listaContactos = listaContactos;
        this.paises = new PaisesRepository(context);
    }

    @NonNull
    @Override
    public listaContactosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contacto,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactosModel contacto = listaContactos.get(position);
        holder.txtNombre.setText(contacto.getNombre());
        String codigo = PaisesRepository.obtenerCodigoPorId(contacto.getIdpais());
        holder.txtTelefono.setText("("+codigo+") "+contacto.getNumero());
        holder.imageviwe.setImageBitmap(imageUtils.decodeFromBase64(contacto.getImagen()));

        holder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtTelefono;
        ImageView imageviwe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            imageviwe = itemView.findViewById(R.id.ivContactoLista);
        }
    }
}
