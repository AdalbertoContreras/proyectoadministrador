package com.example.serviamigoadmin.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.modelo.Administrador;
import com.example.servimaigoadmin.R;
import java.util.ArrayList;

public class AdapterItemAsesor extends  RecyclerView.Adapter<AdapterItemAsesor.ViewHolderDatos>{
    private ArrayList<Administrador> alertas;
    public int h;
    public AdapterItemAsesor(ArrayList<Administrador> alerta_tempranas)
    {
        this.alertas = alerta_tempranas;
    }

    @NonNull
    @Override
    public AdapterItemAsesor.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_alerta_temprana,null, false);
        return new AdapterItemAsesor.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull AdapterItemAsesor.ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(alertas.get(i));
    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        private View view;
        private ImageView imagenAsesorImageView;
        private TextView nombreAsesorTextView;
        private RecyclerView listaEspecialidadesRecyclerView;
        private ImageButton detallesImageButton;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            this.view = itemView;
            imagenAsesorImageView = view.findViewById(R.id.magenAsesorImageView);
            nombreAsesorTextView = view.findViewById(R.id.nombreAsesorTextView);
            listaEspecialidadesRecyclerView = view.findViewById(R.id.listaEspecialidadesRecyclerView);
            detallesImageButton = view.findViewById(R.id.detallesAsesorImageButton);
        }

        public void setDatos(final Administrador asesor)
        {

        }
    }
}